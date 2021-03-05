package campuslifecenter.info.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.InfoService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Api("发布者操作")
@RestWarpController
@RequestMapping("/info")
public class PublisherController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private TracerUtil tracerUtil;

    @ApiOperation("获取来源下所有填写的信息")
    @GetMapping("/getAccountSubmit")
    public InfoSourceCollect get(@RequestParam String ref) {
        tracerUtil.getSpan().tag("ref", ref);
        long root = infoService.getRoot(ref);
        return accountInfoService.getSubmitByRoot(root).setSource(ref);
    }

    @ApiOperation("获取现有信息列表")
    @GetMapping("/getExistInfo")
    public List<InfoItem> getExistInfo() {
        return infoService.getExistInfo();
    }

    @GetMapping("/download")
    public void download(@RequestParam String ref, HttpServletResponse response) {
        tracerUtil.getSpan().tag("ref", ref);
        long root = infoService.getRoot(ref);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        InfoSourceCollect submits = accountInfoService.getSubmitByRoot(root);
        String fn = submits.getItems().get(0).getName();
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fn, StandardCharsets.UTF_8) + ".xlsx");
        tracerUtil.newSpan("write excel", scopedSpan -> {
            //// head ////
            List<List<String>> head = new ArrayList<>();
            head.add(new ArrayList<>(List.of("账户id")));
            head.add(new ArrayList<>(List.of("账户名")));
            InfoItem.CompositeItem getHeadInfo = (InfoItem.CompositeItem) submits.getItems().get(0);
            AtomicInteger index = new AtomicInteger(3);
            Map<Long, Integer> column = new HashMap<>();
            List<List<String>> dynamicHead = getHeadInfo
                    .getItems()
                    .stream()
                    .flatMap(item -> getItemList(item, new ArrayList<>(), index, column).stream())
                    .collect(Collectors.toList());
            head.addAll(dynamicHead);
            scopedSpan.annotate("get head finish");
            //// data ////
            List<List<String>> data = submits.getItems()
                    .stream()
                    .map(item -> {
                        List<InfoItem> accountSubmits = getValues(item);
                        accountSubmits.sort(Comparator.comparingInt(i -> column.get(i.getId())));
                        List<String> res = new ArrayList<>();
                        res.add(item.getAid());
                        res.add(item.getAccountName());
                        res.addAll(accountSubmits.stream()
                                .map(value -> String.join(", ", value.getValue()))
                                .collect(Collectors.toList()));
                        return res;
                    })
                    .collect(Collectors.toList());
            scopedSpan.annotate("get data finish");
            //// write ////
            try (OutputStream outputStream = response.getOutputStream()) {
                EasyExcel.write(outputStream)
                        .sheet(fn)
                        .head(head)
                        .doWrite(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private List<List<String>> getItemList(InfoItem item, List<String> parents, AtomicInteger index, Map<Long, Integer> column) {
        if (!(item instanceof InfoItem.CompositeItem)) {
            List<String> list = new ArrayList<>(parents.size() + 1);
            list.addAll(parents);
            list.add(item.getName());
            column.put(item.getId(), index.getAndIncrement());
            ArrayList<List<String>> res = new ArrayList<>(1);
            res.add(list);
            return res;
        }
        List<String> parent = new ArrayList<>(parents.size() + 1);
        parent.addAll(parents);
        parent.add(item.getName());
        return ((InfoItem.CompositeItem) item).getItems()
                .stream()
                .flatMap(infoItem -> getItemList(infoItem, parent, index, column).stream())
                .collect(Collectors.toList());
    }

    private List<InfoItem> getValues(InfoItem item) {
        if (!(item instanceof InfoItem.CompositeItem)) {
            return List.of(item);
        }
        return ((InfoItem.CompositeItem) item)
                .getItems()
                .stream()
                .flatMap(item1 -> getValues(item1).stream())
                .collect(Collectors.toList());
    }
}

package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Api("账户操作")
@RestWarpController
@RequestMapping("/info")
public class InfoAccountController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private Tracer tracer;

    @Value("${info.save-path}")
    private String SAVE_PATH;
    @Value("${info.uri-path}")
    private String URI_PATH;
    private static final char FILE_DIVIDER = File.separatorChar;

    @ApiOperation("获取收集项目及已提交信息")
    @GetMapping("/get")
    public InfoItem.CompositeItem get(@RequestParam String ref, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("account", aid);
        tracer.currentSpan().tag("ref", ref);
        long rootId = infoService.getRoot(ref);
        return accountInfoService.getSubmit(aid, rootId);
    }

    @ApiOperation("获取已保存信息")
    @PostMapping("/getAccountSave")
    public List<AccountSaveInfo> getByAccount(@RequestBody List<Long> ids, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("account", aid);
        return accountInfoService.getSaveByAccount(ids, aid);
    }

    @ApiOperation("提交")
    @PostMapping("/submit")
    public Boolean submit(@RequestBody List<AccountSubmit> infos, @RequestParam String token, @RequestParam String ref) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("account", aid);
        tracer.currentSpan().tag("ref", ref);
        long root = infoService.getRoot(ref);
        infos.stream().peek(info -> info.setAid(aid)).forEach(info -> info.setRoot(root));
        return accountInfoService.submit(infos);
    }

    @ApiOperation("上传")
    @PostMapping("/{id}/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile file,
                                   @PathVariable("id") long id,
                                   @RequestParam String name,
                                   @RequestParam String token) {
        return Response.withData(() -> {
            String aid = cacheService.getAccountIdByToken(token);
            tracer.currentSpan().tag("account", aid);
            InfoItem infoItem = infoService.getInfoItem(id, null);
            if (!(infoItem instanceof InfoItem.FileItem)) {
                throw new ResponseException("not file info");
            }
            StringBuilder pathBuilder = new StringBuilder(SAVE_PATH)
                    .append(((InfoItem.FileItem) infoItem).getPath())
                    .append(FILE_DIVIDER)
                    .append(aid);
            File pathFile = new File(pathBuilder.toString());
            if (!pathFile.exists() && !pathFile.mkdirs()) {
                throw new ResponseException("create path fail:" + pathBuilder.toString());
            }
            pathBuilder.append(FILE_DIVIDER).append(name);
            try {
                file.transferTo(new File(pathBuilder.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return URI_PATH + ((InfoItem.FileItem) infoItem).getPath() + FILE_DIVIDER + aid + FILE_DIVIDER + name;
        });
    }

}

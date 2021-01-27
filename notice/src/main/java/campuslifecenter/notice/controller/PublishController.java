package campuslifecenter.notice.controller;

import brave.Tracer;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.NoticeService;
import campuslifecenter.notice.service.PublishService;
import campuslifecenter.notice.service.TodoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/notice/publish")
public class PublishController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private PublishService publishService;
    @Autowired
    private CacheService cacheService;

    @Autowired
    private Tracer tracer;
    @Value("${notice.notice-file-path}")
    private String FILE_PREFIX;
    @Value("${notice.web-file-path}")
    private String WEB_PREFIX;

    @ApiOperation("发布id")
    @GetMapping("/publishId")
    public Response<String> publishId(@ApiParam("发布id") @RequestParam String token) {
        return Response.withData(() -> publishService.getPublishId(token));
    }

    @ApiOperation("发布通知")
    @PostMapping("/publicNotice")
    public Response<Long> publicNotice(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        return Response.withData(() -> {
            publishNotice.getNotice().setCreator(cacheService.getAccountIdByToken(publishNotice.getToken()));
            publishNotice.setAccountList(publishService
                    .publicAccountStream(publishNotice)
                    .map(PublishAccount::getAccounts)
                    .flatMap(List::stream)
                    .map(IdName::getId)
                    .distinct()
                    .collect(toList())
            );
            return publishService.publicNotice(publishNotice);
        });
    }

    @ApiOperation("获取收到通知的成员")
    @PostMapping("/getPublicNoticeAccount")
    public Response<List<PublishAccount<?>>> getPublicNoticeAccount(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        return Response.withData(() -> publishService
                .publicAccountStream(publishNotice)
                .collect(toList()));
    }

    @GetMapping("/getAllTodo")
    public Response<List<TodoInfo>> getAllTodo(@RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        List<String> sources = noticeService.getTodoRefByCreator(aid);
        return Response.withData(() -> {
            Response<List<TodoInfo>> response = todoService.getTodoBySources(sources);
            if (!response.isSuccess()) {
                throw new RuntimeException("get fail");
            }
            return response.getData();
        });
    }

    @PostMapping("/getPublishTodo")
    public Response<PublishAccount<PublishTodo>> getPublishTodo(@RequestBody PublishTodo publishTodo) {
        return Response.withData(() -> publishService.publishTodo(publishTodo));
    }

    @PostMapping("/getPublishInfo")
    public Response<PublishAccount<PublishInfo>> getPublishInfo(@RequestBody PublishInfo publishInfo) {
        return Response.withData(() -> publishService.publishInfo(publishInfo));
    }

    @PostMapping("/getPublishOrganization")
    public Response<PublishAccount<PublishOrganization>> getPublishOrganization(@RequestBody PublishOrganization publishOrganization) {
        return Response.withData(() -> publishService.publishOrganization(publishOrganization));
    }

    @PostMapping("/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile file, @RequestParam String ref, @RequestParam String name) {
        return Response.withData(() -> {
            try {
                File path = new File(FILE_PREFIX + ref);
                if (!path.exists() && !path.mkdir()) {
                    throw new RuntimeException("create path fail: " + path);
                }
                file.transferTo(new File(path.getPath() + "/" + name));
                return WEB_PREFIX + ref + "/" + name;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping("/getFileList")
    public Response<List<String>> getFileList(@RequestParam String ref) {
        return Response.withData(() -> {
            File path = new File(FILE_PREFIX + ref);
            if (!path.exists()) {
                return List.of();
            }
            return Arrays.stream(Optional.ofNullable(path.list()).orElse(new String[]{}))
                    .map(name -> WEB_PREFIX + ref + "/" + name).collect(toList());
        });
    }
}

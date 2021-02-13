package campuslifecenter.notice.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestWarpController
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
    private TracerUtil tracerUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${notice.save-path}")
    private String SAVE_PREFIX;
    @Value("${notice.uri-path}")
    private String URI_PREFIX;
    @Value("${notice.redis.cache.notice}")
    private String NOTICE_PREFIX;

    private static final char FILE_DIVIDER = File.separatorChar;

    @ApiOperation("发布id")
    @GetMapping("/publishId")
    public Response<String> publishId(@ApiParam("发布id") @RequestParam String token) {
        return Response.withData(() -> publishService.getPublishId(token));
    }

    @ApiOperation("发布通知")
    @PostMapping("/publicNotice")
    public Long publicNotice(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        String aid = cacheService.getAccountIdByToken(publishNotice.getToken());
        tracerUtil.getSpan().tag("aid", aid);
        AuthException.checkThrow(aid, publishService.getPublishAid(publishNotice.getPid()));
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
    }

    @ApiOperation("获取收到通知的成员")
    @PostMapping("/getPublicNoticeAccount")
    public List<PublishAccount<?>> getPublicNoticeAccount(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        return publishService
                .publicAccountStream(publishNotice)
                .collect(toList());
    }

    @GetMapping("/getAllTodo")
    public List<TodoInfo> getAllTodo(@RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        List<String> sources = noticeService.getTodoRefByCreator(aid);
        Response<List<TodoInfo>> response = todoService.getTodoBySources(sources);
        if (!response.isSuccess()) {
            throw new RuntimeException("get fail");
        }
        return response.getData();
    }

    @PostMapping("/getPublishTodo")
    public PublishAccount<PublishTodo> getPublishTodo(@RequestBody PublishTodo publishTodo) {
        return publishService.publishTodo(publishTodo);
    }

    @PostMapping("/getPublishInfo")
    public PublishAccount<PublishInfo> getPublishInfo(@RequestBody PublishInfo publishInfo) {
        return publishService.publishInfo(publishInfo);
    }

    @PostMapping("/getPublishOrganization")
    public PublishAccount<PublishOrganization> getPublishOrganization(@RequestBody PublishOrganization publishOrganization) {
        return publishService.publishOrganization(publishOrganization);
    }

    @PostMapping("/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile file,
                         @RequestParam String ref,
                         @RequestParam String name,
                         @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        AuthException.checkThrow(aid, publishService.getPublishAid(ref));
        try {
            File path = new File(SAVE_PREFIX + ref);
            if (!path.exists() && !path.mkdir()) {
                throw new RuntimeException("create path fail: " + path);
            }
            file.transferTo(new File(path.getPath() + FILE_DIVIDER + name));
            redisTemplate.delete(NOTICE_PREFIX + noticeService.getNoticeIdByRef(ref));
            return Response.withData(URI_PREFIX + ref + FILE_DIVIDER + name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/deleteFile")
    public boolean deleteFile(@RequestParam String ref, @RequestParam String name, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        tracerUtil.getSpan().tag("file name", name);
        tracerUtil.getSpan().tag("ref", ref);
        AuthException.checkThrow(aid, publishService.getPublishAid(ref));
        if (new File(SAVE_PREFIX + ref + FILE_DIVIDER + name).delete()) {
            redisTemplate.delete(NOTICE_PREFIX + noticeService.getNoticeIdByRef(ref));
        }
        return true;
    }

    @GetMapping("/getFileList")
    public List<String> getFileList(@RequestParam String ref) {
        File path = new File(SAVE_PREFIX + ref);
        if (!path.exists()) {
            return List.of();
        }
        return Arrays.stream(Optional.ofNullable(path.list()).orElse(new String[]{}))
                .map(name -> URI_PREFIX + ref + FILE_DIVIDER + name).collect(toList());
    }
}

package campuslifecenter.notice.controller;

import campuslifecenter.notice.integration.UserService;
import campuslifecenter.notice.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import campuslifecenter.notice.model.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@AllArgsConstructor
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;

    @Autowired
    private final UserService userService;

    @PostMapping("/createNotice")
    public boolean createNotice(@RequestBody Notice notice) {
        return noticeService.createNotice(notice);
    }

    @GetMapping("/getNotice/{id}")
    public Notice getNotice(@PathVariable("id") Long id) {
        return noticeService.getNotice(id);
    }

    @GetMapping("/getNoticeByUser/{id}")
    public List<Notice> getNoticeByUser(@PathVariable("id") Long id) {
        return noticeService.getNoticeByUser(id);
    }

    @GetMapping("getNoticeByAuthor/{id}")
    public List<Notice> getNoticeByAuthor(@PathVariable("id") Long id) {
        return noticeService.getNoticeByAuthor(id);
    }

    @GetMapping("/readCount/{id}")
    public Long readCount(@PathVariable("id") Long id) {
        return noticeService.readCount(id);
    }
}

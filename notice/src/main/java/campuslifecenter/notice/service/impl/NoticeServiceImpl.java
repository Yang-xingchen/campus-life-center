package campuslifecenter.notice.service.impl;

import campuslifecenter.common.model.User;
import campuslifecenter.notice.integration.RoleService;
import campuslifecenter.notice.integration.UserService;
import campuslifecenter.notice.model.InformsUser;
import campuslifecenter.notice.model.Notice;
import campuslifecenter.notice.repository.InformRepository;
import campuslifecenter.notice.repository.NoticeRepository;
import campuslifecenter.notice.service.NoticeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoleService roleService;

    @Autowired
    private final NoticeRepository noticeRepository;

    @Autowired
    private final InformRepository informRepository;

    @Override
    public boolean createNotice(Notice notice) {
        log.info(notice.toString());
        Objects.requireNonNull(notice.getTitle());
        if (notice.getAuthors().size() == 0) {
            throw new IllegalArgumentException("no author");
        }
        if (notice.getAuthorName() == null) {
            List<Long> ids = notice
                    .getAuthors()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            String names = userService
                    .getUsers(ids)
                    .stream()
                    .map(User::getName)
                    .collect(Collectors.joining(", ", "", ""));
            notice.setAuthorName(names);
        }
        List<InformsUser> informs = notice.getInforms();
        notice.setInforms(null);
        Notice finalNotice = noticeRepository.save(notice);
        informs.forEach(informsUser -> informsUser.setNotice(finalNotice));
        informRepository.saveAll(informs);
        return true;
    }

    @Override
    public Notice getNotice(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notice> getNoticeByUser(Long id) {
        return informRepository
                .findByUser(userService.userInfo(id))
                .stream()
                .peek(informsUser -> informsUser.setLooked(true))
                .map(InformsUser::getNotice)
                .collect(Collectors.toList());
    }

    @Override
    public Long readCount(Long id) {
        return informRepository.countByNoticeAndLooked(noticeRepository.getOne(id), true);
    }

    @Override
    public List<Notice> getNoticeByAuthor(Long id) {
        return noticeRepository.findByAuthors(userService.userInfo(id));
    }


}

package campuslifecenter.notice.repository;

import campuslifecenter.common.model.User;
import campuslifecenter.notice.model.Notice;
import campuslifecenter.notice.model.projection.NoticeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<NoticeInfo> findByAuthors(User user);

    Optional<NoticeInfo> findInfoById(Long id);
}

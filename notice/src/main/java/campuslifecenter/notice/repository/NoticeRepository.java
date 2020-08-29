package campuslifecenter.notice.repository;

import campuslifecenter.common.model.User;
import campuslifecenter.notice.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByAuthors(User user);

}

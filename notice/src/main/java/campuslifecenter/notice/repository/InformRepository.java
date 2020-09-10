package campuslifecenter.notice.repository;

import campuslifecenter.common.model.User;
import campuslifecenter.notice.model.InformsUser;
import campuslifecenter.notice.model.Notice;
import campuslifecenter.notice.model.projection.InformsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformRepository extends JpaRepository<InformsUser, Long> {

    List<InformsInfo> findByUser(User user);

    Long countByNoticeAndLooked(Notice notice, Boolean looked);

}

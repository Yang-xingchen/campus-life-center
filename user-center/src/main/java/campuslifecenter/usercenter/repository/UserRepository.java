package campuslifecenter.usercenter.repository;

import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.SignInUser;
import campuslifecenter.common.model.projections.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<SignInUser> getSingInBySignId(Long signId);

    Optional<UserInfo> getInfoById(Long id);

    List<UserInfo> getAllBy();

}

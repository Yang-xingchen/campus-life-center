package campuslifecenter.common.model.projections;

import campuslifecenter.common.model.Gender;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public interface UserInfo extends Serializable {

    Long getId();

    Long getSignId();

    String getName();

    Gender getGender();

    LocalDateTime getCreateTime();

    Set<UserRole> getRoles();

    Set<UserRole> getCreateRole();

    interface UserRole extends Serializable{

        Long getId();

        String getName();

        LocalDateTime getCreateTime();

        boolean isPub();

    }

}

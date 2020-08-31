package campuslifecenter.common.model.projections;

import campuslifecenter.common.model.Gender;

import java.time.LocalDateTime;
import java.util.List;

public interface RoleInfo {

    Long getId();

    String getName();

    LocalDateTime getCreateTime();

    RoleUser getBelong();

    boolean isPub();

    List<RoleUser> getUsers();

    interface RoleUser {

        Long getId();

        Long getSignId();

        String getName();

        Gender getGender();

        LocalDateTime getCreateTime();

    }

}

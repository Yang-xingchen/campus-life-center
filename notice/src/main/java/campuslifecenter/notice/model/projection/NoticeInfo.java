package campuslifecenter.notice.model.projection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public interface NoticeInfo extends Serializable {

    Long getId();

    String getTitle();

    String getContent();

    List<SimpleUserInfo> getAuthors();
    String getAuthorName();

    LocalDateTime getCreateDate();

    List<NoticeInforms> getInforms();

    interface NoticeInforms extends Serializable {

        SimpleUserInfo getUser();

        boolean isLooked();

        boolean isCompleted();

    }


}

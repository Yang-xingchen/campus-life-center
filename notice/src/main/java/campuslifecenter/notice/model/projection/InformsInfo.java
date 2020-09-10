package campuslifecenter.notice.model.projection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public interface InformsInfo extends Serializable {

    Long getId();

    InformsNotice getNotice();

    boolean isLooked();

    boolean isCompleted();

    interface InformsNotice extends Serializable {

        Long getId();

        String getTitle();

        String getContent();

        List<SimpleUserInfo> getAuthors();
        String getAuthorName();

        LocalDateTime getCreateDate();
    }


}

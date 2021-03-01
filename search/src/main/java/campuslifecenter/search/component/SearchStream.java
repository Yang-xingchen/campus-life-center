package campuslifecenter.search.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SearchStream {

    String PUBLISH_NOTICE = "publishNotice";

    @Input(PUBLISH_NOTICE)
    SubscribableChannel publishNotice();

}

package campuslifecenter.info.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface InfoStream {
    String PUBLISH_OBSERVE = "publishObserve";

    @Output(PUBLISH_OBSERVE)
    MessageChannel publishAccount();

}

package campuslifecenter.usercenter.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AccountStream {
    String PUBLISH_OBSERVE = "publishObserve";

    @Output(PUBLISH_OBSERVE)
    MessageChannel publishAccount();

}

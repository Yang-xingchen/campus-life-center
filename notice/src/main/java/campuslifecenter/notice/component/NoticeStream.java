package campuslifecenter.notice.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NoticeStream {

    String PUBLISH_ACCOUNT = "publishAccount";
    String PUBLISH_OBSERVE = "publishObserve";

    @Output(PUBLISH_ACCOUNT)
    MessageChannel publishAccount();

    @Input(PUBLISH_OBSERVE)
    SubscribableChannel publishObserve();

}

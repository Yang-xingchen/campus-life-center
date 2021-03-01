package campuslifecenter.notice.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NoticeStream {

    String PUBLISH_ACCOUNT = "publishAccount";
    String PUBLISH_OBSERVE = "publishObserve";
    String CONDITION_CHANGE = "conditionChange";
    String PUBLISH_NOTICE = "publishNotice";

    @Output(CONDITION_CHANGE)
    MessageChannel conditionChange();

    @Output(PUBLISH_ACCOUNT)
    MessageChannel publish();

    @Output(PUBLISH_NOTICE)
    MessageChannel publishNotice();

    @Input(PUBLISH_ACCOUNT)
    SubscribableChannel publishAccount();

    @Input(PUBLISH_OBSERVE)
    SubscribableChannel publishObserve();

}

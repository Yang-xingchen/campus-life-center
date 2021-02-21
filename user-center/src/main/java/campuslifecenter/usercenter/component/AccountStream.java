package campuslifecenter.usercenter.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AccountStream {
    String CONDITION_CHANGE = "conditionChange";

    @Output(CONDITION_CHANGE)
    MessageChannel conditionChange();

}

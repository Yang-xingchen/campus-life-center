package campuslifecenter.todo.component;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TodoStream {
    String CONDITION_CHANGE = "conditionChange";

    @Output(CONDITION_CHANGE)
    MessageChannel conditionChange();

}

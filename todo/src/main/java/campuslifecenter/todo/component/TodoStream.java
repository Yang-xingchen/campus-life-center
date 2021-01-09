package campuslifecenter.todo.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TodoStream {

    @Output("handle-public-todo")
    MessageChannel publicNotice();

    @Input("public-todo")
    SubscribableChannel handlePublicNotice();

}

package campuslifecenter.info.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface InfoStream {
    @Output("")
    MessageChannel publicNotice();

    @Input("")
    SubscribableChannel handlePublicNotice();

}

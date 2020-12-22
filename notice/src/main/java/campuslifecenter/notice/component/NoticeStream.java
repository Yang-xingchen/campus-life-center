package campuslifecenter.notice.component;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NoticeStream {
    @Output("handle-public-notice")
    MessageChannel publicNotice();

    @Input("public-notice")
    SubscribableChannel handlePublicNotice();

}

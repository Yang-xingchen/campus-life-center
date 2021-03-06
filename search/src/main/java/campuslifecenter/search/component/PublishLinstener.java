package campuslifecenter.search.component;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.model.Response;
import campuslifecenter.search.model.NoticeInfo;
import campuslifecenter.search.model.NoticeSearch;
import campuslifecenter.search.repository.NoticeRepository;
import campuslifecenter.search.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class PublishLinstener {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private TracerUtil tracerUtil;

    @StreamListener(SearchStream.PUBLISH_NOTICE)
    public void processPublishNotice(long id) {
        tracerUtil.getSpan().tag("nid", id+"");
        Response<NoticeInfo> response = noticeService.getNotice(id);
        if (response.getCode() == AuthException.AUTH_FAIL_CODE) {
            noticeRepository.deleteById(id);
            return;
        }
        NoticeInfo noticeInfo = response.checkGet(ProcessException.NOTICE, "get fail");
        NoticeSearch noticeSearch = NoticeSearch.createByInfo(noticeInfo);
        noticeRepository.save(noticeSearch);
    }

}

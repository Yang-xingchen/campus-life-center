package campuslifecenter.comment.service.impl;

import campuslifecenter.comment.entry.Comment;
import campuslifecenter.comment.entry.CommentExample;
import campuslifecenter.comment.entry.CommentRef;
import campuslifecenter.comment.entry.CommentRefExample;
import campuslifecenter.comment.mapper.CommentMapper;
import campuslifecenter.comment.mapper.CommentRefMapper;
import campuslifecenter.comment.model.CommentInfo;
import campuslifecenter.comment.service.CacheService;
import campuslifecenter.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRefMapper refMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    public List<CommentInfo> getByRef(String ref) {
        CommentRefExample example = new CommentRefExample();
        example.createCriteria().andRefEqualTo(ref);
        return refMapper.selectByExample(example)
                .stream()
                .map(CommentRef::getId)
                .map(this::getComment)
                .collect(Collectors.toList());
    }

    @Override
    public CommentInfo getComment(long id) {
        CommentInfo comment = CommentInfo.createByComment(commentMapper.selectByPrimaryKey(id));
        comment.setAccountName(cacheService.getAccountNameByID(comment.getAid()));
        comment.setChildren(getCommentChildren(id));
        return comment;
    }

    private List<CommentInfo> getCommentChildren(long id) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentEqualTo(id);
        return commentMapper.selectByExample(example)
                .stream()
                .map(CommentInfo::createByComment)
                .peek(commentInfo -> commentInfo.setAccountName(cacheService.getAccountNameByID(commentInfo.getAid())))
                .peek(commentInfo -> commentInfo.setChildren(getCommentChildren(commentInfo.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public CommentInfo reply(long id, String aid, String content) {
        Comment comment = new Comment();
        comment.withParent(id).withAid(aid).withContent(content).withPublishTime(new Date());
        commentMapper.insertSelective(comment);
        return getComment(comment.getId());
    }

    @Override
    public CommentInfo reply(String ref, String aid, String content) {
        Comment comment = new Comment();
        comment.withAid(aid).withContent(content).withPublishTime(new Date());
        commentMapper.insertSelective(comment);
        CommentRef commentRef = new CommentRef();
        commentRef.withId(comment.getId()).withRef(ref);
        refMapper.insert(commentRef);
        return getComment(comment.getId());
    }

}

package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.entity.Comment;
import com.tencent.wxcloudrun.entity.CommentLike;
import com.tencent.wxcloudrun.entity.ExhibitScript;
import com.tencent.wxcloudrun.entity.Script;
import com.tencent.wxcloudrun.mapper.CommentLikeMapper;
import com.tencent.wxcloudrun.mapper.CommentMapper;
import com.tencent.wxcloudrun.mapper.ExhibitScriptMapper;
import com.tencent.wxcloudrun.mapper.ScriptMapper;
import com.tencent.wxcloudrun.service.CommentLikeService;
import com.tencent.wxcloudrun.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    private CommentLikeMapper commentLikeMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ExhibitScriptMapper exhibitScriptMapper;

    @Autowired
    private ScriptMapper scriptMapper;


    @Transactional(rollbackFor = CommonException.class)
    @Override
    public Long likeComment(Long commentId, String openId) {

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) throw new CommonException(CommonErrorCode.COMMENT_NOT_EXIST);

        QueryWrapper<CommentLike> commentLikeQueryWrapper = new QueryWrapper<>();
        commentLikeQueryWrapper.eq("comment_id", commentId);
        commentLikeQueryWrapper.eq("open_id", openId);

        CommentLike commentLike0 = commentLikeMapper.selectOne(commentLikeQueryWrapper);
        if (commentLike0 != null) throw new CommonException(CommonErrorCode.COMMENT_LIKE_ALREADY_EXIST);

        CommentLike commentLike = CommentLike.builder()
                .commentId(commentId)
                .createTime(TimeUtil.getCurrentTimestamp())
                .userOpenId(openId)
                .build();

        commentLikeMapper.insert(commentLike);

        comment.setLikeCount(comment.getLikeCount() + 1);
        commentMapper.updateById(comment);

        ExhibitScript exhibitScript = exhibitScriptMapper.selectById(comment.getExhibitScriptId());
        exhibitScript.setDiscussCount(exhibitScript.getDiscussCount() + 1);
        exhibitScriptMapper.updateById(exhibitScript);

        Script script = scriptMapper.selectById(exhibitScript.getScriptId());
        script.setHeat(script.getHeat() + 1);
        scriptMapper.updateById(script);


        return commentLike.getId();
    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    public void cancelLikeComment(Long commentId, String openId) {

        QueryWrapper<CommentLike> commentLikeQueryWrapper = new QueryWrapper<>();
        commentLikeQueryWrapper.eq("comment_id", commentId);
        commentLikeQueryWrapper.eq("open_id", openId);

        CommentLike commentLike = commentLikeMapper.selectOne(commentLikeQueryWrapper);
        if (commentLike == null) throw new CommonException(CommonErrorCode.COMMENT_LIKE_NOT_EXIST);

        commentLikeMapper.deleteById(commentLike.getId());

        Comment comment = commentMapper.selectById(commentId);
        comment.setLikeCount(comment.getLikeCount() - 1);
        commentMapper.updateById(comment);

    }
}

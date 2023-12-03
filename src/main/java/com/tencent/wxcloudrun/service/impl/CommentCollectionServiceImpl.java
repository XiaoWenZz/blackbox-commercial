package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefCommentCollection;
import com.tencent.wxcloudrun.entity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.service.CommentCollectionService;
import com.tencent.wxcloudrun.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentCollectionServiceImpl implements CommentCollectionService {

    @Autowired
    private CommentCollectionMapper commentCollectionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ExhibitScriptMapper exhibitScriptMapper;

    @Autowired
    private ScriptMapper scriptMapper;

    @Transactional(rollbackFor = CommonException.class)
    @Override
    public Long collect(String openId, Long commentId) {

        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        CommentCollection commentCollection0 = commentCollectionMapper.selectByUserIdAndCommentId(user.getId(), commentId);
        if (commentCollection0 != null) throw new CommonException(CommonErrorCode.COLLECTION_ALREADY_EXIST);

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) throw new CommonException(CommonErrorCode.COMMENT_NOT_EXIST);

        ExhibitScript exhibitScript = exhibitScriptMapper.selectById(comment.getExhibitScriptId());
        exhibitScript.setDiscussCount(exhibitScript.getDiscussCount() + 1);
        exhibitScriptMapper.updateById(exhibitScript);

        Script script = scriptMapper.selectById(exhibitScript.getScriptId());
        script.setHeat(script.getHeat() + 1);
        scriptMapper.updateById(script);


        CommentCollection commentCollection = CommentCollection.builder()
                .userId(user.getId())
                .commentId(commentId)
                .createTime(TimeUtil.getCurrentTimestamp())
                .build();

        commentCollectionMapper.insert(commentCollection);

        return commentCollection.getId();
    }

    @Override
    public void cancelCollect(String openId, Long commentCollectionId) {
        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        CommentCollection commentCollection = commentCollectionMapper.selectById(commentCollectionId);
        if (commentCollection == null || !commentCollection.getUserId().equals(user.getId())) throw new CommonException(CommonErrorCode.COLLECTION_NOT_EXIST);

        Comment comment = commentMapper.selectById(commentCollection.getCommentId());
        if (comment == null) throw new CommonException(CommonErrorCode.COMMENT_NOT_EXIST);

        comment.setLikeCount(comment.getLikeCount() - 1);
        commentMapper.updateById(comment);

        commentCollectionMapper.deleteById(commentCollectionId);


    }

    @Override
    public Page<BriefCommentCollection> getCollectionList(String openId, int pageSize, int pageNum) {
        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        QueryWrapper<CommentCollection> commentCollectionQueryWrapper = new QueryWrapper<>();
        commentCollectionQueryWrapper.eq("user_id", user.getId());

        PageHelper.startPage(pageNum, pageSize);
        List<CommentCollection> commentCollectionList = commentCollectionMapper.selectList(commentCollectionQueryWrapper);
        PageInfo<CommentCollection> pageInfo = new PageInfo<>(commentCollectionList);

        List<BriefCommentCollection> briefCommentCollectionList = new ArrayList<>();
        for (CommentCollection commentCollection : commentCollectionList) {

            User user1 = userMapper.selectById(commentCollection.getUserId());
            if (user1 == null) throw new CommonException(CommonErrorCode.UNKNOWN_ERROR);


            BriefCommentCollection briefCommentCollection = BriefCommentCollection.builder()
                    .id(commentCollection.getId())
                    .commentId(commentCollection.getCommentId())
                    .createTime(commentCollection.getCreateTime())
                    .build();
            briefCommentCollectionList.add(briefCommentCollection);
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefCommentCollectionList);

    }

    @Override
    public CommentCollection getCollection(Long commentCollectionId) {

        CommentCollection commentCollection = commentCollectionMapper.selectById(commentCollectionId);
        if (commentCollection == null) throw new CommonException(CommonErrorCode.COLLECTION_NOT_EXIST);

        return commentCollection;

    }
}

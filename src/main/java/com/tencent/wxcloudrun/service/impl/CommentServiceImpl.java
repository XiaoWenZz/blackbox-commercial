package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefComment;
import com.tencent.wxcloudrun.dto.BriefCommentLandScript;
import com.tencent.wxcloudrun.dto.BriefSubComment;
import com.tencent.wxcloudrun.dto.request.AddCommentRequest;
import com.tencent.wxcloudrun.dto.request.AddSubCommentRequest;
import com.tencent.wxcloudrun.dto.request.GetScriptCommentListRequest;
import com.tencent.wxcloudrun.dto.request.GetSubCommentListRequest;
import com.tencent.wxcloudrun.entity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.service.CommentService;
import com.tencent.wxcloudrun.service.UrlService;
import com.tencent.wxcloudrun.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExhibitScriptMapper exhibitScriptMapper;

    @Autowired
    private SubCommentMapper subCommentMapper;

    @Autowired
    private CommentImageMapper commentImageMapper;

    @Autowired
    private UrlService urlService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addComment(AddCommentRequest request, String openId) {

        ExhibitScript exhibitScript = exhibitScriptMapper.selectById(request.getExhibitScriptId());
        if (exhibitScript == null) throw new CommonException(CommonErrorCode.EXHIBIT_SCRIPT_NOT_EXIST);

        Script script = scriptMapper.selectById(exhibitScript.getScriptId());
        if (script == null) throw new CommonException(CommonErrorCode.SCRIPT_NOT_EXIST);

        Comment comment = Comment.builder()
                .userOpenId(openId)
                .exhibitScriptId(exhibitScript.getId())
                .content(request.getContent())
                .createTime(TimeUtil.getCurrentTimestamp())
                .likeCount(0)
                .score(request.getScore())
                .image(request.getImage())
                .isAnonymous(request.getIsAnonymous())
                .build();

        UpdateWrapper<ExhibitScript> exhibitScriptUpdateWrapper = new UpdateWrapper<>();
        exhibitScriptUpdateWrapper.eq("id", exhibitScript.getId()).set("discuss_count", exhibitScript.getDiscussCount() + 1);
        exhibitScriptMapper.update(null, exhibitScriptUpdateWrapper);
        UpdateWrapper<Script> scriptUpdateWrapper = new UpdateWrapper<>();
        scriptUpdateWrapper.eq("id", script.getId()).set("heat", script.getHeat() + 1);
        scriptMapper.update(null, scriptUpdateWrapper);

        commentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    public Page<BriefComment> getBriefScriptCommentList(GetScriptCommentListRequest request) {

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Comment> commentList = commentMapper.getCommentListByScriptId(request.getScriptId());
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);

        List<BriefComment> briefCommentList = new ArrayList<>();

        for (Comment comment : commentList) {

            User user = userMapper.getUserByUnionId(comment.getUserOpenId());

            BriefComment briefComment = BriefComment.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .likeCount(comment.getLikeCount())
                    .score(comment.getScore())
                    .image(comment.getImage())
                    .createTime(comment.getCreateTime())
                    .userOpenId(comment.getUserOpenId())
                    .userNickname(user.getNickname())
                    .userPortrait(user.getPortrait())
                    .isAnonymous(comment.getIsAnonymous())
                    .build();

            briefCommentList.add(briefComment);
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefCommentList);
    }

    @Override
    public Page<BriefCommentLandScript> getCommentLandCommentList(Integer pageNum, Integer pageSize, Long exhibitionId) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("discuss_count desc");
        QueryWrapper<ExhibitScript> exhibitScriptQueryWrapper = new QueryWrapper<>();
        exhibitScriptQueryWrapper.eq("exhibition_id", exhibitionId);
        List<ExhibitScript> exhibitScriptList = exhibitScriptMapper.selectList(exhibitScriptQueryWrapper);
        PageInfo<ExhibitScript> pageInfo = new PageInfo<>(exhibitScriptList);

        List<BriefCommentLandScript> briefCommentLandScriptList = new ArrayList<>();
        for (ExhibitScript exhibitScript : exhibitScriptList) {
            Script script = scriptMapper.selectById(exhibitScript.getScriptId());
            BriefCommentLandScript briefCommentLandScript = BriefCommentLandScript.builder()
                    .name(script.getName())
                    .scriptId(script.getId())
                    .cover(script.getFileId())
                    .discussCount(exhibitScript.getDiscussCount())
                    .exhibitScriptId(exhibitScript.getId())
                    .build();
            briefCommentLandScriptList.add(briefCommentLandScript);
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefCommentLandScriptList);

    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    public Long addSubComment(AddSubCommentRequest request, String openId) {

        User user = userMapper.getUserByUnionId(openId);

        Comment comment = commentMapper.selectById(request.getCommentId());
        if (comment == null) throw new CommonException(CommonErrorCode.COMMENT_NOT_EXIST);

        ExhibitScript exhibitScript = exhibitScriptMapper.selectById(comment.getExhibitScriptId());
        exhibitScript.setDiscussCount(exhibitScript.getDiscussCount() + 1);
        exhibitScriptMapper.updateById(exhibitScript);
        Script script = scriptMapper.selectById(exhibitScript.getScriptId());
        script.setHeat(script.getHeat() + 1);
        scriptMapper.updateById(script);


        SubComment subComment = SubComment.builder()
                .commentId(request.getCommentId())
                .content(request.getContent())
                .createTime(TimeUtil.getCurrentTimestamp())
                .commentUserId(user.getId())
                .build();

        subCommentMapper.insert(subComment);


        return subComment.getId();
    }

    @Override
    public Page<BriefSubComment> getBriefSubCommentList(GetSubCommentListRequest request) {

        QueryWrapper<SubComment> subCommentQueryWrapper = new QueryWrapper<>();
        subCommentQueryWrapper.eq("comment_id", request.getCommentId());
        subCommentQueryWrapper.orderByDesc("create_time");

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<SubComment> subCommentList = subCommentMapper.selectList(subCommentQueryWrapper);

        PageInfo<SubComment> pageInfo = new PageInfo<>(subCommentList);

        List<BriefSubComment> briefSubCommentList = new ArrayList<>();
        for (SubComment subComment : subCommentList) {
            User user = userMapper.selectById(subComment.getCommentUserId());
            BriefSubComment briefSubComment = BriefSubComment.builder()
                    .id(subComment.getId())
                    .content(subComment.getContent())
                    .createTime(subComment.getCreateTime())
                    .commentUserId(subComment.getCommentUserId())
                    .commentUserNickname(user.getNickname())
                    .commentUserPortrait(user.getPortrait())
                    .build();
            briefSubCommentList.add(briefSubComment);
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), briefSubCommentList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadCommentImage(Long commentId, MultipartFile file, String openId) {

        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) throw new CommonException(CommonErrorCode.COMMENT_NOT_EXIST);
        if (!comment.getUserOpenId().equals(openId)) throw new CommonException(CommonErrorCode.PERMISSION_DENIED);

        User user = userMapper.getUserByUnionId(openId);
        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

        String fileName = file.getOriginalFilename();
        String key = "blackbox-exhibition/comment" + "/" + user.getId() + "/" + fileName;

        COSClient cosClient = urlService.getCosClient();
        if (cosClient == null) throw new RuntimeException("get cos client is error!");

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(CommonConstants.COS_BUCKET, key, file.getInputStream(), new ObjectMetadata());
            cosClient.putObject(putObjectRequest);

        } catch (CosClientException | IOException e) {
            throw new RuntimeException("upload file to cos is error!");
        } finally {
            cosClient.shutdown();
        }

        String fileId = CommonConstants.FILE_ID_PREFIX + key;

        CommentImage commentImage = CommentImage.builder()
                .commentId(commentId)
                .fileId(fileId)
                .build();

        commentImageMapper.insert(commentImage);

        return fileId;
    }

    @Override
    public Page<CommentImage> getCommentImageList(Long commentId, Integer pageNum, Integer pageSize) {

        QueryWrapper<CommentImage> commentImageQueryWrapper = new QueryWrapper<>();
        commentImageQueryWrapper.eq("comment_id", commentId);

        PageHelper.startPage(pageNum, pageSize);
        List<CommentImage> commentImageList = commentImageMapper.selectList(commentImageQueryWrapper);
        return new Page<>(new PageInfo<>(commentImageList));

    }


}

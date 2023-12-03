package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefComment;
import com.tencent.wxcloudrun.dto.BriefCommentLandScript;
import com.tencent.wxcloudrun.dto.BriefSubComment;
import com.tencent.wxcloudrun.dto.request.AddCommentRequest;
import com.tencent.wxcloudrun.dto.request.AddSubCommentRequest;
import com.tencent.wxcloudrun.dto.request.GetScriptCommentListRequest;
import com.tencent.wxcloudrun.dto.request.GetSubCommentListRequest;
import com.tencent.wxcloudrun.entity.CommentImage;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {

    Long addComment(AddCommentRequest request, String openId);

    Page<BriefComment> getBriefScriptCommentList(GetScriptCommentListRequest request);

    Page<BriefCommentLandScript> getCommentLandCommentList (Integer pageNum, Integer pageSize, Long exhibitionId);

    Long addSubComment(AddSubCommentRequest request, String openId);

    Page<BriefSubComment> getBriefSubCommentList(GetSubCommentListRequest request);

    String uploadCommentImage(Long commentId, MultipartFile file, String openId);

    Page<CommentImage> getCommentImageList(Long commentId, Integer pageNum, Integer pageSize);


}

package com.tencent.wxcloudrun.service;

public interface CommentLikeService {

    Long likeComment(Long commentId, String openId);

    void cancelLikeComment(Long commentId, String openId);

}

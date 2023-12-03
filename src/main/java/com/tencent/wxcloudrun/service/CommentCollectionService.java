package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefCommentCollection;
import com.tencent.wxcloudrun.entity.CommentCollection;

public interface CommentCollectionService {

    Long collect(String openId, Long commentId);

    void cancelCollect(String openId, Long commentCollectionId);

    Page<BriefCommentCollection> getCollectionList(String openId, int pageSize, int pageNum);

    CommentCollection getCollection(Long commentCollectionId);

}

package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefScriptCollection;
import com.tencent.wxcloudrun.entity.ScriptCollection;

public interface ScriptCollectionService {

    void collect(String openId, Long scriptId);

    void cancelCollect(String openId, Long collectionId);

    Page<BriefScriptCollection> getCollectionList(String openId, int pageSize, int pageNum);

    ScriptCollection getCollection(Long collectionId);
}

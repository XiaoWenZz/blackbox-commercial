package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefExhibitScript;
import com.tencent.wxcloudrun.dto.BriefMerchantExhibitScript;
import com.tencent.wxcloudrun.dto.ExhibitScriptFlagForUser;
import com.tencent.wxcloudrun.dto.request.AddExhibitScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetExhibitScriptListRequest;
import com.tencent.wxcloudrun.dto.request.SendScriptMessageRequest;

import java.util.List;

public interface ExhibitScriptService {

    Page<BriefExhibitScript> getBriefExhibitScriptList(GetExhibitScriptListRequest request);

    void addExhibitScript(AddExhibitScriptRequest request);

    Page<BriefMerchantExhibitScript> getExhibitScriptList(Integer pageNum, Integer pageSize, Long exhibitionId, Long merchantId);

    List<String> sendScriptMessage(SendScriptMessageRequest request);

    Long flagExhibitScript(Long id, Integer attitude, String openId);

    ExhibitScriptFlagForUser getExhibitScriptFlagForUser(Long id, String openId);

}

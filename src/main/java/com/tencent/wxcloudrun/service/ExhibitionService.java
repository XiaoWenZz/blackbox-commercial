package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefExhibition;
import com.tencent.wxcloudrun.dto.request.AddExhibitionRequest;
import com.tencent.wxcloudrun.dto.request.UpdateExhibitionRequest;
import com.tencent.wxcloudrun.entity.Exhibition;

public interface ExhibitionService {

    Long addExhibition(AddExhibitionRequest request);

    Page<BriefExhibition> getExhibitionListByStatus(int pageSize, int pageNum, int status);

    void updateExhibition(UpdateExhibitionRequest request);

    Exhibition getExhibitionById(Long id);

}

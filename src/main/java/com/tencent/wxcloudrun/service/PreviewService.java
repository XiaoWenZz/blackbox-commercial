package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddPreviewRequest;
import com.tencent.wxcloudrun.entity.Preview;

import java.util.List;

public interface PreviewService {

    Long addPreview(String openId, AddPreviewRequest request);

    void scheduledStartPreview(Preview preview);

    void scheduledEndPreview(Preview preview);

    void startPreview(Long previewId);

    void endPreview(Long previewId);

    void cancelScheduledStartPreview(Long previewId);

    Page<Preview> getRunningPreviewList(Integer pageNum, Integer pageSize, Long exhibitionId);

    Page<Preview> getExhibitionPreviewList(Integer pageNum, Integer pageSize, Long exhibitionId);

    List<Integer> getPreviewCount(Long exhibitionId, Integer exhibitionDateId);

}

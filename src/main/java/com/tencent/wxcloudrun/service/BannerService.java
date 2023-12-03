package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddBannerRequest;
import com.tencent.wxcloudrun.dto.request.UpdateBannerRequest;
import com.tencent.wxcloudrun.entity.Banner;

public interface BannerService {

    Banner getBanner(Long bannerId);

    Page<Banner> getExhibitionBannerList(int pageSize, int pageNum, Long exhibitionId);

    Long addBanner(AddBannerRequest request);

    void updateBanner(UpdateBannerRequest request);

    Page<Banner> getHomepageExhibitionBannerList(int pageSize, int pageNum);

    Page<Banner> getHomepageScriptBannerList(int pageSize, int pageNum);

}

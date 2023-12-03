package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddBannerRequest;
import com.tencent.wxcloudrun.dto.request.UpdateBannerRequest;
import com.tencent.wxcloudrun.entity.Banner;
import com.tencent.wxcloudrun.mapper.BannerMapper;
import com.tencent.wxcloudrun.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public Banner getBanner(Long bannerId) {
        Banner banner = bannerMapper.selectById(bannerId);
        if (banner == null) throw new CommonException(CommonErrorCode.BANNER_NOT_EXIST);

        return banner;
    }

    @Override
    public Page<Banner> getExhibitionBannerList(int pageSize, int pageNum, Long exhibitionId) {

        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(bannerMapper.getBannerListByExhibitionId(exhibitionId)));

    }

    @Override
    public Long addBanner(AddBannerRequest request) {


        Banner banner = Banner.builder()
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .type(request.getType())
                .build();

        if (request.getType() == 1) {
            if (request.getExhibitionId() == null) throw new CommonException(CommonErrorCode.EXHIBITION_ID_IS_NULL);
            banner.setExhibitionId(request.getExhibitionId());
        }

        if (request.getType() == 2) {
            if (request.getScriptId() == null) throw new CommonException(CommonErrorCode.SCRIPT_ID_IS_NULL);
            banner.setScriptId(request.getScriptId());
        }


        bannerMapper.insert(banner);
        return banner.getId();
    }

    @Override
    public void updateBanner(UpdateBannerRequest request) {
        Banner banner = bannerMapper.selectById(request.getBannerId());
        if (banner == null) throw new CommonException(CommonErrorCode.BANNER_NOT_EXIST);

        if (request.getDescription() != null) banner.setDescription(request.getDescription());
        if (request.getImageUrl() != null) banner.setImageUrl(request.getImageUrl());

        if (request.getExhibitionId() != null) {
            if (banner.getType() != 1) throw new CommonException(CommonErrorCode.BANNER_TYPE_ERROR);
            banner.setExhibitionId(request.getExhibitionId());
        }

        if (request.getScriptId() != null) {
            if (banner.getType() != 2) throw new CommonException(CommonErrorCode.BANNER_TYPE_ERROR);
            banner.setScriptId(request.getScriptId());
        }

        if (request.getPriority() != null) banner.setPriority(request.getPriority());

        bannerMapper.updateById(banner);
    }

    @Override
    public Page<Banner> getHomepageExhibitionBannerList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(bannerMapper.getHomepageExhibitionBannerList()));
    }

    @Override
    public Page<Banner> getHomepageScriptBannerList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(bannerMapper.getHomepageScriptBannerList()));
    }
}

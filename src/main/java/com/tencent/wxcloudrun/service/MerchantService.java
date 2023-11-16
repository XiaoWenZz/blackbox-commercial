package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.request.AddMerchantRequest;
import com.tencent.wxcloudrun.dto.request.UpdateMerchantRequest;
import com.tencent.wxcloudrun.entity.Merchant;

public interface MerchantService {

    String addMerchant(AddMerchantRequest request);

    void setLogo(Long merchantId, String logo);

    Merchant getMerchantById(Long merchantId);

    void updateMerchant(UpdateMerchantRequest request);

    void refreshPublishCount();
}

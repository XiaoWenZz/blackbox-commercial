package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.dto.request.AddMerchantRequest;
import com.tencent.wxcloudrun.dto.request.UpdateMerchantRequest;
import com.tencent.wxcloudrun.entity.Merchant;
import com.tencent.wxcloudrun.mapper.MerchantMapper;
import com.tencent.wxcloudrun.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public String addMerchant(AddMerchantRequest request) {

        String adminKey = request.getAdminKey();
        String memberKey = request.getMemberKey();

        if (merchantMapper.getMerchantByKey(adminKey) != null || merchantMapper.getMerchantByKey(memberKey) != null)
            throw new CommonException(CommonErrorCode.KEY_ALREADY_EXIST);

        Merchant merchant = Merchant.builder()
                .memberKey(request.getMemberKey())
                .adminKey(request.getAdminKey())
                .province(request.getProvince())
                .city(request.getCity())
                .region(request.getRegion())
                .name(request.getName())
                .logo(request.getLogo())
                .build();

        merchantMapper.insert(merchant);

        merchant.setMerchantNum(String.format("%03d", merchant.getId()));
        merchantMapper.updateById(merchant);

        return merchant.getMerchantNum();

    }

    @Override
    public void setLogo(Long merchantId, String logo) {

        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        merchant.setLogo(logo);
        merchantMapper.updateById(merchant);

    }

    @Override
    public Merchant getMerchantById(Long merchantId) {

        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        return merchant;
    }

    @Override
    public void updateMerchant(UpdateMerchantRequest request) {

        Merchant merchant = merchantMapper.selectById(request.getId());
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        if (request.getProvince() != null) merchant.setProvince(request.getProvince());
        if (request.getCity() != null) merchant.setCity(request.getCity());
        if (request.getRegion() != null) merchant.setRegion(request.getRegion());
        if (request.getName() != null) merchant.setName(request.getName());
        if (request.getLogo() != null) merchant.setLogo(request.getLogo());

        merchantMapper.updateById(merchant);

    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void refreshPublishCount() {
        Iterable<Merchant> merchants = merchantMapper.selectList(null);

        for (Merchant merchant : merchants) {
            merchant.setPublishCount(1);
            merchantMapper.updateById(merchant);
        }
    }

}

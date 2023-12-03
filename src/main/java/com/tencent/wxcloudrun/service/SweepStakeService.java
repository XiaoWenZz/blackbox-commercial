package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefMerchant;
import com.tencent.wxcloudrun.dto.UserWithCodeCount;
import com.tencent.wxcloudrun.entity.SweepStake;

import java.util.List;

public interface SweepStakeService {

    List<String> sweepStake(Long userId, Long merchantId, Long exhibitionId);

    Page<SweepStake> getSweepStakeList(Long userId, int pageSize, int pageNum, Long exhibitionId);

    void openLottery(Long bonus, int count, Long exhibitionId);

    String sweep(Long userId, Long stakeId, Long exhibitionId);

    String signUpSweepStake(Long userId, Long exhibitionId);

    Page<BriefMerchant> getCollectedMerchantIdByUserId(Long userId, int pageSize, int pageNum, Long exhibitionId);

    Page<SweepStake> getSweepStakeListByStakeId(Long stakeId, int pageSize, int pageNum);

    float getUserRate(Long userId, Long exhibitionId, int count);

    Page<UserWithCodeCount> getRankedList(int pageSize, int pageNum, Long exhibitionId);

    Page<SweepStake> getSweepStakeListByMerchantId(Long merchantId, int pageSize, int pageNum, Long exhibitionId);



}

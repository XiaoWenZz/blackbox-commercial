package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.entity.Stake;

import java.text.ParseException;

public interface StakeService {

    Long addStake(Long userId, String startTime, String endTime, int quota) throws ParseException;

    void scheduleStartStake(Stake stake);

    void scheduleEndStake(Stake stake);

    void cancelScheduleStartStake(Long stakeId);

    void startStake(Long stakeId);

    void endStake(Long stakeId);

    Stake getStakeById(Long stakeId);

    Page<Stake> getStakeList(int pageSize, int pageNum);

    int getStakeCountByTime(String startTime);

    Page<Stake> getMerchantStakeList(Long merchantId, int pageSize, int pageNum);

}

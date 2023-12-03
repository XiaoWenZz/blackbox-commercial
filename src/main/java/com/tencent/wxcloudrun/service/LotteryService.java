package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.LotteryInfo;
import com.tencent.wxcloudrun.entity.Bonus;

public interface LotteryService {

    Page<LotteryInfo> getLotteryList(int pageNum, int pageSize, Long exhibitionId);

    Bonus getLottery(Long userId, Long exhibitionId);

    Bonus getUserLotteryByExhibitionId(Long userId, Long exhibitionId);

    Page<Bonus> getUserLotteryList(int pageNum, int pageSize, Long userId);
}

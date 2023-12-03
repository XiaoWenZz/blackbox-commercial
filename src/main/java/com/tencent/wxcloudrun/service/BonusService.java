package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddBonusRequest;
import com.tencent.wxcloudrun.dto.request.UpdateBonusRequest;
import com.tencent.wxcloudrun.entity.Bonus;

import java.util.List;

public interface BonusService {

    Long addBonus(AddBonusRequest addBonusRequest);

    Bonus getBonus(Long bonusId);

    Page<Bonus> getBonusList(int pageNum, int pageSize, Long exhibitionId);

    void updateBonus(UpdateBonusRequest request);

    List<Bonus> getUserBonusListByExhibitionId(Long userId, Long exhibitionId);

}

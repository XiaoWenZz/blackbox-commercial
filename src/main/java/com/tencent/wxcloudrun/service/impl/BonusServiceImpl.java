package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.request.AddBonusRequest;
import com.tencent.wxcloudrun.dto.request.UpdateBonusRequest;
import com.tencent.wxcloudrun.entity.Bonus;
import com.tencent.wxcloudrun.mapper.BonusMapper;
import com.tencent.wxcloudrun.mapper.SweepStakeMapper;
import com.tencent.wxcloudrun.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private SweepStakeMapper sweepStakeMapper;

    @Override
    public Long addBonus(AddBonusRequest addBonusRequest) {

        Bonus bonus = Bonus.builder()
                .image(addBonusRequest.getImage())
                .level(addBonusRequest.getLevel())
                .name(addBonusRequest.getName())
                .image(addBonusRequest.getImage())
                .exhibitionId(addBonusRequest.getExhibitionId())
                .build();

        bonusMapper.insert(bonus);
        return bonus.getId();

    }

    @Override
    public Bonus getBonus(Long bonusId) {

        Bonus bonus = bonusMapper.selectById(bonusId);
        if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

        return bonus;
    }

    @Override
    public Page<Bonus> getBonusList(int pageNum, int pageSize, Long exhibitionId) {

        PageHelper.startPage(pageNum, pageSize);

        return new Page<>(new PageInfo<>(bonusMapper.getBonusListByExhibitionId(exhibitionId)));
    }

    @Override
    public void updateBonus(UpdateBonusRequest request) {

        Bonus bonus = bonusMapper.selectById(request.getId());
        if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

        if (request.getImage() != null) bonus.setImage(request.getImage());
        if (request.getLevel() != null) bonus.setLevel(request.getLevel());
        if (request.getName() != null) bonus.setName(request.getName());
        if (request.getExhibitionId() != null) bonus.setExhibitionId(request.getExhibitionId());

        bonusMapper.updateById(bonus);

    }

    @Override
    public List<Bonus> getUserBonusListByExhibitionId(Long userId, Long exhibitionId) {

        List<String> codes = sweepStakeMapper.getUserAllCodesByExhibitionId(userId, exhibitionId);

        List<Bonus> bonusList = new ArrayList<>();

        for (String code : codes) {
            Bonus bonus = bonusMapper.getBonusByCode(code);
            if (bonus != null) bonusList.add(bonus);
        }

        return bonusList;
    }

}

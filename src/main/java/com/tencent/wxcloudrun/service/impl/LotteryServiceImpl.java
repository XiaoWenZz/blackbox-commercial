package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.LotteryInfo;
import com.tencent.wxcloudrun.entity.Bonus;
import com.tencent.wxcloudrun.entity.Lottery;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.BonusMapper;
import com.tencent.wxcloudrun.mapper.LotteryMapper;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    private LotteryMapper lotteryMapper;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<LotteryInfo> getLotteryList(int pageNum, int pageSize, Long exhibitionId) {

        PageHelper.startPage(pageNum, pageSize);
        List<Lottery> lotteries = lotteryMapper.getLotteryByExhibitionId(exhibitionId);
        if (lotteries == null) return null;
        PageInfo<Lottery> pageInfo = new PageInfo<>(lotteries);

        List<LotteryInfo> lotteryInfos = new ArrayList<>();

        for (Lottery lottery : lotteries) {
            Bonus bonus = bonusMapper.selectById(lottery.getBonusId());
            if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

            User user = userMapper.selectById(lottery.getUserId());
            if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);

            LotteryInfo lotteryInfo = LotteryInfo.builder()
                    .id(lottery.getId())
                    .userId(user.getId())
                    .userType(user.getUserType())
                    .nickname(user.getNickname())
                    .publisherName(user.getPublisherName())
                    .province(user.getProvince())
                    .city(user.getCity())
                    .region(user.getRegion())
                    .telephone(user.getTelephone())
                    .exhibitionId(lottery.getExhibitionId())
                    .code(lottery.getCode())
                    .bonusId(lottery.getBonusId())
                    .bonusName(bonus.getName())
                    .build();

            lotteryInfos.add(lotteryInfo);
        }
        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), lotteryInfos);
    }

    @Override
    public Bonus getLottery(Long userId, Long exhibitionId) {

        Lottery lottery = lotteryMapper.getLotteryByUserIdAndExhibitionId(userId, exhibitionId);
        if (lottery == null) return null;

        Bonus bonus = bonusMapper.selectById(lottery.getBonusId());
        if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

        return bonus;
    }

    @Override
    public Bonus getUserLotteryByExhibitionId(Long userId, Long exhibitionId) {
        Lottery lottery = lotteryMapper.getLotteryByUserIdAndExhibitionId(userId, exhibitionId);
        if (lottery == null) return null;

        Bonus bonus = bonusMapper.selectById(lottery.getBonusId());
        if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

        return bonus;
    }

    @Override
    public Page<Bonus> getUserLotteryList(int pageNum, int pageSize, Long userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Lottery> lotteries = lotteryMapper.getLotteryListByUserId(userId);
        if (lotteries == null) return null;
        PageInfo<Lottery> pageInfo = new PageInfo<>(lotteries);

        List<Bonus> bonuses = new ArrayList<>();
        for (Lottery lottery : lotteries) {
            Bonus bonus = bonusMapper.selectById(lottery.getBonusId());
            if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);
            bonuses.add(bonus);
        }
        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), bonuses);
    }

}

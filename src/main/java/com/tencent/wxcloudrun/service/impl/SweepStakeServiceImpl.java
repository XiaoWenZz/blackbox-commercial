package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefMerchant;
import com.tencent.wxcloudrun.dto.UserWithCodeCount;
import com.tencent.wxcloudrun.entity.*;
import com.tencent.wxcloudrun.mapper.*;
import com.tencent.wxcloudrun.service.SweepStakeService;
import com.tencent.wxcloudrun.util.SessionUtils;
import com.tencent.wxcloudrun.util.TimeUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SweepStakeServiceImpl implements SweepStakeService {


    @Autowired
    private StakeMapper stakeMapper;

    @Autowired
    private SweepStakeMapper sweepStakeMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExhibitionMapper exhibitionMapper;

    @Autowired
    private LotteryMapper lotteryMapper;

    @Autowired
    private BonusMapper bonusMapper;

    @Override
    public List<String> sweepStake(Long userId, Long merchantId, Long exhibitionId) {

        Stake stake = stakeMapper.selectById(merchantId);
        if (stake == null) throw new CommonException(CommonErrorCode.STAKE_NOT_EXIST);

        Exhibition exhibition = exhibitionMapper.selectById(exhibitionId);
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);
        String header = exhibition.getExhibitionAbbreviation();

        if (stake.getStatus() == 0) throw new CommonException(CommonErrorCode.STAKE_NOT_BEGIN);
        if (stake.getStatus() == 2) throw new CommonException(CommonErrorCode.STAKE_ALREADY_END);

        int codeNum = 0;
        int codeLeft = stake.getQuotaLeft();

        if (codeLeft <= 2) codeNum = RandomUtils.nextInt(1, codeLeft);
        else codeNum = RandomUtils.nextInt(1, 3);

        codeLeft = codeLeft - codeNum;
        if (codeLeft == 0) stake.setStatus(2);
        stakeMapper.updateById(stake);

        String code1 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        while (sweepStakeMapper.selectIdByCode(code1) != null) {
            code1 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        }


        if (codeNum == 1) {
            SweepStake sweepStake = SweepStake.builder()
                    .userId(userId)
                    .merchantId(merchantId)
                    .code1(code1)
                    .exhibitionId(exhibitionId)
                    .codeNum(1)
                    .createTime(TimeUtil.getCurrentTimestamp())
                    .build();
            sweepStakeMapper.insert(sweepStake);
            return Arrays.asList(code1);
        }

        else if (codeNum == 2){
            String code2 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            while (sweepStakeMapper.selectIdByCode(code2) != null || code2.equals(code1)) {
                code2 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            }
            SweepStake sweepStake = SweepStake.builder()
                    .userId(userId)
                    .merchantId(merchantId)
                    .code1(code1)
                    .code2(code2)
                    .exhibitionId(exhibitionId)
                    .codeNum(2)
                    .createTime(TimeUtil.getCurrentTimestamp())
                    .build();

            sweepStakeMapper.insert(sweepStake);
            return Arrays.asList(code1, code2);
        }

        else {
            String code2 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            while (sweepStakeMapper.selectIdByCode(code2) != null || code2.equals(code1)) {
                code2 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            }
            String code3 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            while (sweepStakeMapper.selectIdByCode(code3) != null || code3.equals(code1) || code3.equals(code2)) {
                code3 = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
            }
            SweepStake sweepStake = SweepStake.builder()
                    .userId(userId)
                    .merchantId(merchantId)
                    .code1(code1)
                    .code2(code2)
                    .code3(code3)
                    .exhibitionId(exhibitionId)
                    .codeNum(3)
                    .createTime(TimeUtil.getCurrentTimestamp())
                    .build();

            sweepStakeMapper.insert(sweepStake);
            return Arrays.asList(code1, code2, code3);


        }
    }

    @Override
    public Page<SweepStake> getSweepStakeList(Long userId, int pageSize, int pageNum, Long exhibitionId) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(sweepStakeMapper.getUserSweepStakeListByExhibitionId(userId, exhibitionId)));
    }

    @Override
    public void openLottery(Long bonusId, int count, Long exhibitionId) {

        Exhibition exhibition = exhibitionMapper.selectById(exhibitionId);
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);

        Bonus bonus = bonusMapper.selectById(bonusId);
        if (bonus == null) throw new CommonException(CommonErrorCode.BONUS_NOT_EXIST);

        //Admin验证有问题，暂且先这样写
        String openId = sessionUtils.getOpenId();
        User user0 = userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId));
        if (user0 == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);
        if (user0.getType() != 1) throw new CommonException(CommonErrorCode.USER_NOT_ADMIN);

        List<String> list = sweepStakeMapper.getAllCodesByExhibitionId(exhibitionId);

        for (int i = 0; i < count; i++) {

            int index = RandomUtils.nextInt(0, list.size() - 1);
            String code = list.get(index);
            Long userId = sweepStakeMapper.getUserIdByCode(code);
            User user = userMapper.selectById(userId);

            while ((lotteryMapper.getLotteryByUserIdAndExhibitionId(userId, exhibitionId) != null) || sweepStakeMapper.getUserCodeCountByExhibitionId(userId, exhibitionId) < 20 || user.getUserType() != null || user.getChipId() == null) {
                index = RandomUtils.nextInt(0, list.size() - 1);
                code = list.get(index);
                userId = sweepStakeMapper.getUserIdByCode(code);
                user = userMapper.selectById(userId);
            }

            Lottery lottery = Lottery.builder()
                    .userId(userId)
                    .exhibitionId(exhibitionId)
                    .code(code)
                    .bonusId(bonusId)
                    .build();

            lotteryMapper.insert(lottery);

        }

    }


    @Override
    public String sweep(Long userId, Long merchantId, Long exhibitionId) {

        if (sweepStakeMapper.getCodeByUserIdAndMerchantIdAndExhibitionId(userId, merchantId, exhibitionId) != null) throw new CommonException(CommonErrorCode.SWEEP_ALREADY);

        Exhibition exhibition = exhibitionMapper.selectById(exhibitionId);
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);


        String header = exhibition.getExhibitionAbbreviation();

        String code = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        while (sweepStakeMapper.selectIdByCode(code) != null) {
            code = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        }
        SweepStake sweepStake = SweepStake.builder()
                .userId(userId)
                .merchantId(merchantId)
                .exhibitionId(exhibitionId)
                .code1(code)
                .createTime(TimeUtil.getCurrentTimestamp())
                .build();
        sweepStakeMapper.insert(sweepStake);

        return code;
    }

    @Override
    public String signUpSweepStake(Long userId, Long exhibitionId) {

        Exhibition exhibition = exhibitionMapper.selectById(exhibitionId);
        if (exhibition == null) throw new CommonException(CommonErrorCode.EXHIBITION_NOT_EXIST);

        String header = exhibition.getExhibitionAbbreviation();
        String code = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        while (sweepStakeMapper.selectIdByCode(code) != null) {
            code = header + IdUtil.fastSimpleUUID().replaceAll("-", "").substring(0, 4);
        }
        SweepStake sweepStake = SweepStake.builder()
                .userId(userId)
                .code1(code)
                .codeNum(1)
                .createTime(TimeUtil.getCurrentTimestamp())
                .exhibitionId(exhibitionId)
                .build();
        sweepStakeMapper.insert(sweepStake);

        User user = userMapper.selectById(userId);
        user.setIsUpdate(1);
        userMapper.updateById(user);

        return code;

    }

    @Override
    public Page<BriefMerchant> getCollectedMerchantIdByUserId(Long userId, int pageSize, int pageNum, Long exhibitionId) {

        PageHelper.startPage(pageNum, pageSize);
        List<Long> list = sweepStakeMapper.getCollectedMerchantIdByUserIdAndExhibitionId(userId, exhibitionId);
        if (list == null) return null;

        PageInfo<Long> pageInfo = new PageInfo<>(list);

        List<BriefMerchant> merchants = new ArrayList<>();
        for (Long l : list) {
            BriefMerchant merchant = merchantMapper.getBriefMerchantById(l);
            merchants.add(merchant);
        }

        return new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), pageInfo.getPages(), merchants);
    }

    @Override
    public Page<SweepStake> getSweepStakeListByStakeId(Long stakeId, int pageSize, int pageNum) {
        Stake stake = stakeMapper.selectById(stakeId);
        if (stake == null) throw new CommonException(CommonErrorCode.STAKE_NOT_EXIST);

        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(sweepStakeMapper.getSweepStakeListByStakeId(stakeId)));
    }


    @Override
    public Page<UserWithCodeCount> getRankedList(int pageSize, int pageNum, Long exhibitionId) {

        PageHelper.startPage(pageNum, pageSize);
        List<UserWithCodeCount> userWithCodeCounts = sweepStakeMapper.getRankedUsersByCodeCount(exhibitionId);

//        List<Long> list = sweepStakeMapper.getUserIdsByExhibitionId(exhibitionId);
//        List<UserWithCodeCount> userWithCodeCounts = new ArrayList<>();
//
//        for (Long l : list) {
//            UserWithCodeCount userWithCodeCount = new UserWithCodeCount();
//            int count = sweepStakeMapper.getUserCodeCountByExhibitionId(l, exhibitionId);
//            userWithCodeCount.setUserId(l);
//            userWithCodeCount.setCodeCount(count);
//            userWithCodeCounts.add(userWithCodeCount);
//        }

        int rank = pageSize * (pageNum - 1) + 1;
        for (UserWithCodeCount u : userWithCodeCounts) {
            u.setRank(rank++);
        }

        return new Page<>(new PageInfo<>(userWithCodeCounts));
    }

    @Override
    public Page<SweepStake> getSweepStakeListByMerchantId(Long merchantId, int pageSize, int pageNum, Long exhibitionId) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(sweepStakeMapper.getSweepStakeListByMerchantIdAndExhibitionId(merchantId, exhibitionId)));
    }

    @Override
    public float getUserRate(Long userId, Long exhibitionId, int count) {
        int userCount = sweepStakeMapper.getUserCodeCountByExhibitionId(userId, exhibitionId);
        int totalCount = sweepStakeMapper.getCodeCountByExhibitionId(exhibitionId);
        float rate =  (float) userCount / totalCount;
        return 1 - (float) Math.pow(1 - rate, count);
    }


}

package com.tencent.wxcloudrun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.entity.Merchant;
import com.tencent.wxcloudrun.entity.Stake;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.MerchantMapper;
import com.tencent.wxcloudrun.mapper.StakeMapper;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.service.StakeService;
import com.tencent.wxcloudrun.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StakeServiceImpl implements StakeService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StakeMapper stakeMapper;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private MerchantMapper merchantMapper;

    private final Map<Long, Runnable> startTasks = new HashMap<>();
    private final Map<Long, Runnable> endTasks = new HashMap<>();

    @Override
    public Long addStake(Long userId, String startTime, String endTime, int quota) throws ParseException {

        User user = userMapper.selectById(userId);
        if (user.getUserType() != 2) throw new CommonException(CommonErrorCode.USER_NOT_PUBLISHER);

        Merchant merchant = merchantMapper.selectById(user.getPublisherId());
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        if (merchant.getQuota() < quota) throw new CommonException(CommonErrorCode.QUOTA_NOT_ENOUGH);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date time1 = format2.parse(startTime);

        List<Stake> currentStakes = stakeMapper.getMerchantStakeList(merchant.getId());

        for (Stake stake : currentStakes) {
            Date time2 = format1.parse(stake.getEndTime());
            if (time1.before(time2)) throw new CommonException(CommonErrorCode.STAKE_TIME_CONFLICT);
        }

        Stake stake = Stake.builder()
                .startTime(startTime)
                .endTime(endTime)
                .merchantId(merchant.getId())
                .merchantName(merchant.getName())
                .quota(quota)
                .quotaLeft(quota)
                .status(0)
                .createTime(TimeUtil.getCurrentTimestamp())
                .build();

        stakeMapper.insert(stake);
        scheduleStartStake(stake);
        scheduleEndStake(stake);

        merchant.setQuota(merchant.getQuota() - quota);
        merchantMapper.updateById(merchant);

        return stake.getId();
    }

    @Override
    public void scheduleStartStake(Stake stake) {
        Runnable startStake = () -> startStake(stake.getId());
        Date startTime = Date.from(LocalDateTime.parse(stake.getStartTime()).atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(startStake, startTime);
        startTasks.put(stake.getId(), startStake);
    }

    @Override
    public void scheduleEndStake(Stake stake) {
        Runnable endStake = () -> endStake(stake.getId());
        Date endTime = Date.from(LocalDateTime.parse(stake.getEndTime()).atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(endStake, endTime);
        endTasks.put(stake.getId(), endStake);
    }

    @Override
    public void cancelScheduleStartStake(Long stakeId) {
        Runnable startStake = startTasks.get(stakeId);
        Runnable endStake = endTasks.get(stakeId);

        if (startStake != null) taskScheduler.getScheduledThreadPoolExecutor().remove(startStake);
        if (endStake != null) taskScheduler.getScheduledThreadPoolExecutor().remove(endStake);

        Stake stake = stakeMapper.selectById(stakeId);
        if (stake.getStatus() == 1) throw new CommonException(CommonErrorCode.STAKE_ALREADY_BEGIN);
        if (stake.getStatus() == 2) throw new CommonException(CommonErrorCode.STAKE_ALREADY_END);
        if (stake.getStatus() == 3) throw new CommonException(CommonErrorCode.STAKE_ALREADY_CANCEL);

        stake.setStatus(3);
        stakeMapper.updateById(stake);

        Merchant merchant = merchantMapper.selectById(stake.getMerchantId());
        merchant.setQuota(merchant.getQuota() + stake.getQuotaLeft());
        merchantMapper.updateById(merchant);

        startTasks.remove(stakeId);
        endTasks.remove(stakeId);

    }

    @Override
    public void startStake(Long stakeId) {
        Stake stake = stakeMapper.selectById(stakeId);
        if (stake == null) throw new CommonException(CommonErrorCode.STAKE_NOT_EXIST);

        if (stake.getStatus() == 0) {
            stake.setStatus(1);
            stakeMapper.updateById(stake);
        }

        else if (stake.getStatus() == 2) throw new CommonException(CommonErrorCode.STAKE_ALREADY_END);
        else throw new CommonException(CommonErrorCode.STAKE_ALREADY_BEGIN);

        startTasks.remove(stakeId);
    }

    @Override
    public void endStake(Long stakeId) {
        Stake stake = stakeMapper.selectById(stakeId);
        if (stake == null) throw new CommonException(CommonErrorCode.STAKE_NOT_EXIST);

        if (stake.getStatus() == 1){
            stake.setStatus(2);
            stakeMapper.updateById(stake);

            if (stake.getQuotaLeft() != 0){
                Merchant merchant = merchantMapper.selectById(stake.getMerchantId());
                if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

                merchant.setQuota(merchant.getQuota() + stake.getQuotaLeft());
                merchantMapper.updateById(merchant);
            }
        }
        else if (stake.getStatus() == 2) throw new CommonException(CommonErrorCode.STAKE_ALREADY_END);
        else throw new CommonException(CommonErrorCode.STAKE_NOT_BEGIN);

        endTasks.remove(stakeId);
    }

    @Override
    public Stake getStakeById(Long stakeId) {
        Stake stake = stakeMapper.selectById(stakeId);
        if (stake == null) throw new CommonException(CommonErrorCode.STAKE_NOT_EXIST);
        return stake;
    }

    @Override
    public Page<Stake> getStakeList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(stakeMapper.getStakeList()));
    }

    @Override
    public int getStakeCountByTime(String startTime) {
        return stakeMapper.getStakeCountByTime(startTime);
    }

    @Override
    public Page<Stake> getMerchantStakeList(Long merchantId, int pageSize, int pageNum) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        PageHelper.startPage(pageNum, pageSize);
        return new Page<>(new PageInfo<>(stakeMapper.getMerchantStakeList(merchantId)));
    }


}

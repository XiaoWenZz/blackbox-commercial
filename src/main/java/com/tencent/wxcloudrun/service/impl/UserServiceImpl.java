package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.YmlConfig;
import com.tencent.wxcloudrun.dto.BriefUser;
import com.tencent.wxcloudrun.dto.SessionData;
import com.tencent.wxcloudrun.dto.WxSession;
import com.tencent.wxcloudrun.dto.request.UpdateUserRequest;
import com.tencent.wxcloudrun.entity.Merchant;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.MerchantMapper;
import com.tencent.wxcloudrun.mapper.UserMapper;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private YmlConfig ymlConfig;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private RedisUtils redisUtil;




    public User getUserById(Long id) throws CommonException {
        User user = userMapper.selectById(id);

        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);
        return user;
    }

    public User getUserByOpenId(String openId) throws CommonException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) throw new CommonException(CommonErrorCode.USER_NOT_EXIST);
        return user;
    }

    public SessionData getSessionDataFromRedis(String openId) throws CommonException {
        return (SessionData) redisUtil.get(openId);
    }


    @Override
    public Page<BriefUser> getBriefUserList(int pageSize, int pageNum, Long userId) throws CommonException {
        if (userMapper.selectById(userId).getType() != 1)
            throw new CommonException(CommonErrorCode.USER_NOT_ADMIN);
        PageHelper.startPage(pageNum, pageSize, "id asc");
        return new Page<>(new PageInfo<>(userMapper.getBriefUserList()));
    }

    @Transactional(rollbackFor = CommonException.class)
    @Override
    public void updateUser(String openId, UpdateUserRequest updateUserRequest) {
        User user = userMapper.getUserByOpenId(openId);

        if (updateUserRequest.getNickname() != null) user.setNickname(updateUserRequest.getNickname());
        if (updateUserRequest.getTelephone() != null) user.setTelephone(updateUserRequest.getTelephone());
        if (updateUserRequest.getPublisherName() != null) user.setPublisherName(updateUserRequest.getPublisherName());
        if (updateUserRequest.getWechatId() != null) user.setWechatId(updateUserRequest.getWechatId());
        if (updateUserRequest.getProvince() != null) user.setProvince(updateUserRequest.getProvince());
        if (updateUserRequest.getCity() != null) user.setCity(updateUserRequest.getCity());
        if (updateUserRequest.getRegion() != null) user.setRegion(updateUserRequest.getRegion());
        if (updateUserRequest.getPortrait() != null) user.setPortrait(updateUserRequest.getPortrait());

        if (updateUserRequest.getChipId() != null) {
            user.setChipId(updateUserRequest.getChipId());
        }

        if (userMapper.updateById(user) == 0) {
            throw new CommonException(CommonErrorCode.UPDATE_FAIL);
        }
    }

    @Override
    public Page<BriefUser> searchUser(int pageNum, int pageSize, String content) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper.and(qw -> qw.like("account_num", content).or().like("nickname", content));

        userQueryWrapper.select("account_num","nickname","portrait","type","account_status","downloads_available","can_modify","can_upload","career","position");
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectList(userQueryWrapper);
        List<BriefUser> briefUserList = new ArrayList<>();

        for (User user : users){
            briefUserList.add(BriefUser.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .portrait(user.getPortrait())
                    .build());
        }
        return new Page<>(new PageInfo<>(briefUserList));
    }

    @Override
    public SessionData login() {
//        //shadow test
//        if (CommonConstants.SHADOW_TEST.equals(code)) {
//            sessionUtils.setSessionId(CommonConstants.SHADOW_TEST);
//            return new SessionData();
//        }

//        WxSession wxSession = Optional.ofNullable(
//                        getWxSessionByCode(code))
//                .orElse(new WxSession());
//
//        checkWxSession(wxSession);

        String openId = sessionUtils.getOpenId();
        String unionId = sessionUtils.getUnionId();

        User user = userMapper.getUserByOpenId(openId);

        if (user != null) {
            sessionUtils.setOpenId(user.getOpenId());
            return new SessionData(user);
        }

        User user1 = User.builder()
                .openId(openId)
                .unionId(unionId)
                .createTime(TimeUtil.getCurrentTimestamp())
                .nickname("黑匣看展用户")
                .isUpdate(0)
                .build();

        userMapper.insert(user1);

        return new SessionData(user1);
    }

    @Override
    public WxSession getWxSessionByCode(String code) {
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", ymlConfig.getAppId());
//        requestUrlParam.put("appid", "wx22fa1182d4e66c4a");
        //小程序secret
        requestUrlParam.put("secret", ymlConfig.getAppSecret());
//        requestUrlParam.put("secret", "200e82982f7ec2a2812fc3ae9f2d5f15");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数：授权类型
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpUtil.get(CommonConstants.WX_SESSION_REQUEST_URL, requestUrlParam);
//        String result = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", requestUrlParam);

        return JsonUtil.toObject(result, WxSession.class);
    }

    @Override
    public void checkWxSession(WxSession wxSession) {
        if (wxSession.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY, wxSession.getErrmsg());
            AssertUtil.isFalse(40029 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_INVALID_CODE, wxSession.getErrmsg());
            AssertUtil.isFalse(45011 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_FREQUENCY_REFUSED, wxSession.getErrmsg());
            AssertUtil.isTrue(wxSession.getErrcode() == 0, CommonErrorCode.WX_LOGIN_UNKNOWN_ERROR, wxSession.getErrmsg());
        }
    }

    @Override
    public void BindMerchant(String openId, String key) {

        Merchant merchant = merchantMapper.selectById(merchantMapper.getMerchantByKey(key));
        if (merchant == null) throw new CommonException(CommonErrorCode.MERCHANT_NOT_EXIST);

        User user = userMapper.getUserByOpenId(openId);
        user.setPublisherId(merchant.getId());
        user.setPublisherName(merchant.getName());

        if (merchantMapper.getMerchantIdByAdminKey(key) != null) user.setUserType(2);
        else user.setUserType(1);

        user.setProvince(merchant.getProvince());
        user.setCity(merchant.getCity());
        user.setRegion(merchant.getRegion());
        user.setChipId(merchant.getMerchantNum());

        userMapper.updateById(user);


    }

}

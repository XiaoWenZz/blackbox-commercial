package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.dto.BriefUser;
import com.tencent.wxcloudrun.dto.SessionData;
import com.tencent.wxcloudrun.dto.WxSession;
import com.tencent.wxcloudrun.dto.request.UpdateUserRequest;
import com.tencent.wxcloudrun.entity.User;

public interface UserService {

    User getUserById(Long id);

    Page<BriefUser> getBriefUserList(int pageSize, int pageNum, Long userId);

    void updateUser(String openId, UpdateUserRequest updateUserRequest);

    Page<BriefUser> searchUser(int pageNum, int pageSize, String content);

    SessionData login();

    WxSession getWxSessionByCode(String code);

    void checkWxSession(WxSession wxSession);

    void BindMerchant(String openId, String code);

    User getUserByOpenId(String openId);

    SessionData getSessionDataFromRedis(String openId);


}

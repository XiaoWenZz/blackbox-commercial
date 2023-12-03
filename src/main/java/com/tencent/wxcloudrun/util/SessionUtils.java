package com.tencent.wxcloudrun.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.wxcloudrun.common.CommonConstants;
import com.tencent.wxcloudrun.dto.SessionData;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;


/**
 * @author yannis
 * @version 2020/8/1 18:38
 */
@Component
public class SessionUtils {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private RedisUtils redisUtil;

    @Autowired
    private UserMapper userMapper;

    public Long getUserId(){
        return Optional
                .ofNullable(getSessionData())
                .orElse(new SessionData())
                .getId();
    }

    public String getOpenId(){
        return request.getHeader(CommonConstants.OPEN_ID);
    }

    public String getUnionId(){
        return request.getHeader(CommonConstants.UNION_ID);
    }

//    public Integer getUserType(){
//        return Optional
//                .ofNullable(getSessionData())
//                .orElse(new SessionData())
//                .getType();
//    }

    public SessionData getSessionData(){
        String key = request.getHeader(CommonConstants.OPEN_ID);
        if(key == null) return null;

        SessionData sessionData = null;
        try {
            sessionData = (SessionData) redisUtil.get(key);
        }catch (Exception e){
            return getSessionDataFromDB(key);

        }
        if(sessionData != null)return sessionData;
        return getSessionDataFromDB(key);
    }

    public String getSessionId() {
        String key = request.getHeader(CommonConstants.SESSION);
        if(key == null) return null;
        return  key;
    }

    public void setSessionId(String sessionId){
        response.setHeader(CommonConstants.SESSION,sessionId);
    }

    public void setUnionId(String unionId){
        response.setHeader(CommonConstants.UNION_ID, unionId);
    }

    public String generateSessionId(){
        String sessionId = UUID.randomUUID().toString();
        response.setHeader(CommonConstants.SESSION, sessionId);
        return sessionId;
    }

    public void invalidate(){
//        request.removeAttribute(CommonConstants.SESSION);
        request.removeAttribute(CommonConstants.OPEN_ID);
    }

    private SessionData getSessionDataFromDB(String key) {
        SessionData sessionData;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("session_id",key);
        userQueryWrapper.eq("open_id",key);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null){
            sessionData = new SessionData(user);
            redisUtil.set(key, sessionData, 3600);
            return sessionData;
        }else{
            redisUtil.set(key,null,3600);
            return null;
        }
    }
}

package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyTest {

    @Autowired
    private RedisUtils redisUtils;


    public void set(String key, String value) {
        try {
            redisUtils.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        try {
            return redisUtils.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getExpire(String key) {
        try {
            return redisUtils.getExpire(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void expire(String key){
        try {
            redisUtils.expire(key, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

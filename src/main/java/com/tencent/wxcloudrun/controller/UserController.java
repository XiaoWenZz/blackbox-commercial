package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.annotation.Auth;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Result;
import com.tencent.wxcloudrun.dto.SessionData;
import com.tencent.wxcloudrun.dto.request.UpdateUserRequest;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api("用户相关操作")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionUtils sessionUtils;

    @GetMapping("/login")
    @ApiOperation(value = "登录", response = SessionData.class)
    public Result login() {
        try {
            return  Result.success(userService.login());
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping("/logout")
    @ApiOperation(value = "登出",response = String.class)
    public Result logout(){
        try {
            Long userId = sessionUtils.getUserId();
            sessionUtils.invalidate();
            return Result.success(userId);
        }catch (CommonException e){
            return  Result.result(e.getCommonErrorCode());
        }
    }


    @Auth
    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "获取个人信息")
    public Result getUserSessionData(){
        try{
            return Result.success(userService.getUserByOpenId(sessionUtils.getOpenId()));
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping(value = "/infoTest", produces = "application/json")
    @ApiOperation(value = "获取个人信息")
    public Result getUserSessionDataTest(){
        try{
            return Result.success(userService.getSessionDataFromRedis(sessionUtils.getOpenId()));
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping(value = "/user", produces = "application/json")
    @ApiOperation("获取用户信息")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "Long")
    public Result getUserInformation(@RequestParam("userId") Long userId){
        try {
            return Result.success(userService.getUserById(userId));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping("/check")
    @ApiOperation(value = "检查登录态")
    public Result checkSession(){
        try {
            if(sessionUtils.getSessionData()==null) return Result.fail("登录失效");
            return Result.success("已登录");
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }

    }

    @Auth
    @PostMapping(value = "/update", produces = "application/json")
    @ApiOperation(value = "更改用户信息")
    public Result updateUser(@NotNull @RequestBody UpdateUserRequest updateUserRequest){
        try{
            userService.updateUser(sessionUtils.getOpenId(),updateUserRequest);
            return Result.success("更新成功");

        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping(value = "/bind", produces = "application/json")
    @ApiOperation(value = "绑定发行")
    @ApiImplicitParam(name = "key", value = "密钥", required = true, paramType = "query", dataType = "String")
    public Result bindMerchant(@RequestParam("key") String key){
        try{
            userService.BindMerchant(sessionUtils.getOpenId(),key);
            return Result.success("绑定成功");
        }catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

}

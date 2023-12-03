package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.annotation.Admin;
import com.tencent.wxcloudrun.annotation.Auth;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Result;
import com.tencent.wxcloudrun.dto.request.AddExhibitScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetExhibitScriptListRequest;
import com.tencent.wxcloudrun.dto.request.SendScriptMessageRequest;
import com.tencent.wxcloudrun.service.ExhibitScriptService;
import com.tencent.wxcloudrun.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api("展会展本相关操作")
@RestController
@RequestMapping("/exhibitScript")
public class ExhibitScriptController {

    @Autowired
    private ExhibitScriptService exhibitScriptService;

    @Autowired
    private SessionUtils sessionUtils;

    @PostMapping(value = "/add", produces = "application/json")
    @ApiOperation(value = "添加展会展本")
    public Result addExhibitScript(@NotNull @RequestBody AddExhibitScriptRequest request) {
        try {
            exhibitScriptService.addExhibitScript(request);
            return Result.success("添加成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/list", produces = "application/json")
    @ApiOperation(value = "查看展会展本列表(含筛选)")
    public Result getExhibitScriptList(@RequestBody GetExhibitScriptListRequest request) {
        try {
            return Result.success(exhibitScriptService.getBriefExhibitScriptList(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/merchantScriptList", produces = "application/json")
    @ApiOperation(value = "查看发行商展会展本列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "exhibitionId", value = "展会id", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "merchantId", value = "发行商id", required = true, dataType = "long", paramType = "query")
    })
    public Result getMerchantScriptList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("exhibitionId") Long exhibitionId,
                                        @RequestParam("merchantId") Long merchantId) {
        try {
            return Result.success(exhibitScriptService.getExhibitScriptList(pageNum, pageSize, exhibitionId, merchantId));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Admin
    @PostMapping(value = "/send", produces = "application/json")
    @ApiOperation("发送剧本消息")
    public Result sendScriptMessage(@RequestBody SendScriptMessageRequest request){
        try {
            return Result.success(exhibitScriptService.sendScriptMessage(request));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping(value = "/flag", produces = "application/json")
    @ApiOperation("插旗展会展本")
    public Result flagExhibitScript(@RequestParam("id") Long id, @RequestParam("attitude") Integer attitude){
        try {
            return Result.success(exhibitScriptService.flagExhibitScript(id, attitude, sessionUtils.getOpenId()));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @GetMapping(value = "/flagOfScript", produces = "application/json")
    @ApiOperation("获取展会展本的插旗情况")
    @ApiImplicitParam(name = "id", value = "展会展本id", required = true, dataType = "long", paramType = "query")
    public Result getExhibitScriptFlagForUser(@RequestParam("id") Long id){
        try {
            return Result.success(exhibitScriptService.getExhibitScriptFlagForUser(id, sessionUtils.getOpenId()));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }



}

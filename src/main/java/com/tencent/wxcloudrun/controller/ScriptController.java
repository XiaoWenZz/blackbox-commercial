package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.annotation.Auth;
import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Result;
import com.tencent.wxcloudrun.dto.request.AddScriptRequest;
import com.tencent.wxcloudrun.dto.request.GetScriptScheduleListRequest;
import com.tencent.wxcloudrun.dto.request.ScriptFilterRequest;
import com.tencent.wxcloudrun.dto.request.UpdateScriptRequest;
import com.tencent.wxcloudrun.service.ScriptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("剧本相关操作")
@RestController
@RequestMapping("/script")
public class ScriptController {
    @Autowired
    private ScriptService scriptService;

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation("查看剧本详情")
    @ApiImplicitParam(name = "scriptId", value = "剧本id", required = true, paramType = "query", dataType = "Long")
    public Result getScript(@RequestParam("scriptId") Long scriptId){
        try {
            return Result.success(scriptService.getScriptById(scriptId));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/listWithFilter", produces = "application/json")
    @ApiOperation("查看剧本列表")
    public Result getScriptList(@RequestBody ScriptFilterRequest request){
        try {
            return Result.success(scriptService.getBriefScriptListWithFilter(request));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping(value = "/add", produces = "application/json")
    @ApiOperation("添加剧本")
    public Result addScript(@RequestBody AddScriptRequest addScriptRequest){
        try {
            return Result.success(scriptService.addScript(addScriptRequest));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

//    @Auth
//    @PostMapping(value = "/upload", produces = "application/json")
//    @ApiImplicitParam(name = "scriptId", value = "剧本id", required = true, paramType = "query", dataType = "Long")
//    @ApiOperation("上传剧本图片")
//    public Result upload(@RequestParam("scriptId") Long scriptId, MultipartFile file){
//        try {
//            return Result.success(scriptService.uploadScriptPicture(file, scriptId));
//        } catch (CommonException e){
//            return Result.result(e.getCommonErrorCode());
//        }
//    }

//    @Admin
    @Auth
    @PostMapping(value = "/update", produces = "application/json")
    @ApiOperation("更新剧本")
    public Result updateScript(@RequestBody UpdateScriptRequest request){
        try {
            scriptService.updateScript(request);
            return Result.success("更新成功");
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }

    @Auth
    @PostMapping(value = "/getSchedule", produces = "application/json")
    @ApiOperation("获取剧本参展日程")
    public Result getScriptExhibitSchedule(@RequestBody GetScriptScheduleListRequest request){
        try {
            return Result.success(scriptService.getScriptScheduleList(request));
        } catch (CommonException e){
            return Result.result(e.getCommonErrorCode());
        }
    }




}

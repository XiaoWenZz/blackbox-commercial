package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.common.CommonException;
import com.tencent.wxcloudrun.common.Result;
import com.tencent.wxcloudrun.dto.request.AddMerchantRequest;
import com.tencent.wxcloudrun.dto.request.UpdateMerchantRequest;
import com.tencent.wxcloudrun.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("发行相关操作")
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "获取发行详情")
    public Result getMerchant(@RequestParam("merchantId") Long merchantId) {
        try {
            return Result.success(merchantService.getMerchantById(merchantId));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ApiOperation(value = "添加发行")
    public Result addMerchant(@RequestBody AddMerchantRequest request) {
        try {
            return Result.success(merchantService.addMerchant(request));
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @GetMapping(value = "/setLogo", produces = "application/json")
    @ApiOperation(value = "设置logo 上传logo文件的fileid")
    public Result setLogo(@RequestParam("merchantId") Long merchantId, @RequestParam("logo") String logo) {
        try {
            merchantService.setLogo(merchantId, logo);
            return Result.success("设置成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }

    @PostMapping(value = "/update", produces = "application/json")
    @ApiOperation(value = "更新发行")
    public Result updateMerchant(@RequestBody UpdateMerchantRequest request) {
        try {
            merchantService.updateMerchant(request);
            return Result.success("更新成功");
        } catch (CommonException e) {
            return Result.result(e.getCommonErrorCode());
        }
    }


}

package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("GetScriptListRequest 获取剧本列表请求")
public class GetExhibitScriptListRequest {

    @NotNull
    @ApiModelProperty("展会id")
    private Long exhibitionId;

    @NotNull
    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @NotNull
    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("剧本类型 0情感 1推理 2机制 3欢乐 不传为全选")
    private Integer type;

    @ApiModelProperty("发售形式 0城限 1独家 2盒装 不传为全选")
    private Integer saleForm;

    @ApiModelProperty("首发情况 0非首发 1首发 不传为全选")
    private Integer debutFlag;

    @ApiModelProperty("题材 0现代 1欧式 2日式 3古代 4民国 5未来 6架空 7其他")
    private Integer theme;

    @ApiModelProperty("派系 0本格 1变格 2新本格 3还原 4封闭 5半封闭 6开发")
    private Integer faction;

    @ApiModelProperty("难度 0新手 1进阶 2硬核")
    private Integer difficulty;

    @ApiModelProperty("按照收藏量排序 0升序 1降序 不传为不排序")
    private Integer rank;

}

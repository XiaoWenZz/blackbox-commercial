package com.tencent.wxcloudrun.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BriefMerchantExhibitScript 商户展会剧本简要信息")
public class BriefMerchantExhibitScript {
    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("剧本名称")
    private String name;

    @ApiModelProperty("剧本封面")
    private String fileId;

    @ApiModelProperty("剧本类型")
    private Integer type;

    @ApiModelProperty("剧本标签")
    private String tag;

    @ApiModelProperty("展酒房间")
    private String room;

    @ApiModelProperty("发售形式")
    private Integer saleForm;

    @ApiModelProperty("男女组成")
    private String composition;

    @ApiModelProperty("首发情况 0非首发 1首发")
    private Integer debutFlag;

    @ApiModelProperty("收藏量")
    private Integer collectionCount;


}

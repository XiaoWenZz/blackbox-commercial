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
@ApiModel("BriefScriptCollection 收藏剧本")
public class BriefScriptCollection {

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("剧本名称")
    private String name;

    @ApiModelProperty("收藏id")
    private Long collectionId;

    @ApiModelProperty("剧本类型")
    private Integer type;

    @ApiModelProperty("剧本封面")
    private String fileId;

    @ApiModelProperty("剧本收藏量")
    private Integer collectionCount;

    @ApiModelProperty("剧本标签")
    private String tag;

    @ApiModelProperty("发售形式")
    private Integer saleForm;

    @ApiModelProperty("男女组成")
    private String composition;


}

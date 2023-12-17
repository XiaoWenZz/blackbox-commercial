package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateScriptRequest 更新剧本信息")
public class UpdateScriptRequest {

    @NotNull
    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("剧本名称")
    private String name;

    @ApiModelProperty("发售形式 0城限 1独家 2盒装")
    private Integer saleForm;

    @ApiModelProperty("剧本类型 0情感 1推理 2机制 3欢乐 4其他")
    private Integer type;

    @NotNull
    @ApiModelProperty("题材 0现代 1欧式 2日式 3古代 4民国 5未来 6架空 7其他")
    private Integer theme;

    @NotNull
    @ApiModelProperty("派系 0本格 1变格 2新本格 3还原 4封闭 5半封闭 6开放 7立意 8其他")
    private Integer faction;

    @NotNull
    @ApiModelProperty("难度 0新手 1进阶 2硬核")
    private Integer difficulty;

    @ApiModelProperty("剧本标签")
    private String tag;

    @ApiModelProperty("作者1姓名")
    private String author1Name;

    @ApiModelProperty("作者2姓名")
    private String author2Name;

    @ApiModelProperty("作者3姓名")
    private String author3Name;

    @ApiModelProperty("剧本出品商1 id")
    private Long merchant1Id;

    @ApiModelProperty("剧本出品商2 id")
    private Long merchant2Id;

    @ApiModelProperty("剧本出品商3 id")
    private Long merchant3Id;

    @ApiModelProperty("剧本简介")
    private String introduction;

    @ApiModelProperty("人数")
    private Integer playerCount;

    @ApiModelProperty("男生人数")
    private Integer maleCount;

    @ApiModelProperty("女生人数")
    private Integer femaleCount;

    @ApiModelProperty("剧本封面文件id")
    private String fileId;

    @ApiModelProperty("需要配置")
    private String needs;

    @ApiModelProperty("售后支持")
    private String afterSaleSupport;

    @ApiModelProperty("售卖价格")
    private Integer price;

    @ApiModelProperty("最短游玩时长")
    private Double minPlayTime;

    @ApiModelProperty("最长游玩时长")
    private Double maxPlayTime;

    @ApiModelProperty("能否反串 1否 2是")
    private Integer crossDressing;

    @ApiModelProperty("单人阅读体量 数字")
    private Double singleReadVolume;

    @ApiModelProperty("一句话剧本亮点")
    private String highlight;

    @ApiModelProperty("是否有破冰 1否 2是")
    private Integer IceBreaking;

    @ApiModelProperty("预计发货时间")
    private String deliveryTime;

}

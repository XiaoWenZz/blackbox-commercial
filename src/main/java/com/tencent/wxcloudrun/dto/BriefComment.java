package com.tencent.wxcloudrun.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BriefComment 评论简要信息")
public class BriefComment {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("评分")
    private double score;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论人openId")
    private String userOpenId;

    @ApiModelProperty("评论人昵称")
    private String userNickname;

    @ApiModelProperty("评论人头像")
    private String userPortrait;

    @ApiModelProperty("评论图片")
    private String image;

    @ApiModelProperty("评论时间")
    private String createTime;

    @ApiModelProperty("评论点赞数")
    private Integer likeCount;

    @ApiModelProperty("是否匿名 1否 2是")
    private Integer isAnonymous;


}

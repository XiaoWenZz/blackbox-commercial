package com.tencent.wxcloudrun.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("CommentImage 评论图片")
public class CommentImage {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("评论图片id")
    private Long id;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("图片fileId")
    private String fileId;

    @ApiModelProperty("上传时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

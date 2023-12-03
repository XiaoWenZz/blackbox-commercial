package com.tencent.wxcloudrun.entity;

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
@ApiModel("ScriptPicture 剧本图片")
public class ScriptPicture {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("图片id")
    private Long id;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片文件id")
    private String fileId;


}

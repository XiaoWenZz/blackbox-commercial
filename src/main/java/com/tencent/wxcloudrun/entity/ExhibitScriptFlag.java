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
@ApiModel("ExhibitScriptFlag 剧本插旗")
public class ExhibitScriptFlag {

    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("插旗id")
    private Long id;

    @ApiModelProperty("展本id")
    private Long exhibitScriptId;

    @ApiModelProperty("观望/支持 0观望 1支持")
    private Integer attitude;

    @ApiModelProperty("用户id")
    private Long userId;

}

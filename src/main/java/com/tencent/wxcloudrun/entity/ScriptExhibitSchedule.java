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
@ApiModel("ScriptExhibitSchedule 剧本参展日程")
public class ScriptExhibitSchedule {
    @Id
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("剧本id")
    private Long scriptId;

    @ApiModelProperty("参与展会id")
    private Long exhibitionId;

}

package com.tencent.wxcloudrun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("ScriptFilterRequest 剧本筛选请求")
public class ScriptFilterRequest {

    @NotNull
    @ApiModelProperty("每页数量")
    private Integer pageSize;

    @NotNull
    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("剧本人数 <=5输入-1 >=8输入-2 其他正常输入")
    private Integer playerCount;

    @ApiModelProperty("剧本类型 0情感 1推理 2机制 3欢乐 4其他")
    private Integer type;


    @ApiModelProperty("发售形式 0城限 1独家 2盒装")
    private Integer saleForm;

    @ApiModelProperty("题材 0现代 1欧式 2日式 3古代 4民国 5未来 6架空 7其他 输入List")
    private List<Integer> theme;

    @ApiModelProperty("派系 0本格 1变格 2新本格 3还原 4封闭 5半封闭 6开放 输入List")
    private List<Integer> faction;

    @ApiModelProperty("难度 0新手 1进阶 2硬核 输入List")
    private List<Integer> difficulty;

    @ApiModelProperty("剧本名称 搜索用")
    private String name;

    @ApiModelProperty("排序方式 0综合 1收藏最多 2收藏最少")
    private Integer sort;

}

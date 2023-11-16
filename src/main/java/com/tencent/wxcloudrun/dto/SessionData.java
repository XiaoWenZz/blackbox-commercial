package com.tencent.wxcloudrun.dto;


import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.util.AssertUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * session缓存实体
 * @author yan on 2020-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SessionData 会话实体")
public class SessionData implements Serializable {

    /**
     * {@link User}
     */
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户唯一标识")
    private String openId;

    @ApiModelProperty("unionId")
    private String unionId;

    @ApiModelProperty("头像")
    private String portrait;

    @ApiModelProperty("类型(0为普通用户，1为管理员)")
    private Integer type;

    @ApiModelProperty("电话号")
    private String telephone;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("注册时间")
    private String createTime;

    @ApiModelProperty("会话id")
    private String sessionId;

    @ApiModelProperty("所属发行/店家id")
    private Long publisherId;

    @ApiModelProperty("所属发行/店家名称")
    private String publisherName;

    @ApiModelProperty("可发布公车次数")
    private Integer publishCount;




    public SessionData(User user){
        AssertUtil.isNotNull(user, CommonErrorCode.USER_NOT_EXIST);
        id = user.getId();
        openId = user.getOpenId();
        unionId = user.getUnionId();
        nickname = user.getNickname();
        type = user.getType();
        portrait = user.getPortrait();
        telephone = user.getTelephone();
        createTime = user.getCreateTime();
        sessionId = user.getSessionId();
        publisherId = user.getPublisherId();
        publisherName = user.getPublisherName();
    }
}

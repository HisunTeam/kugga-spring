package com.hisun.kugga.duke.user.controller.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 用户信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRespVO extends UserBaseVO {

    @ApiModelProperty(value = "id 99duke******", required = true)
    private String userId;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}

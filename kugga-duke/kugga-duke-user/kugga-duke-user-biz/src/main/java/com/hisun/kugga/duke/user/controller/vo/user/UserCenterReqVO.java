package com.hisun.kugga.duke.user.controller.vo.user;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("个人中心请求vo")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCenterReqVO extends PageParam {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "简历和推荐报告类型 0-简历 1-推荐报告")
    private String cvType;

    @ApiModelProperty(value = "收藏类型 G-工会 T-推荐报告")
    private String favoriteType;

    @ApiModelProperty(value = "主页类型 0-同公会 1-不同公会")
    private String homePageType;
}

package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("检查邮箱对应的用户是否为公会成员 Response VO")
@Data
public class CheckMailIsLeagueMemberRespVO {
    @ApiModelProperty(value = "是否是公会成员", example = "true:是,false:否")
    private Boolean memberFlag;
}

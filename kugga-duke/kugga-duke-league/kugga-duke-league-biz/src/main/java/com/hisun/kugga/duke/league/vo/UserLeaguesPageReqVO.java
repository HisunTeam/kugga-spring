package com.hisun.kugga.duke.league.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zuocheng
 */
@ApiModel("查询用户加入公会列表 request VO")
@Data
public class UserLeaguesPageReqVO extends PageParam {
    @ApiModelProperty(value = "查询类型 true:查询用户已加入,且已被认证的公会 false:查询用户加入的所有公会列表", example = "false")
    private Boolean type;
}

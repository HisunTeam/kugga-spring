package com.hisun.kugga.duke.system.controller.app.shorturl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author zuocheng
 */
@ApiModel("短链 Response VO")
@Data
@ToString(callSuper = true)
public class ShortCodeRespVO {
    @ApiModelProperty(value = "uuid,用于获取邀请公会信息的关键值", example = "461b5ff7406d4316916dba54ca7ee9e1")
    private String uuid;
}

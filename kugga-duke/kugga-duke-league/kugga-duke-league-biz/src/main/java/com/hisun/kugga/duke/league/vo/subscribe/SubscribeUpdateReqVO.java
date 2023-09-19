package com.hisun.kugga.duke.league.vo.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 */
@ApiModel("订阅信息更新 Request VO")
@Data
public class SubscribeUpdateReqVO {

    @ApiModelProperty(value = "订阅id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "订阅状态 true 订阅中 继续订阅 false取消订阅")
    private Boolean status;

}

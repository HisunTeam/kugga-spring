package com.hisun.kugga.duke.league.vo.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author admin
 */
@ApiModel("订阅续期VO")
@Data
//@ToString
public class SubscriptionRenewalVO {

    @ApiModelProperty(value = "订阅流水id", required = true)
    @NotNull(message = "订阅流水id不能为空")
    private Long flowId;

    @ApiModelProperty(value = "uuid", required = true)
    @NotNull(message = "uuid不能为空")
    private String uuid;

    private BigDecimal price;

    private Long userId;


}

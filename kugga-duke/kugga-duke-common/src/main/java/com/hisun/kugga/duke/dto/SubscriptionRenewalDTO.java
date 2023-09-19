package com.hisun.kugga.duke.dto;

import com.hisun.kugga.duke.innercall.InnerCallReqDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author admin
 */
@ApiModel("订阅续期VO")
@Data
@ToString
public class SubscriptionRenewalDTO extends InnerCallReqDTO {

    @ApiModelProperty(value = "订阅流水id", required = true)
    @NotNull(message = "订阅流水id不能为空")
    private Long flowId;

    private BigDecimal price;

    private Long userId;


}

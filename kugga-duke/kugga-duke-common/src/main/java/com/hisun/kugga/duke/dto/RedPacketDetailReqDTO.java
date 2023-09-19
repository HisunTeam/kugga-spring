package com.hisun.kugga.duke.dto;

import com.hisun.kugga.duke.innercall.InnerCallReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketDetailReqDTO extends InnerCallReqDTO {

    @ApiModelProperty(value = "内部订单号", required = true)
    @NotEmpty(message = "appOrderNo can not be empty")
    private String appOrderNo;
}

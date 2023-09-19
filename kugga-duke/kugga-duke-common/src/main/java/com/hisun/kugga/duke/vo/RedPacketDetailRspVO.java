package com.hisun.kugga.duke.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
@ApiModel("红包发放状态 Response VO")
public class RedPacketDetailRspVO {
    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "draft 待发放；processing 处理中；partial_success 部分发放成功；full_success 已全部发放成功；failed 已退款；")
    private String status;
}

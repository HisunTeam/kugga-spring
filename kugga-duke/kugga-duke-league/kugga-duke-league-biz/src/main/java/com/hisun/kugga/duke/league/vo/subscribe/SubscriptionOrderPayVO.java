package com.hisun.kugga.duke.league.vo.subscribe;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author admin
 */
@ApiModel("订阅续期VO")
@Data
public class SubscriptionOrderPayVO {

    private Long flowId;

    private BigDecimal price;

    private Long userId;

    private Long leagueId;
    /**
     * 公会创建者id
     */
    private Long leagueCreateId;

    private String appOrderNo;
    /**
     * 标识 下单 支付 分账等 是否成功
     */
    private Boolean isSuccess;


}

package com.hisun.kugga.duke.league.vo.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author admin
 */
@ApiModel("订阅信息vo")
@Data
@ToString(callSuper = true)
public class SubscribeRespVO {

    @ApiModelProperty(value = "订阅id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "订阅id")
    private Long userId;
    /**
     * 公会id
     */
    @ApiModelProperty(value = "订阅id")
    private Long leagueId;
    /**
     * 订阅类型
     */
    @ApiModelProperty(value = "订阅id")
    private String subscribeType;
    /**
     * 订阅价格
     */
    private BigDecimal price;
    /**
     * 订阅过期时间
     */
    private LocalDateTime expireTime;

    private LocalDateTime updateTime;
    /**
     * 订阅状态 true 订阅中 false取消订阅
     */
    private Boolean status;
    /**
     * 实际订阅状态 过期状态
     * status 为false，但是过期时间还没到，此时为false
     */
    private Boolean expireStatus;

    private String leagueName;

    private String leagueAvatar;
}

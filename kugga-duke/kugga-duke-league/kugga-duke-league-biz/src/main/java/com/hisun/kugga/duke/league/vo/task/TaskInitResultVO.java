package com.hisun.kugga.duke.league.vo.task;

import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
@ApiModel("InitTask接口返回对象")
public class TaskInitResultVO {

    TaskInitResultChat taskInitResultChat;

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "手续费 单位：元")
    private BigDecimal fee;

    @Data
    public static class TaskInitResultChat {
        @ApiModelProperty(value = "付费状态 1免费 2付费")
        private PayTypeEnum payType;
    }
}

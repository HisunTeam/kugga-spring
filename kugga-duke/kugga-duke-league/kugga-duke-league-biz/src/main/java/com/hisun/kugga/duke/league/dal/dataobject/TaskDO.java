package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 * 公会任务表
 */
@TableName("duke_task")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("任务表")
public class TaskDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务表ID")
    private Long id;
    @ApiModelProperty(value = "任务发起者ID")
    private Long userId;
    @ApiModelProperty(value = "任务发起类型 1 推荐报告 2 认证 3 聊天")
    private TaskTypeEnum type;
    @ApiModelProperty(value = "任务状态 0待支付 1未接单 已支付、2已结单、3、已完成、4带退款、5、已退款")
    private TaskStatusEnum status;
    @ApiModelProperty(value = "任务状态 0 免费 1 付费")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "任务金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "订单编号")
    private String orderRecord;
    @ApiModelProperty(value = "业务参数")
    private String businessParams;
    @ApiModelProperty(value = "失效时间")
    private LocalDateTime expiresTime;
}

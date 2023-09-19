package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-08 15:31
 */
@TableName("duke_task_report")
@Data
@ApiModel("任务 写推荐报告的子订单表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskReportDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "任务ID")
    private Long taskId;
    @ApiModelProperty(value = "公会公告栏ID")
    private Long noticeId;
    @ApiModelProperty(value = "订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "推荐报告金额 子订单价格")
    private BigDecimal amount;
    @ApiModelProperty(value = "付费类型 0免费 1付费")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "状态 0未支付 1已支付 2已分账 3已退款")
    private PayStatusEnum status;
    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expiresTime;
}

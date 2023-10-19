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
@ApiModel("Task Sub-Order Table for Writing Recommendation Reports")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskReportDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "Task ID")
    private Long taskId;
    @ApiModelProperty(value = "Guild Notice Board ID")
    private Long noticeId;
    @ApiModelProperty(value = "Order Number")
    private String appOrderNo;
    @ApiModelProperty(value = "Recommendation Report Amount (Sub-Order Price)")
    private BigDecimal amount;
    @ApiModelProperty(value = "Payment Type: 0 (Free), 1 (Paid)")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "Status: 0 (Unpaid), 1 (Paid), 2 (Shared), 3 (Refunded)")
    private PayStatusEnum status;
    @ApiModelProperty(value = "Expiration Time")
    private LocalDateTime expiresTime;
}

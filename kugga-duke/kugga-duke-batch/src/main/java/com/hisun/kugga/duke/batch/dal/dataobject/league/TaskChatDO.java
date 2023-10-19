package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-16 15:04
 */
@TableName("duke_task_chat")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Task Chat Table")
public class TaskChatDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "Task Chat Key")
    private String taskChatKey;
    @ApiModelProperty(value = "Task Table ID")
    private Long taskId;
    @ApiModelProperty(value = "Guild Notice Board ID")
    private Long noticeId;
    @ApiModelProperty(value = "Internal Order Number")
    private String appOrderNo;
    @ApiModelProperty(value = "Chat Amount")
    private BigDecimal amount;
    @ApiModelProperty(value = "Payment Type: 0 (Free), 1 (Paid)")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "Status: 0 (Unpaid), 1 (Paid), 2 (Shared), 3 (Pending Refund), 4 (Refunded)")
    private PayStatusEnum status;
}

package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.*;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 * Guild Task Table
 */
@TableName("duke_task")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Task Table")
public class TaskDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "Task Table ID")
    private Long id;
    @ApiModelProperty(value = "Task Initiator ID")
    private Long userId;
    @ApiModelProperty(value = "Task Initiator Type: 1 (Recommendation Report), 2 (Authentication), 3 (Chat)")
    private TaskTypeEnum type;
    @ApiModelProperty(value = "Task Status: 1 (Published), 2 (Accepted), 3 (Completed)")
    private TaskStatusEnum status;
    @ApiModelProperty(value = "Task Payment Type: 1 (Paid)")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "Task Amount")
    private BigDecimal amount;
    @ApiModelProperty(value = "Order Number")
    private String orderRecord;
    @ApiModelProperty(value = "Business Parameters")
    private String businessParams;
    @ApiModelProperty(value = "Expiration Time")
    private LocalDateTime expiresTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    protected String updater;
}

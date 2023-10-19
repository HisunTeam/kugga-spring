package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.*;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-08 15:31
 */
@TableName("duke_task_league_auth")
@Data
@ApiModel("Pending Authentication Guild Table - Task type is Guild Authentication, Task associates Pending Authentication Guild with Authentication Guild")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLeagueAuthDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "Task ID")
    private Long taskId;
    @ApiModelProperty(value = "Guild Notice Board ID")
    private Long noticeId;
    @ApiModelProperty(value = "Order Number")
    private String appOrderNo;
    @ApiModelProperty(value = "Sub-Order Price")
    private BigDecimal amount;
    @ApiModelProperty(value = "Payment Type: 0 (Free), 1 (Paid)")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "Status: 0 (Unpaid), 1 (Paid), 2 (Shared), 3 (Refunded)")
    private PayStatusEnum status;

    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updater;
}

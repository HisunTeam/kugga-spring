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
    @ApiModelProperty(value = "任务状态 1 已发布 2 被接单 3 已完成")
    private TaskStatusEnum status;
    @ApiModelProperty(value = "任务状态 1 已发布 2 被接单 3 已完成")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "任务金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "订单编号")
    private String orderRecord;
    @ApiModelProperty(value = "业务参数")
    private String businessParams;
    @ApiModelProperty(value = "失效时间")
    private LocalDateTime expiresTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    protected String updater;
}

package com.hisun.kugga.duke.league.dal.dataobject;

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
@ApiModel("任务聊天表")
public class TaskChatDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "任务聊天key")
    private String taskChatKey;
    @ApiModelProperty(value = "任务表ID")
    private Long taskId;
    @ApiModelProperty(value = "公会公告栏ID")
    private Long noticeId;
    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;
    @ApiModelProperty(value = "聊天金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "付费类型 0免费 1付费")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "状态 0默认值  1未支付 2已支付 3已分账 4待退款 5已退款")
    private PayStatusEnum status;
}

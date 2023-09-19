package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 16:41
 */
@Data
@Builder
@TableName("duke_league_notice")
@ApiOperation("duke_league_notice")
@NoArgsConstructor
@AllArgsConstructor
public class LeagueNoticeDO extends BaseDO {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "公告栏ID不传值 数据库自增")
    private Long id;
    @ApiModelProperty(value = "所属任务")
    private Long taskId;
    @ApiModelProperty(value = "所属任务")
    private TaskTypeEnum taskType;
    @ApiModelProperty(value = "所属公会 (哪个公会的公告栏)")
    private Long leagueId;
    @ApiModelProperty(value = "公告类型")
    private LeagueNoticeTypeEnum type;
    @ApiModelProperty(value = "状态 1未接单 2已接单 3已完成 4已失效")
    private LeagueNoticeStatusEnum status;

    @ApiModelProperty(value = "主动用户")
    private Long useUserId;
    @ApiModelProperty(value = "发起用户所属公会ID")
    private Long useLeagueId;

    @ApiModelProperty(value = "被动用户")
    private Long byUserId;
    @ApiModelProperty(value = "被发起用户所属公会ID")
    private Long byLeagueId;

    @ApiModelProperty(value = "付费类型 0免费 1付费")
    private TaskPayTypeEnum payType;
    /**
     * 公告栏金额 存用户/公会分帐后金额，方便公告栏显示
     * 比如10元 存5元 ，10元的原始金额存在各业务订单表里
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "失效时间")
    private LocalDateTime expiresTime;
}

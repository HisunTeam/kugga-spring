package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.*;
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
import org.apache.ibatis.type.JdbcType;

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
    @ApiModelProperty(value = "Notice Board ID, not passed if self-incremented in the database")
    private Long id;
    @ApiModelProperty(value = "Belonging Task")
    private Long taskId;
    @ApiModelProperty(value = "Task Type")
    private TaskTypeEnum taskType;
    @ApiModelProperty(value = "Belonging Guild (Guild's Notice Board)")
    private Long leagueId;
    @ApiModelProperty(value = "Notice Type")
    private LeagueNoticeTypeEnum type;
    @ApiModelProperty(value = "Status: 1 (Not Accepted), 2 (Accepted), 3 (Completed), 4 (Expired)")
    private LeagueNoticeStatusEnum status;

    @ApiModelProperty(value = "Active User")
    private Long useUserId;
    @ApiModelProperty(value = "Guild ID of the Initiating User")
    private Long useLeagueId;

    @ApiModelProperty(value = "Passive User")
    private Long byUserId;
    @ApiModelProperty(value = "Guild ID of the Initiated User")
    private Long byLeagueId;

    @ApiModelProperty(value = "Payment Type: 0 (Free), 1 (Paid)")
    private TaskPayTypeEnum payType;
    @ApiModelProperty(value = "Amount")
    private BigDecimal amount;
    @ApiModelProperty(value = "Expiration Time")
    private LocalDateTime expiresTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    protected String updater;
}

package com.hisun.kugga.duke.league.api.dto.leaguenotice;

import com.hisun.kugga.duke.enums.LeagueNoticeStatusEnum;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: zhou_xiong
 */
@Data
public class LeagueNoticeDTO{

    private Long leagueId;
    private LeagueNoticeTypeEnum type;
    private LeagueNoticeStatusEnum status;

    private Long useUserId;
    private Long useLeagueId;

    private Long byUserId;
    private Long byLeagueId;

    private TaskPayTypeEnum payType;
    /**
     * 公告栏金额 存用户/公会分帐后金额，方便公告栏显示
     * 比如10元 存5元 ，10元的原始金额存在各业务订单表里
     */
    private BigDecimal amount;
    private LocalDateTime expiresTime;
}

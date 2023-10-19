package com.hisun.kugga.duke.batch.dal.dataobject.league;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * League Invite Link Binding Table (Binding Initiator and Invitation Type of the Link)
 * </p>
 *
 * @author zuocheng
 * @since 2022-07-26 19:25:54
 */
@TableName("duke_league_invite_url_bind")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueInviteUrlBindDO {

    /**
     * Binding ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * UUID (a single number used to query binding relationships)
     */
    private String uuid;

    /**
     * Short Link ID
     */
    private Long shortUrlId;

    /**
     * League ID
     */
    private Long leagueId;

    /**
     * Inviter ID
     */
    private Long userId;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

    /**
     * Expiration time
     */
    private LocalDateTime expireTime;

    /**
     * Expiration status: true (expired), false (not expired)
     */
    private Boolean expireStatus;
}

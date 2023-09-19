package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 邀请链绑定表(绑定链的发起者与邀请类型)
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
     * 绑定ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * uuid(就一个编号,用来查绑定关系)
     */
    private String uuid;

    /**
     * 短链ID
     */
    private Long shortUrlId;

    /**
     * 公会ID
     */
    private Long leagueId;

    /**
     * 邀请人ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 失效状态: true已失效, false:未失效
     */
    private Boolean expireStatus;
}

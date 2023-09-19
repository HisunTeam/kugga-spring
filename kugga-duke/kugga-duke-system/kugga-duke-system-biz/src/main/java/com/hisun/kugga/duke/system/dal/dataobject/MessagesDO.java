package com.hisun.kugga.duke.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 消息 DO
 *
 * @author 芋道源码
 */
@TableName("duke_message")
@KeySequence("duke_messages_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 唯一key
     */
    private String messageKey;
    /**
     * 场景 (推荐信、聊天、认证、邀请加入公会、系统通知)
     */
    private String scene;
    /**
     * 状态(邀请、回调)
     */
    private String type;
    /**
     * 业务id (认证id、推荐信id..)
     */
    private Long businessId;
    /**
     * 业务链接
     */
    private String businessLink;
    /**
     * 发起者
     */
    private Long initiatorId;
    /**
     * 接收者
     */
    private Long receiverId;
    /**
     * 发起者公会id
     */
    private Long initiatorLeagueId;
    /**
     * 接收方公会id
     */
    private Long receiverLeagueId;

    /**
     * 消息参数
     */
    private String messageParam;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 已读状态(UR-未读 ,R-已读)
     */
    private String readFlag;
    /**
     * 处理标志(ND-不处理 ,D-处理,AD-已处理)
     */
    private String dealFlag;

}

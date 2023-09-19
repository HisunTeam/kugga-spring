package com.hisun.kugga.duke.batch.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 站内信消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBO {

    /**
     *
     */
    private String messageCode;
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

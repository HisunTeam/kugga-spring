package com.hisun.kugga.duke.system.controller.app.message.vo;

import com.hisun.kugga.duke.entity.ContentParamVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ApiModel("消息 Response VO")
@Data
@ToString(callSuper = true)
public class MessagesRespVO {

    @ApiModelProperty(value = "id", required = true)
    private Long id;
    /**
     * 链路id 邀请和回调
     */
    private String linkId;

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
     * 消息内容
     */
    private String content;

    /**
     * 消息携带信息
     */
    private ContentParamVo messageParam;

    private String messageParamStr;
    /**
     * 已读状态(UR-未读 ,R-已读)
     */
    private String readFlag;
    /**
     * 处理标志(ND-不处理 ,D-处理,AD-已处理)
     */
    private String dealFlag;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    //用户名和公会名称实时查询
    /**
     * 发起者姓名 头像
     */
    private String initiatorFirstName;
    private String initiatorLastName;
    private String avatar;
    /**
     * 接收者姓名
     */
    private String receiverFirstName;
    private String receiverLastName;
    /**
     * 发起公会名称 头像
     */
    private String initiatorLeagueName;
    private String leagueAvatar;
    /**
     * 接收方公会名称
     */
    private String receiverLeagueName;


}

package com.hisun.kugga.duke.dto;

import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.LanguageEnum;
import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import com.hisun.kugga.duke.enums.message.MessageSceneEnum;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.duke.enums.message.MessageTypeEnum;
import com.hisun.kugga.duke.innercall.InnerCallReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 消息发送dto
 * @author： Lin
 * @Date 2022/7/28 10:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageReqDTO extends InnerCallReqDTO {
    /**
     * 消息模板  唯一键
     * {@link MessageTemplateEnum}
     * 消息构建类 MessageUtil
     */
    private MessageTemplateEnum messageTemplate;
    /**
     * 场景 (推荐信、聊天、认证、邀请加入公会、系统通知)
     */
    private MessageSceneEnum messageScene;
    /**
     * 状态(邀请、回调)
     */
    private MessageTypeEnum messageType;
    /**
     * 业务id (认证id、推荐信id..)
     */
    private Long businessId;
    /**
     * 业务链接
     */
    private String businessLink;
    /**
     * 有序list
     * 消息模板参数
     * 模板：{}({})邀请为【{}】做公会认证
     * 参数：张三、99duke*、后端开发工程师
     * 消息：张三（99duke*）邀请为【后端开发工程师】做公会认证
     */
    private List<String> templateParams;
    /**
     * 消息接收方ids
     */
    private List<Long> receivers;

    /**
     * 消息携带信息
     */
    private ContentParamVo messageParam;
    /**
     * 语言标识
     */
    private LanguageEnum language;
    /**
     * 是否需要处理
     */
    private MessageDealStatusEnum dealStatus;

    private String tempKey;
}

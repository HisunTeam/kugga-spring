package com.hisun.kugga.duke.system.api.message.dto;

import com.hisun.kugga.duke.enums.message.MessageDealStatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@ApiModel("消息更新dto")
@Data
@ToString(callSuper = true)
public class MessagesUpdateReqDTO {

    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 消息状态
     */
    private MessageDealStatusEnum status;

}

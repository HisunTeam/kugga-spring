package com.hisun.kugga.duke.league.vo.task;

import com.hisun.kugga.duke.chat.api.dto.ChatCheckRespDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-08 18:35
 */
@Data
public class TaskChatResultVO {
    @ApiModelProperty(value = "能否聊天")
    private Boolean chatStatus;
    @ApiModelProperty(value = "聊天价格")
    private BigDecimal price;

    @ApiModelProperty(value = "请求发起聊天任务接口 请带上次参数")
    private TaskChatParam taskChatParam;

    public void setTaskChatParam2(ChatCheckRespDto dto) {
        this.setTaskChatParam(new TaskChatParam()
                .setIsInPrivateRoom(dto.getIsInPrivateRoom())
                .setRoomId(dto.getRoomId()));
    }

    @Data
    public static class TaskChatParam {
        @ApiModelProperty(value = "双方用户是否在私人聊天室 true：已存在房间 false：不存在")
        private Boolean isInPrivateRoom;
        @ApiModelProperty(value = "房间ID")
        private Long roomId;
    }
}

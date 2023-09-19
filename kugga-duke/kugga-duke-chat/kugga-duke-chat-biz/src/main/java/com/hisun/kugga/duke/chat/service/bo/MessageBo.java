package com.hisun.kugga.duke.chat.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBo {

    private Long roomId;

    private Long userId;

    private String data;

    /**
     * 消息类型. 文字 1 ，图片，视频，语音，文件，撤回消息
     */
    @Builder.Default
    private Integer type = 1;

}

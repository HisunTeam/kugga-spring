package com.hisun.kugga.duke.chat.controller.app.vo.room;

import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("chat - 聊天室创建 返回 VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestChatRespVO {

    @ApiModelProperty(value = "房间号")
    private Long roomId;

    @ApiModelProperty(value = "房间类型")
    private PayTypeEnum payType;

    @ApiModelProperty(value = "工会Id")
    private Long leagueId;
}

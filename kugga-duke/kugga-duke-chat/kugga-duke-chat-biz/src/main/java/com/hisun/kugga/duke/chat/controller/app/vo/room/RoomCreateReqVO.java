package com.hisun.kugga.duke.chat.controller.app.vo.room;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 聊天室创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomCreateReqVO extends RoomBaseVO {

}

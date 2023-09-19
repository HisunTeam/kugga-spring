package com.hisun.kugga.duke.chat.controller.app.vo.roomrecord;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 聊天记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomRecordPageReqVO extends PageParam {

    @ApiModelProperty(value = "聊天室ID(roomId)")
    private Long roomId;

    @ApiModelProperty(value = "消息记录ID")
    private Long recordId;

}

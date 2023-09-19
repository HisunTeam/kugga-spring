package com.hisun.kugga.duke.chat.controller.app.vo.room;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 聊天室分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoomPageReqVO extends PageParam {

    @ApiModelProperty(value = "聊天室名字")
    private String name;

    @ApiModelProperty(value = "聊天室描述")
    private String description;

    @ApiModelProperty(value = "聊天室头像URL")
    private String avatar;

    @ApiModelProperty(value = "聊天室的类型")
    private Integer roomType;

    @ApiModelProperty(value = "聊天室最大人数")
    private Integer peopleLimit;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}

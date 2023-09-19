package com.hisun.kugga.duke.system.controller.app.message.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author Lin
 */
@ApiModel("消息分页")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessagesPageReqVO extends PageParam {

    @ApiModelProperty(value = "场景 (推荐信、聊天、认证、邀请加入公会、系统通知)")
    private String scene;

    @ApiModelProperty(value = "状态(邀请、回调)")
    private String type;

    @ApiModelProperty(value = "用户id")
    private Long receiverId;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}

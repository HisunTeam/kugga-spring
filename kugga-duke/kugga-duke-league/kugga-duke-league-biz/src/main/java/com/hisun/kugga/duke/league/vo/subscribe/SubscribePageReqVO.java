package com.hisun.kugga.duke.league.vo.subscribe;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author admin
 */
@ApiModel("管理后台 - 加入公会审批分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubscribePageReqVO extends PageParam {

    private Long userId;

    @ApiModelProperty(value = "过期状态 true 过期 false订阅中")
//    private Boolean status;
    private Boolean expireStatus;

}

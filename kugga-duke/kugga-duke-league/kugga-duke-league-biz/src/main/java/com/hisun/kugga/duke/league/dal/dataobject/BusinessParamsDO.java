package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 9:29
 * 公会任务表
 */
@TableName("duke_business_params")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("业务参数表")
public class BusinessParamsDO extends BaseDO {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "业务ID")
    private String businessId;
    @ApiModelProperty(value = "业务参数")
    private String params;
}

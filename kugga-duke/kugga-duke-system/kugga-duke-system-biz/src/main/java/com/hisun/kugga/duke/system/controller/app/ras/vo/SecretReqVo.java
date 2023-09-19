package com.hisun.kugga.duke.system.controller.app.ras.vo;

import com.hisun.kugga.duke.enums.SecretTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/7 13:52
 */
@ApiModel("公钥信息")
@Data
@ToString(callSuper = true)
public class SecretReqVo {
    @ApiModelProperty(value = "0登录 1登录改密、2支付、3支付改密", required = true)
    private SecretTypeEnum type;

    @ApiModelProperty(value = "test pwd", required = false)
    private String password;
}

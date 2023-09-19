package com.hisun.kugga.duke.system.controller.app.ras.vo;

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
public class SecretRespVo {
    @ApiModelProperty(value = "公钥")
    private String publicKey;
    /**
     * 随机数 对应密码 老密码
     */
    @ApiModelProperty(value = "随机数 对应密码、老密码")
    private String random;
    /**
     * 随机数2 修改密码时 对应新密码
     */
    @ApiModelProperty(value = "随机数2 修改密码时、对应新密码")
    private String random2;
}

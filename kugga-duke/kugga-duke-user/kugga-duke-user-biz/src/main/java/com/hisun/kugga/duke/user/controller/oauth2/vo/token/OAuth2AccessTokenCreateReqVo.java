package com.hisun.kugga.duke.user.controller.oauth2.vo.token;

import com.hisun.kugga.framework.common.enums.UserTypeEnum;
import com.hisun.kugga.framework.common.validation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * OAuth2.0 访问令牌创建 Request vo
 *
 * @author 芋道源码
 */
@Data
public class OAuth2AccessTokenCreateReqVo implements Serializable {

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Long userId;
    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    @InEnum(value = UserTypeEnum.class, message = "用户类型必须是 {value}")
    private Integer userType;
    /**
     * 客户端编号
     */
    @NotNull(message = "客户端编号不能为空")
    private String clientId;
    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 额外添加
     * lastName 姓
     */
    private String lastName;
    /**
     * firstName 名
     */
    private String firstName;
    /**
     * 用户账户id
     */
    private String accountId;

}

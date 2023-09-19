package com.hisun.kugga.framework.common.api.oauth2.dto;

import lombok.Data;

import java.util.List;

/**
 * OAuth2.0 访问令牌的校验 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class OAuth2AccessTokenCheckRespDTO {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 授权范围的数组
     */
    private List<String> scopes;
    /**
     * 用户账户id
     */
    private String accountId;

}

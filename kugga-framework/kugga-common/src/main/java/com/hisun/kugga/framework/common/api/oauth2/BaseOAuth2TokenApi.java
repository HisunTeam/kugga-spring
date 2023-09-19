package com.hisun.kugga.framework.common.api.oauth2;

import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;

public interface BaseOAuth2TokenApi {

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);

    OAuth2AccessTokenDTO getAccessToken(String accessToken);
}

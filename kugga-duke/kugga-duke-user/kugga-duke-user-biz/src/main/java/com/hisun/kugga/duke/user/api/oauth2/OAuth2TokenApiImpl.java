package com.hisun.kugga.duke.user.api.oauth2;

import com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.hisun.kugga.duke.user.controller.oauth2.vo.token.OAuth2AccessTokenCreateReqVo;
import com.hisun.kugga.duke.user.convert.auth.OAuth2TokenConvert;
import com.hisun.kugga.duke.user.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.hisun.kugga.duke.user.service.oauth2.OAuth2TokenService;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OAuth2.0 Token API 实现类
 *
 * @author 芋道源码
 */
@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi {

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    public OAuth2AccessTokenRespDTO createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        OAuth2AccessTokenCreateReqVo reqVo = new OAuth2AccessTokenCreateReqVo();
        BeanUtils.copyProperties(reqDTO, reqVo);
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(reqVo);
        // OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(reqDTO.getUserId(), reqDTO.getUserType(), reqDTO.getClientId(), reqDTO.getScopes());
        OAuth2AccessTokenRespDTO respDTO = new OAuth2AccessTokenRespDTO();
        BeanUtils.copyProperties(accessTokenDO, respDTO);
        return respDTO;
    }


    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        OAuth2AccessTokenCheckRespDTO respDTO = new OAuth2AccessTokenCheckRespDTO();
        OAuth2AccessTokenDO oAuth2AccessTokenDO = oauth2TokenService.checkAccessToken(accessToken);
        BeanUtils.copyProperties(oAuth2AccessTokenDO, respDTO);
        return respDTO;
    }

    @Override
    public OAuth2AccessTokenDTO getAccessToken(String accessToken) {
        return OAuth2TokenConvert.INSTANCE.convert1(oauth2TokenService.getAccessToken(accessToken));
    }

    @Override
    public OAuth2AccessTokenRespDTO removeAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(accessToken);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, clientId);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

}

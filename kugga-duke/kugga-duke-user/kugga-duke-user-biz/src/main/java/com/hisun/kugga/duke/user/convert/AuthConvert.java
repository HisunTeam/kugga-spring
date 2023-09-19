package com.hisun.kugga.duke.user.convert;

import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthLoginRespVO;
import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthSocialBindLoginReqVO;
import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthSocialQuickLoginReqVO;
import com.hisun.kugga.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.hisun.kugga.module.system.api.social.dto.SocialUserBindReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialBindLoginReqVO reqVO);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialQuickLoginReqVO reqVO);
//    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

    AppAuthLoginRespVO convert(com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenRespDTO accessTokenRespDTO);
}

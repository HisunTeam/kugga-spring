package com.hisun.kugga.module.member.convert.auth;

import com.hisun.kugga.module.member.controller.app.auth.vo.AppAuthLoginRespVO;
import com.hisun.kugga.module.member.controller.app.auth.vo.AppAuthSocialBindLoginReqVO;
import com.hisun.kugga.module.member.controller.app.auth.vo.AppAuthSocialQuickLoginReqVO;
import com.hisun.kugga.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import com.hisun.kugga.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.hisun.kugga.module.system.api.social.dto.SocialUserBindReqDTO;
import com.hisun.kugga.module.system.api.social.dto.SocialUserUnbindReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialBindLoginReqVO reqVO);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialQuickLoginReqVO reqVO);

    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);


    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

}

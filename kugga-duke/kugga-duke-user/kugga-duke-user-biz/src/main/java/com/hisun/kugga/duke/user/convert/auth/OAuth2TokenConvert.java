package com.hisun.kugga.duke.user.convert.auth;

import com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.hisun.kugga.duke.user.controller.oauth2.vo.token.OAuth2AccessTokenRespVO;
import com.hisun.kugga.duke.user.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenDTO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(OAuth2AccessTokenDO bean);

    OAuth2AccessTokenDTO convert1(OAuth2AccessTokenDO bean);

    PageResult<OAuth2AccessTokenRespVO> convert(PageResult<OAuth2AccessTokenDO> page);

    OAuth2AccessTokenRespDTO convert2(OAuth2AccessTokenDO bean);

}

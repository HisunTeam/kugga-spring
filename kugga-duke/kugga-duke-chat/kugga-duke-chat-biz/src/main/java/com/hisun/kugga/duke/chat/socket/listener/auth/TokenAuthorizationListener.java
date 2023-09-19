/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.hisun.kugga.duke.chat.socket.listener.auth;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.hisun.kugga.framework.common.api.oauth2.BaseOAuth2TokenApi;
import com.hisun.kugga.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.chat.socket.utils.SocketUtils.getHandshakeToken;

/**
 * 用户请求连接认证
 *
 * @author toi
 */
@Data
public class TokenAuthorizationListener implements AuthorizationListener {

    protected static Logger logger = LoggerFactory.getLogger(TokenAuthorizationListener.class);

    @Resource
    private final BaseOAuth2TokenApi baseOAuth2TokenApi;

    @Override
    public boolean isAuthorized(HandshakeData data) {

        String token = getHandshakeToken(data);
        if (StrUtil.isBlank(token)) {
            return false;
        }
        try {
            OAuth2AccessTokenCheckRespDTO oAuth2AccessTokenCheckRespDTO = baseOAuth2TokenApi.checkAccessToken(token);
        } catch (ServiceException e) {
            logger.info("socketio checkAccessToken:{}, {}, {}", token, e.getCode(), e.getMessage());
            return false;
        }
        return true;
    }

}

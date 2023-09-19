package com.hisun.kugga.duke.chat.socket.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.HandshakeData;
import lombok.Data;

@Data
public class SocketUtils {

    public static final String AUTHORIZATION_PARAM = "token";
    /**
     * HTTP Authorization header, equal to <code>Authorization</code>
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String authorizationHeaderName = AUTHORIZATION_HEADER;
    private static final String authorizationParamName = AUTHORIZATION_PARAM;

//    private static Map<String, Long> tokenToUserIdMap = new ConcurrentHashMap<>();

//    public static Long getSocketClientUserId(SocketIOClient client){
//        String token = getHandshakeToken(client.getHandshakeData());
//        return tokenToUserIdMap.get(token);
//    }

    public static String getHandshakeToken(HandshakeData data) {
        // 从header中获取token
        String token = data.getHttpHeaders().get(authorizationHeaderName);
        // 如果header中不存在token，则从参数中获取token
        if (StrUtil.isEmptyIfStr(token)) {
            return data.getSingleUrlParam(authorizationParamName);
        }
        return token;
    }

    public static String createMessageTokenString(Long roomId) {
        // message token 生成规则： roomId + 32位 uuid
        return roomId + IdUtil.fastSimpleUUID();
    }

    public static Long getRoomIdByMessageToken(String messageToken) {
        String sub = StrUtil.sub(messageToken, 0, messageToken.length() - 32);
        return Long.valueOf(sub);
    }

    //todo ??? 集群环境是否有问题
//    public static void addTokenToUserIdMap(String token, Long userId) {
//        tokenToUserIdMap.put(token, userId);
//    }
//    public static void deleteTokenToUserIdMap(String token) {
//        tokenToUserIdMap.remove(token);
//    }


//    public static Long getUserIdByToken(String token) {
//        return tokenToUserIdMap.get(token);
//    }
}

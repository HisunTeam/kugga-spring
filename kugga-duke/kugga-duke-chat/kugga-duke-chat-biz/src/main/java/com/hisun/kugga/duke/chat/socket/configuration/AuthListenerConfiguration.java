package com.hisun.kugga.duke.chat.socket.configuration;

import com.hisun.kugga.duke.chat.socket.listener.auth.TokenAuthorizationListener;
import com.hisun.kugga.framework.socketio.SocketioServerAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureBefore(SocketioServerAutoConfiguration.class)
@Import({
        /* token认证 **/
        TokenAuthorizationListener.class
})
public class AuthListenerConfiguration {

}

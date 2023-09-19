package com.hisun.kugga.server.module;

import com.hisun.kugga.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * System 模块的 Security 配置
 * <p>
 * 认证url配置
 */
@Configuration("kuggaSystemSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("kuggaSystemAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger 接口文档
                registry.antMatchers("/swagger-ui.html").anonymous()
                        .antMatchers("/swagger-resources/**").anonymous()
                        .antMatchers("/webjars/**").anonymous()
                        .antMatchers("/*/api-docs").anonymous();
                // Spring Boot Actuator 的安全配置
                registry.antMatchers("/actuator").anonymous()
                        .antMatchers("/actuator/**").anonymous();
                // Druid 监控
                registry.antMatchers("/druid/**").anonymous();

                // 用户鉴权放行接口
                registry.antMatchers(buildAppApi("/user/auth/register")).permitAll();
                registry.antMatchers(buildAppApi("/user/auth/email-login")).permitAll();
                registry.antMatchers(buildAppApi("/user/auth/resetPassword")).permitAll();
                registry.antMatchers(buildAppApi("/user/auth/send-email-code")).permitAll();
                registry.antMatchers(buildAppApi("/user/auth/send-email-link")).permitAll();
                registry.antMatchers(buildAppApi("/user/auth/forgetPassword")).permitAll();


                registry.antMatchers(buildAppApi("/duke/recommendation/page-league")).permitAll();
                //查询邀请公会信息
                registry.antMatchers(buildAppApi("/league/invite/{uuid}")).permitAll();
                //短链解析,并重定向
                registry.antMatchers(buildAppApi("/s/{shortCode}")).permitAll();
                registry.antMatchers(buildAppApi("/g/{shortCode}")).permitAll();
                registry.antMatchers(buildAppApi("/league/recommend-leagues")).permitAll();
                registry.antMatchers(buildAppApi("/league/search-leagues")).permitAll();
                registry.antMatchers(buildAppApi("/league/details")).permitAll();
                registry.antMatchers(buildAppApi("/league/account")).permitAll();
                // 获取公钥等信息
                registry.antMatchers(buildAppApi("/system/secret/getSecretInfo")).permitAll();

                //开发阶段临时测试使用，后期建议注释
                registry.antMatchers(buildAppApi("/user/auth/email-login-test")).permitAll();
                registry.antMatchers(buildAppApi("/plate/page-list")).permitAll();
                registry.antMatchers(buildAppApi("/posts/page-list")).permitAll();
                registry.antMatchers(buildAppApi("/posts/details")).permitAll();
                registry.antMatchers(buildAppApi("/posts/page-floor")).permitAll();
                registry.antMatchers(buildAppApi("/posts/page-comments")).permitAll();
                registry.antMatchers(buildAppApi("/league/label/simple-labels")).permitAll();
                registry.antMatchers(buildAppApi("/league/label/best-leagues")).permitAll();
                registry.antMatchers(buildAppApi("/plate/simple")).permitAll();
                registry.antMatchers(buildAppApi("/plate/page-list")).permitAll();
                registry.antMatchers(buildAppApi("/posts/real-time-hot-posts")).permitAll();
                registry.antMatchers(buildAppApi("/posts/rise-hot-posts")).permitAll();
                registry.antMatchers(buildAppApi("/posts/anonymous-hot-posts")).permitAll();
                registry.antMatchers(buildAppApi("/league/label/single-label")).permitAll();
                registry.antMatchers(buildAppApi("/league/label/label-leagues")).permitAll();
                registry.antMatchers(buildAppApi("/forum/label/hot-labels")).permitAll();
                registry.antMatchers(buildAppApi("/forum/label/vague-labels")).permitAll();
                registry.antMatchers(buildAppApi("/forum/label/posts-list")).permitAll();


                /**
                 * 后台内部调用放行接口 (batch-duke)
                 */
                //后台下单支付接口放行
                registry.antMatchers(buildAppApi("/league/subscribe/subscribeOrderPay")).permitAll();
                //通用邮件发送
                registry.antMatchers(buildAppApi("/system/email/innerGeneralSend")).permitAll();
                // batch-server调用duke-server 放行
                registry.antMatchers(buildAppApi("/red-packet/inner/detail")).permitAll();
                // batch 调用duke放行
                registry.antMatchers(buildAppApi("/system/messages/sendMessageClient")).permitAll();
            }

        };
    }


}

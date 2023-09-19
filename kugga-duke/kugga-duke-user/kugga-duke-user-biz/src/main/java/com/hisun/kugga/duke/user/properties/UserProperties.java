package com.hisun.kugga.duke.user.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/10 13:57
 */
@Data
@Component
@ToString
//@ConfigurationProperties(value = "kugga.forum.kugga-max")
@ConfigurationProperties(value = "duke.user")
public class UserProperties {
    /**
     * 区号 99
     */
    private String regionPrefix;
    /**
     * 忘记密码url前缀
     */
    private String forgetPwdLinkPrefix;

    private String test;
    private String test2;
}

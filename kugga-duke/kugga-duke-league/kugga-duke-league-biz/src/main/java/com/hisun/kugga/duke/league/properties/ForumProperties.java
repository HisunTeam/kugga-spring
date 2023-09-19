package com.hisun.kugga.duke.league.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhou_xiong
 */
@Data
@Component
@ConfigurationProperties(value = "kugga.forum.kugga-max")
public class ForumProperties {
    private String loginUrl;
    private String clientId;
    private String secret;
}

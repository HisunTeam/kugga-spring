package com.hisun.kugga.framework.email.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
@ConfigurationProperties("kugga.email")
public class EmailProperties {

    private String roundRobinType = "normal";

    private String fileFolder;

    private List<CustomMailProperties> properties = new ArrayList<>();

}

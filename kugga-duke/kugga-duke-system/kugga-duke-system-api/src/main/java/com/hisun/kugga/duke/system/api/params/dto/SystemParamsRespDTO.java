package com.hisun.kugga.duke.system.api.params.dto;

import lombok.Data;

/**
 * @author zuocheng
 */
@Data
public class SystemParamsRespDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 参数key
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数描述
     */
    private String description;
}

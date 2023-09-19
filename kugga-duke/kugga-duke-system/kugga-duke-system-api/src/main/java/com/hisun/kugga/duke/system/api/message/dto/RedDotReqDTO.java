package com.hisun.kugga.duke.system.api.message.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class RedDotReqDTO {
    private Long userId;
    private Boolean chatRedDot;
    private Boolean messageRedDot;
}

package com.hisun.kugga.duke.league.api.dto;

import lombok.Data;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/11/8 14:55
 */
@Data
public class UserGrowthLevelDTO {

    /**
     * 公会等级
     */
    private Integer growthLevel;
    /**
     * 等级名称
     */
    private String levelName;
}

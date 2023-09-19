package com.hisun.kugga.duke.league.vo.rule;

import lombok.Data;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/31 15:44
 */
@Data
public class SubscribeSelectVo {

    private static final String ON = "1";
    private static final String OFF = "0";

    /**
     * 各种订阅周期是否生效
     */
    private String month;
    private String quarter;
    private String year;
    private String forever;

    /**
     * 根据1_0_0_0构造出具体的状态值
     *
     * @param selectStr
     */
    public SubscribeSelectVo(String selectStr) {

        this.month = OFF;
        this.quarter = OFF;
        this.year = OFF;
        this.forever = OFF;

        if (selectStr != null && selectStr.length() == 7) {
            String[] arrays = selectStr.split("_");
            this.month = arrays[0];
            this.quarter = arrays[1];
            this.year = arrays[2];
            this.forever = arrays[3];
        }
    }
}

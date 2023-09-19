package com.hisun.kugga.duke.bos.enums;

import lombok.Getter;

/**
 * @Description:
 * @author： lzt
 * @Date 2022/9/15 15:03
 */
@Getter
public enum LeagueSortEnum {

    /**
     * 公会排序 first,second,third,forth,fifth,sixth
     */
    first(1, "first"),
    second(2, "second"),
    third(3, "third"),
    forth(4, "forth"),
    fifth(5, "fifth"),
    sixth(6, "sixth"),
    no_sorted(99, "未排序");

    private Integer code;
    private String msg;

    LeagueSortEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

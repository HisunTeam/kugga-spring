package com.hisun.kugga.duke.enums;

import lombok.Getter;

/**
 * @author zuocheng
 */
@Getter
public enum NumberEnum {
    /**
     * 数字0
     */
    ZERO("0", 0),
    /**
     * 数字1
     */
    ONE("1", 1),
    /**
     * 数字2
     */
    TWO("2", 2),
    /**
     * 数字3
     */
    THREE("3", 3),
    /**
     * 数字4
     */
    FOUR("4", 4),
    /**
     * 数字5
     */
    FIVE("5", 5),
    /**
     * 数字6
     */
    SIX("6", 6),
    /**
     * 数字7
     */
    SEVEN("7", 7),
    /**
     * 数字8
     */
    EIGHT("8", 8),
    /**
     * 数字9
     */
    NINE("9", 9);

    private String value;
    private int number;

    NumberEnum(String value, int number) {
        this.value = value;
        this.number = number;
    }
}

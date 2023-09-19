package com.hisun.kugga.framework.common.util.amount;

import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class AmountUtil {
    public static final BigDecimal MIN_AMOUNT = BigDecimal.ZERO;
    public static final BigDecimal HUNDRED = new BigDecimal("100");

    private AmountUtil() {
    }

    /**
     * 加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        return d1.add(d2).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
        return d1.subtract(d2).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        return d1.multiply(d2).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 除
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        if (d2.compareTo(MIN_AMOUNT) == 0) {
            return MIN_AMOUNT;
        }
        return d1.divide(d2, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal mulToInt(BigDecimal d1, BigDecimal d2) {
        return d1.multiply(d2).setScale(0, RoundingMode.HALF_UP);
    }

    public static String toString(BigDecimal intd) {
        return intd.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public static BigDecimal fromString(String dstr) {
        if (StrUtil.isBlank(dstr)) {
            return MIN_AMOUNT;
        }
        return new BigDecimal(dstr).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 元转成分
     *
     * @param yuan
     * @return
     */
    public static Integer yuanToFen(BigDecimal yuan) {
        return mul(yuan, HUNDRED).intValue();
    }

    /**
     * 分转成元
     *
     * @param fen
     * @return
     */
    public static BigDecimal fenToYuan(Integer fen) {
        fen = Optional.ofNullable(fen).orElse(0);
        return div(new BigDecimal(fen), AmountUtil.HUNDRED);
    }


}

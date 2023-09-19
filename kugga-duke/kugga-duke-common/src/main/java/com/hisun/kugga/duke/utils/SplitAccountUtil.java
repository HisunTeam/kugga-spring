package com.hisun.kugga.duke.utils;

import com.hisun.kugga.duke.enums.SplitAccountEnum;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Description: 分账工具类
 * @author： Lin
 * @Date 2022/9/14 14:47
 */
public class SplitAccountUtil {

    public static final String ONE_DECIMAL_POINT = "0.";
    public static final String TWO_DECIMAL_POINT = "0.0";

    /**
     * 三方分账 5：3：2
     *
     * @param amount
     * @return
     */
    public static SplitAccountVo splitByThree(BigDecimal amount) {
/*        if(ObjectUtil.equal(amount,BigDecimal.ZERO)){
            throw exception(SPLICT_AMOUNT_NOT_ZERO);
        }*/
        String[] rules = SplitAccountEnum.SPLIT_ACCOUNT_ONE.getRule().split(":");
        BigDecimal personAmount = amount.multiply(new BigDecimal(ONE_DECIMAL_POINT + rules[0])).setScale(2, RoundingMode.HALF_UP);
        BigDecimal leagueAmount = amount.multiply(new BigDecimal(ONE_DECIMAL_POINT + rules[1])).setScale(2, RoundingMode.HALF_UP);
        // 平台金额=总金额-其两个相加
        BigDecimal tem = personAmount.add(leagueAmount);
        BigDecimal platformAmount = amount.subtract(tem);

        SplitAccountVo accountVo = new SplitAccountVo()
                .setPersonAmount(personAmount)
                .setLeagueAmount(leagueAmount)
                .setPlatformAmount(platformAmount);
        return accountVo;
    }

    /**
     * 分账8:2
     *
     * @param amount
     * @return
     */
    public static SplitAccountVo splitByTwo(BigDecimal amount) {
/*        if(ObjectUtil.equal(amount,BigDecimal.ZERO)){
            throw exception(SPLICT_AMOUNT_NOT_ZERO);
        }*/
        String[] rules = SplitAccountEnum.SPLIT_ACCOUNT_TWO.getRule().split(":");
        BigDecimal leagueAmount = amount.multiply(new BigDecimal(ONE_DECIMAL_POINT + rules[0])).setScale(2, RoundingMode.HALF_UP);
        // 平台金额=总金额-公会
        BigDecimal platformAmount = amount.subtract(leagueAmount);

        SplitAccountVo accountVo = new SplitAccountVo()
                .setLeagueAmount(leagueAmount)
                .setPlatformAmount(platformAmount);
        return accountVo;
    }
}

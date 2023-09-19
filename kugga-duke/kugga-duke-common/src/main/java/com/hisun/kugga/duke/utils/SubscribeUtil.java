package com.hisun.kugga.duke.utils;

import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.league.LeagueSubscribeType;

import java.time.LocalDateTime;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/20 16:56
 */
public class SubscribeUtil {

    /**
     * 根据订阅类型获取过期时间
     * +n月-1天
     *
     * @return
     */
/*
    public static LocalDate getExpireTimeBySubscribeType(LeagueSubscribeType subscribeType){
        LocalDate localDate = null;
        switch (subscribeType) {
            case SUBSCRIBE_MONTH:
                localDate = LocalDate.now().plusMonths(1L).minusDays(1L);
                break;
            case SUBSCRIBE_QUARTER:
                localDate = LocalDate.now().plusMonths(3L).minusDays(1L);
                break;
            case SUBSCRIBE_YEAR:
                localDate = LocalDate.now().plusMonths(12L).minusDays(1L);
                break;
            case SUBSCRIBE_FOREVER:
                localDate = LocalDate.now().plusMonths(999L).minusDays(1L);
                break;
            default:
                throw exception(BusinessErrorCodeConstants.SUBSCRIBE_TYPE_ERROR);
        }
        return localDate;
    }
*/
    public static LocalDateTime getExpireTimeBySubscribeType(LeagueSubscribeType subscribeType) {
        LocalDateTime localDateTime = null;
        switch (subscribeType) {
            case SUBSCRIBE_MONTH:
                localDateTime = LocalDateTime.now().plusMonths(1L).minusDays(1L);
                break;
            case SUBSCRIBE_QUARTER:
                localDateTime = LocalDateTime.now().plusMonths(3L).minusDays(1L);
                break;
            case SUBSCRIBE_YEAR:
                localDateTime = LocalDateTime.now().plusMonths(12L).minusDays(1L);
                break;
            case SUBSCRIBE_FOREVER:
                localDateTime = LocalDateTime.now().plusMonths(999L).minusDays(1L);
                break;
            default:
                throw exception(BusinessErrorCodeConstants.SUBSCRIBE_TYPE_ERROR);
        }
        return localDateTime;
    }

    /**
     * 自动续期时 +n-2
     *
     * @param subscribeType
     * @return
     */
    public static LocalDateTime getExpireTimeBySubscribeTypeTwo(LeagueSubscribeType subscribeType) {
        LocalDateTime localDateTime = null;
        switch (subscribeType) {
            case SUBSCRIBE_MONTH:
                localDateTime = LocalDateTime.now().plusMonths(1L).minusDays(2L);
                break;
            case SUBSCRIBE_QUARTER:
                localDateTime = LocalDateTime.now().plusMonths(3L).minusDays(2L);
                break;
            case SUBSCRIBE_YEAR:
                localDateTime = LocalDateTime.now().plusMonths(12L).minusDays(2L);
                break;
            case SUBSCRIBE_FOREVER:
                localDateTime = LocalDateTime.now().plusMonths(999L).minusDays(2L);
                break;
            default:
                throw exception(BusinessErrorCodeConstants.SUBSCRIBE_TYPE_ERROR);
        }
        return localDateTime;
    }
}

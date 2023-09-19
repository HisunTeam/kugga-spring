package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.subscribe.SubscriptionOrderPayVO;

/**
 * @Description: 订阅流水处理类
 * @author： Lin
 * @Date 2022/10/19 14:58
 */
public interface LeagueSubscribeFlowServiceHandler {

    /**
     * 订阅自定义下单
     *
     * @param orderPayVO
     * @return
     */
    SubscriptionOrderPayVO customOrder(SubscriptionOrderPayVO orderPayVO);


    /**
     * 支付 + 修改支付状态
     *
     * @param orderPayVO
     * @return
     */
    void customPay(SubscriptionOrderPayVO orderPayVO);

    /**
     * 分账+修改状态
     *
     * @param orderPayVO
     */
    void customSplitAccount(SubscriptionOrderPayVO orderPayVO);

}

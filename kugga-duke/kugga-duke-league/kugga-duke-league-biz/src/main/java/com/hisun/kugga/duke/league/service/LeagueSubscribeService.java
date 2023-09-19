package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.dto.SubscriptionRenewalDTO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribePageReqVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeRespVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeUpdateReqVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeVo;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * @Description: 订阅服务
 * @author： Lin
 * @Date 2022/10/19 14:58
 */
public interface LeagueSubscribeService {

    /**
     * 订阅套餐
     *
     * @param subscribeVo
     */
    void subscribePackage(SubscribeVo subscribeVo);


    /**
     * 获取我的公会订阅信息
     *
     * @param pageVO
     * @return
     */
    PageResult<SubscribeRespVO> getLeagueSubscribePage(SubscribePageReqVO pageVO);

    /**
     * 取消订阅、继续订阅
     *
     * @param updateReqVO
     */
    void updateSubscribe(SubscribeUpdateReqVO updateReqVO);

    /**
     * 订阅续期 供bos后台直接下单且支付接口
     *
     * @param renewalVO
     */
    void subscribeRenewal(SubscriptionRenewalDTO renewalVO);
}

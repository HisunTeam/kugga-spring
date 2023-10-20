//package com.hisun.kugga.duke.batch.service;
//
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribePageReqVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeRespVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeUpdateReqVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeVo;
//import com.hisun.kugga.framework.common.pojo.PageResult;
//
///**
// * @Description: Subscription Service
// * @Author: Lin
// * @Date 2022/10/19 14:58
// */
//public interface LeagueSubscribeService {
//
//    /**
//     * Subscribe to a package
//     * @param subscribeVo The SubscribeVo object.
//     */
//    void subscribePackage(SubscribeVo subscribeVo);
//
//
//    /**
//     * Get information about my league subscriptions
//     * @param pageVO The SubscribePageReqVO object.
//     * @return PageResult containing SubscribeRespVO objects.
//     */
//    PageResult<SubscribeRespVO> getLeagueSubscribePage(SubscribePageReqVO pageVO);
//
//    /**
//     * Cancel or continue a subscription
//     * @param updateReqVO The SubscribeUpdateReqVO object.
//     */
//    void updateSubscribe(SubscribeUpdateReqVO updateReqVO);
//}

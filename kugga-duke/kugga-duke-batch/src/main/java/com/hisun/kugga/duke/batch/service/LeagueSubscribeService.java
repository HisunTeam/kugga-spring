//package com.hisun.kugga.duke.batch.service;
//
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribePageReqVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeRespVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeUpdateReqVO;
//import com.hisun.kugga.duke.league.vo.subscribe.SubscribeVo;
//import com.hisun.kugga.framework.common.pojo.PageResult;
//
///**
// * @Description: 订阅服务
// * @author： Lin
// * @Date 2022/10/19 14:58
// */
//public interface LeagueSubscribeService {
//
//    /**
//     * 订阅套餐
//     * @param subscribeVo
//     */
//    void subscribePackage(SubscribeVo subscribeVo);
//
//
//    /**
//     * 获取我的公会订阅信息
//     * @param pageVO
//     * @return
//     */
//    PageResult<SubscribeRespVO> getLeagueSubscribePage(SubscribePageReqVO pageVO);
//
//    /**
//     * 取消订阅、继续订阅
//     * @param updateReqVO
//     */
//    void updateSubscribe(SubscribeUpdateReqVO updateReqVO);
//}

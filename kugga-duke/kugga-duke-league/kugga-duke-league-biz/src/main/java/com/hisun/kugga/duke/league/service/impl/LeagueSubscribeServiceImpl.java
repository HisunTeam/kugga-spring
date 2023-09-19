package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.dto.SubscriptionRenewalDTO;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueSubscribeDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueSubscribeFlowDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueSubscribeFlowMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueSubscribeMapper;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueSubscribeFlowServiceHandler;
import com.hisun.kugga.duke.league.service.LeagueSubscribeService;
import com.hisun.kugga.duke.league.vo.subscribe.*;
import com.hisun.kugga.duke.utils.SubscribeUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/19 16:07
 */
@Slf4j
@Service
public class LeagueSubscribeServiceImpl implements LeagueSubscribeService {

    @Resource
    private LeagueMemberService leagueMemberService;
    @Resource
    private LeagueSubscribeMapper subscribeMapper;
    @Resource
    private LeagueSubscribeFlowServiceHandler flowServiceHandler;
    @Resource
    private LeagueSubscribeFlowMapper flowMapper;
    @Resource
    private InnerCallHelper innerCallHelper;

    @Override
    public void subscribePackage(SubscribeVo subscribeVo) {
        LeagueSubscribeDO subscribeDO = new LeagueSubscribeDO();
        subscribeDO.setLeagueId(subscribeVo.getLeagueId());
        subscribeDO.setUserId(subscribeVo.getUserId());
        subscribeDO.setSubscribeType(subscribeVo.getSubscribeType().getCode());
        subscribeDO.setPrice(subscribeVo.getAmount());
        subscribeDO.setExpireTime(SubscribeUtil.getExpireTimeBySubscribeType(subscribeVo.getSubscribeType()));
        subscribeDO.setStatus(true);
        subscribeDO.setExpireStatus(false);

        //如果是续定(取消订阅后的再次订阅)，直接修改订阅信息
        LeagueSubscribeDO existSubscribe = subscribeMapper.getByLeagueIdAndUserId(subscribeVo.getLeagueId(), subscribeVo.getUserId());
        if (ObjectUtil.isNotNull(existSubscribe)) {
            BeanUtils.copyProperties(subscribeDO, existSubscribe);
            subscribeMapper.updateById(existSubscribe);
            return;
        }

        subscribeMapper.insert(subscribeDO);
    }

    @Override
    public PageResult<SubscribeRespVO> getLeagueSubscribePage(SubscribePageReqVO pageVO) {
        Page<SubscribeRespVO> pageParam = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        pageVO.setUserId(getLoginUserId());
        Page<SubscribeRespVO> resPage = subscribeMapper.getSubscribePageByUserId(pageParam, pageVO);

        return new PageResult<SubscribeRespVO>().setList(resPage.getRecords()).setTotal(resPage.getTotal());
    }

    @Override
    public void updateSubscribe(SubscribeUpdateReqVO reqVO) {
        LeagueSubscribeDO subscribeDO = subscribeMapper.selectById(reqVO.getId());
        if (ObjectUtil.isNull(subscribeDO) || ObjectUtil.notEqual(subscribeDO.getUserId(), getLoginUserId())) {
            throw exception(SUBSCRIBE_RECORD_NOT_EXIST);
        }
        // 已过期后修改状态报错
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(subscribeDO.getExpireTime())) {
            throw exception(SUBSCRIBE_EXPIRE);
        }
        // 这里只修改订阅状态，实际过期状态定时任务解决
        LeagueSubscribeDO build = LeagueSubscribeDO.builder().id(reqVO.getId()).status(reqVO.getStatus()).build();
        subscribeMapper.updateById(build);
    }


    @Override
    public void subscribeRenewal(SubscriptionRenewalDTO reqVo) {
        log.info("inner subscribeRenewal, flowId:{}", reqVo.getFlowId());
        innerCallHelper.verifyCert(reqVo, Long.class, flowId -> ObjectUtil.equals(flowId, reqVo.getFlowId()));
        // 查询流水记录
        LeagueSubscribeFlowDO flowDo = flowMapper.selectById(reqVo.getFlowId());
        if (ObjectUtil.isNull(flowDo)) {
            throw exception(SUBSCRIBE_FLOW_RECORD_NOT_EXIST);
        }

        // 预下单
        SubscriptionOrderPayVO vo = new SubscriptionOrderPayVO();
        vo.setFlowId(flowDo.getId());
        vo.setUserId(flowDo.getUserId());
        vo.setPrice(flowDo.getPrice());
        vo.setLeagueId(flowDo.getLeagueId());

        vo = flowServiceHandler.customOrder(vo);
        //没有订单号说明下单失败 如余额不足等
        if (ObjectUtil.isEmpty(vo.getAppOrderNo())) {
            throw exception(CREATE_ORDER_ERROR);
        }

        //支付 + 修改支付状态
        flowServiceHandler.customPay(vo);

        //分账
        flowServiceHandler.customSplitAccount(vo);
    }
}

package com.hisun.kugga.duke.league.service.impl;

import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.PayStatusEnum;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueSubscribeFlowDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueSubscribeFlowMapper;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.service.LeagueSubscribeFlowServiceHandler;
import com.hisun.kugga.duke.league.vo.subscribe.SubscriptionOrderPayVO;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.order.dto.*;
import com.hisun.kugga.duke.utils.SplitAccountUtil;
import com.hisun.kugga.duke.utils.vo.SplitAccountVo;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/10/21 14:25
 */
@Slf4j
@Component
public class LeagueSubscribeFlowServiceHandlerImpl implements LeagueSubscribeFlowServiceHandler {

    @Resource
    private OrderApi orderApi;
    @Resource
    private LeagueSubscribeFlowMapper flowMapper;
    @Resource
    private LeagueMemberService memberService;

    @Override
    public SubscriptionOrderPayVO customOrder(SubscriptionOrderPayVO vo) {
        // 预下单
        OrderCreateRspDTO order = null;
        LeagueSubscribeFlowDO updateDo = new LeagueSubscribeFlowDO();
        updateDo.setId(vo.getFlowId());
        updateDo.setBusinessStatus(PayStatusEnum.NO_PAY.getValue());

        try {
            order = createOrder(vo.getUserId(), vo.getPrice());
            updateDo.setAppOrderNo(order.getAppOrderNo());
            vo.setAppOrderNo(order.getAppOrderNo());
        } catch (Exception e) {
            log.error("subscribe createOrder fail:{}", vo, e);
            updateDo.setBusinessStatus(PayStatusEnum.FAIL.getValue());
            if (e instanceof ServiceException) {
                //130003 如果是余额不足，需要备注标识 后续定时任务不发起了
                updateDo.setRemark(((ServiceException) e).getCode() + "");
            }
        }
        flowMapper.updateById(updateDo);
        return vo;
    }

    @Override
    public void customPay(SubscriptionOrderPayVO vo) {
        orderApi.pay(new PayReqDTO().setAppOrderNo(vo.getAppOrderNo()));

        LeagueSubscribeFlowDO updateDo = new LeagueSubscribeFlowDO();
        updateDo.setId(vo.getFlowId());
        updateDo.setBusinessStatus(PayStatusEnum.PAY.getValue());
        flowMapper.updateById(updateDo);
    }

    @Override
    public void customSplitAccount(SubscriptionOrderPayVO vo) {
        //查询公会创建者id
        Long leagueAdminId = memberService.getAdminIdByLeagueId(vo.getLeagueId());
        vo.setLeagueCreateId(leagueAdminId);

        this.splitAccount(vo);

        LeagueSubscribeFlowDO updateDo = new LeagueSubscribeFlowDO();
        updateDo.setId(vo.getFlowId());
        updateDo.setBusinessStatus(PayStatusEnum.SPLIT_ACCOUNT.getValue());
        flowMapper.updateById(updateDo);
    }


    /**
     * 分账
     *
     * @param orderPayVO
     */
    private void splitAccount(SubscriptionOrderPayVO orderPayVO) {
        SplitAccountVo accountVo = SplitAccountUtil.splitByThree(orderPayVO.getPrice());
        //加入公会 公会创建者、公会、平台 532分账
        ReceiverInfo leagueAdmin = new ReceiverInfo().setReceiverId(orderPayVO.getLeagueCreateId()).setAccountType(AccountType.USER).setAmount(accountVo.getPersonAmount());
        ReceiverInfo league = new ReceiverInfo().setReceiverId(orderPayVO.getLeagueId()).setAccountType(AccountType.LEAGUE).setAmount(accountVo.getLeagueAmount());
        ReceiverInfo platform = new ReceiverInfo().setReceiverId(1L).setAccountType(AccountType.PLATFORM).setAmount(accountVo.getPlatformAmount());
        SplitAccountReqDTO splitAccountReqDTO = new SplitAccountReqDTO()
                .setOrderType(OrderType.SUBSCRIPTION_LEAGUE_SPLIT)
                .setAppOrderNo(orderPayVO.getAppOrderNo())
                .setReceiverList(Arrays.asList(leagueAdmin, league, platform));
        orderApi.splitAccount(splitAccountReqDTO);
    }

    /**
     * 下单接口
     *
     * @param amount
     * @return
     */
    private OrderCreateRspDTO createOrder(Long userId, BigDecimal amount) {
        OrderCreateReqDTO req = new OrderCreateReqDTO()
                .setOrderType(OrderType.SUBSCRIPTION_LEAGUE)
                .setPayerId(userId)
                .setAccountType(AccountType.USER)
                .setAmount(amount);
        return orderApi.createOrder(req);
    }
}

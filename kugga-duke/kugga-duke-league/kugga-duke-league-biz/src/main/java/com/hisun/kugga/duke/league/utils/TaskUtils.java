package com.hisun.kugga.duke.league.utils;

import com.hisun.kugga.duke.enums.OrderType;
import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.duke.league.bo.task.TaskCreateBO;
import com.hisun.kugga.duke.league.bo.task.TaskLeagueBO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueRuleDO;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMemberMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleMapper;
import com.hisun.kugga.duke.league.dal.mysql.TaskMapper;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.framework.common.exception.ServiceException;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-04 18:14
 * @AllArgsConstructor 该注解会和 @Value 冲突导致项目无法启动
 * 所以改为如下注解 @RequiredArgsConstructor(onConstructor = @__(@Autowired))
 */
@Component
@Data
public class TaskUtils {

    @Resource
    OrderApi orderApi;
    @Resource
    LeagueRuleMapper leagueRuleMapper;
    @Resource
    LeagueMemberMapper leagueMemberMapper;
    @Resource
    LeagueMemberService leagueMemberService;
    @Resource
    LeagueMapper leagueMapper;
    @Resource
    TaskMapper taskMapper;

    /**
     * 多选公会判断金额
     * 1    ID和金额不能为空
     * 2    总金额计算不能错误
     * 判断金钱相加是否相等  请勿用BigDecimal的equals方法！！！
     *
     * @param list
     * @param amount
     */
    public static void checkLeagueList(List<TaskLeagueBO> list, BigDecimal amount) {
        if (list.size() != list.stream().filter(o -> Objects.nonNull(o.getId()) && Objects.nonNull(o.getAmount())).collect(Collectors.toList()).size()) {
            throw new ServiceException(NOT_NULL_LEAGUE_ID_AMOUNT);
        }
        if (amount.compareTo(list.stream().map(o -> o.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add)) != 0) {
            throw new ServiceException(TASK_AMOUNT_ERROR);
        }
    }

    public void checkOrderParams(TaskDO taskDO) {
        if (taskDO == null) {
            ServiceException.throwServiceException(ORDER_NOT_EXISTS);
        }
        if (!taskDO.getStatus().equals(TaskStatusEnum.TASK_STATUS_0)) {
            ServiceException.throwServiceException(ORDER_HAS_BEEN_PAID);
        }
    }

    public void checkOrderParams(String appOrderNo) {
        TaskDO taskDO = taskMapper.selectOne(TaskDO::getOrderRecord, appOrderNo);
        checkOrderParams(taskDO);
    }

    /**
     * 多选公会调用订单
     *
     * @param bo
     * @param list
     * @param orderType
     */
    public void checkLeagueCallOrder(TaskCreateBO bo, List<TaskLeagueBO> list, OrderType orderType) {
        //---------------------------------------------------------------------------
        //过滤金额为零的订单
/*        List<LeagueTaskCreateLeagueBO> filter = list.stream().filter(o -> BigDecimal.ZERO.compareTo(o.getAmount()) != 0).collect(Collectors.toList());
        if (!filter.isEmpty()) {
            OrderCreateReqDTO orderDTO = new OrderCreateReqDTO()
                    .setOrderType(orderType)
                    .setUserId(getLoginUserId())
                    .setEnterType(EnterType.LEAGUE);
            List<OrderCreateReqDTO.Payment> orderList = new ArrayList();
            filter.forEach(o -> orderList.add(new OrderCreateReqDTO.Payment().setEnterId(o.getId()).setAmount(o.getAmount())));
            orderDTO.setPaymentList(orderList);
            OrderCreateRspDTO order = orderApi.createOrder(orderDTO);
            order.getResults().forEach(o -> {
                Optional<LeagueTaskCreateLeagueBO> opt = list.stream().filter(o2 -> o.getEnterId().equals(o2.getId())).findAny();
                if (opt.isPresent()) {
                    //绑定订单号
                    opt.get().setOrderNo(o.getOrderSubId().toString());
                }
            });*/
        //绑定订单组号
        // bo.setOrderGroupNo(order.getAppOrderNo());
    }

    /**
     * 下单
     *
     * @param list
     */
    public void zay01(List<TaskLeagueBO> list) {
//        WHERE t1.user_id = #{userId}
//        AND t1.deleted = false
/*        List<LeagueRuleDO> rules = leagueRuleMapper.selectList();
        list.forEach(o -> {
            Optional<LeagueRuleDO> first = rules.stream().filter(rule -> o.getId().equals(rule.getLeagueId())).findFirst();
            if (first.isPresent()) {
                o.setPayType(TaskPayTypeEnum.FREE);
            }
        });*/
        //---------------------------------------------------------------------------
        //过滤金额为零的订单
/*        List<LeagueTaskCreateLeagueBO> filter = list.stream().filter(o -> BigDecimal.ZERO.compareTo(o.getAmount()) != 0).collect(Collectors.toList());
        if (!filter.isEmpty()) {
            OrderCreateReqDTO orderDTO = new OrderCreateReqDTO()
                    .setOrderType(orderType)
                    .setUserId(getLoginUserId())
                    .setEnterType(EnterType.LEAGUE);
            List<OrderCreateReqDTO.Payment> orderList = new ArrayList();
            filter.forEach(o -> orderList.add(new OrderCreateReqDTO.Payment().setEnterId(o.getId()).setAmount(o.getAmount())));
            orderDTO.setPaymentList(orderList);
            OrderCreateRspDTO order = orderApi.createOrder(orderDTO);
            order.getResults().forEach(o -> {
                Optional<LeagueTaskCreateLeagueBO> opt = list.stream().filter(o2 -> o.getEnterId().equals(o2.getId())).findAny();
                if (opt.isPresent()) {
                    //绑定订单号
                    opt.get().setOrderNo(o.getOrderSubId().toString());
                }
            });*/
        //绑定订单组号
        // bo.setOrderGroupNo(order.getAppOrderNo());
    }

    /**
     * 邀请写推荐报告
     * 判断推荐报告价格
     * 1    已加入的公会免费
     * 2
     *
     * @param list
     */
    public void judgeLeagueRealTimePrice(List<TaskLeagueBO> list, TaskTypeEnum taskType) {
        List<Long> league = list.stream().map(TaskLeagueBO::getId).collect(Collectors.toList());
        List<LeagueRuleDO> rules = leagueRuleMapper.selectList(LeagueRuleDO::getLeagueId, league);
        if (league.size() != rules.size()) {
            throw new ServiceException(LEAGUE_RULE_ERROR);
        }
        /** 加入的公会 写推荐报告免费 。公会认证无视次规则 */
        if (TaskTypeEnum.TASK_TYPE_1.equals(taskType)) this.freeWriteReport(rules);
        list.forEach(o -> {
            rules.forEach(rule -> {
                if (o.getId().equals(rule.getLeagueId())) {
                    if (!judgePrice(o.getAmount(), rule, taskType)) {
                        throw new ServiceException(NOT_EXPECT_PRICE);
                    }
                }
            });
        });
    }

    private void freeWriteReport(List<LeagueRuleDO> rules) {
        List<Long> userJoinLeague = leagueMemberService.getUserJoinLeague(getLoginUserId());
        rules.forEach(rule -> {
            if (userJoinLeague.stream().filter(o -> rule.getLeagueId().equals(o)).findFirst().isPresent()) {
                rule.setReportPrice(BigDecimal.ZERO);
            }
        });
    }

    private Boolean judgePrice(BigDecimal price, LeagueRuleDO rule, TaskTypeEnum taskType) {
        if (TaskTypeEnum.TASK_TYPE_1.equals(taskType)) {
            return price.compareTo(rule.getReportPrice()) == 0;
        } else if (TaskTypeEnum.TASK_TYPE_2.equals(taskType)) {
            return price.compareTo(rule.getAuthPrice()) == 0;
        }
        throw new ServiceException(JUDGE_PRICE_ERROR);
    }

    public void leagueAuthFlagList(List<TaskLeagueBO> list) {
        List<Long> ids = list.stream().map(TaskLeagueBO::getId).collect(Collectors.toList());
        leagueAuthFlag(ids);
    }

    /**
     * 拓展可增加公会规则判断
     *
     * @param ids
     */
    public void leagueAuthFlag(List<Long> ids) {
        List<LeagueDO> leagueList = leagueMapper.selectList(LeagueDO::getId, ids);
        if (ids.size() != leagueList.size()) {
            throw new ServiceException(LEAGUE_IS_NULL);
        }
        leagueList.forEach(o -> {
            if (o.getAuthFlag().equals(Boolean.FALSE)) {
                throw new ServiceException(LEAGUE_NOT_AUTH);
            }
        });
    }

    public boolean leagueAuthFlag(long id) {
        LeagueDO leagueDO = leagueMapper.selectById(id);
        if (Objects.isNull(leagueDO)) {
            throw new ServiceException(LEAGUE_IS_NULL);
        }
        return leagueDO.getAuthFlag();
    }
}

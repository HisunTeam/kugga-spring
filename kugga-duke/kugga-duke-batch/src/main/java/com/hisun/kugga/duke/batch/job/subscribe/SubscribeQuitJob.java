package com.hisun.kugga.duke.batch.job.subscribe;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.leaguesubscribe.LeagueSubscribeDO;
import com.hisun.kugga.duke.batch.dal.dataobject.message.MessagesDO;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeFlowMapper;
import com.hisun.kugga.duke.batch.dal.mysql.leaguesubscribe.LeagueSubscribeMapper;
import com.hisun.kugga.duke.batch.dal.mysql.message.MessagesMapper;
import com.hisun.kugga.duke.batch.service.MessageService;
import com.hisun.kugga.duke.entity.ContentParamVo;
import com.hisun.kugga.duke.enums.message.*;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd;

/**
 * @Description: 公会订阅-退出公会
 * @author： Lin
 * @Date 2022/10/20 15:09
 */
@Slf4j
@Component
public class SubscribeQuitJob implements JobHandler {

    /**
     * 余额不足错误码
     */
    private static final String Insufficient_Balance_Error_Code = "130003";

    @Value("${duke.league.backed.subscribeOrderPay:}")
    private String url;
    @Resource
    private LeagueSubscribeMapper subscribeMapper;
    @Resource
    private LeagueSubscribeFlowMapper flowMapper;
    @Resource
    private InnerCallHelper innerCallHelper;
    @Resource
    private MessageService messageService;
    @Resource
    private MessagesMapper messagesMapper;

    /**
     * 公会订阅的套餐，定时续费
     *
     * @param param 参数
     * @return
     * @throws Exception
     */
    @Override
    public String execute(String param) throws Exception {
        log.info(" SubscribePayJob 定时处理公会订阅支付数据 start:{}", param);

        /*
        SubscribePayJob 跑定时任务，查询nowDate-1过期且 订阅状态status=true,过期状态expireStatus=false的的数据进行续费，
            续费成功延期、不成功如余额不足改status为false(这时realStatus=true)
        SubscribeQuitJob 跑定时任务，查询nowDate-1过期且 status=false(待退出公会的人员)的数据进行退会 + expireStatus=false(堵重)
            修改expireStatus=true、退出公会、发送退出消息
         */
        // 当天跑过期时间为昨天的数据，  日期-1，status订阅状态为false

        LocalDateTime now = LocalDateTime.now();
        String expireTime = DateUtil.format(now.minusDays(1L), yyyy_MM_dd);
        List<LeagueSubscribeDO> subscribes = subscribeMapper.selectSubscribes(expireTime, false, false);
        if (ObjectUtil.isEmpty(subscribes)) {
            return GlobalErrorCodeConstants.SUCCESS.getMsg();
        }

        for (LeagueSubscribeDO subscribe : subscribes) {
            doQuitSubscribe(subscribe);
        }

        log.info(" SubscribePayJob 定时处理公会订阅支付数据 end:{}", LocalDateTime.now());
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

    /**
     * 取消订阅
     *
     * @param subscribe
     */
    private void doQuitSubscribe(LeagueSubscribeDO subscribe) {
        //修改过期状态、公会成员成员退出、消息通知
        subscribeMapper.updateSubscribeExpireStatusToTrue(subscribe.getId());

        subscribeMapper.deleteLeagueMember(subscribe.getLeagueId(), subscribe.getUserId(), LocalDateTime.now());
    }
}

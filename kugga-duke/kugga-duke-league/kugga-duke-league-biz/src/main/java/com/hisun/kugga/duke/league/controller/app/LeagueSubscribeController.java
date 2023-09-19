package com.hisun.kugga.duke.league.controller.app;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.duke.dto.SubscriptionRenewalDTO;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.league.service.LeagueSubscribeService;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribePageReqVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeRespVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeUpdateReqVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @Author: Lin
 * @Date: 2022-09-13 15:12
 */
@Api(tags = "A9.公会订阅")
@RestController
@RequestMapping("/league/subscribe")
@Validated
@Slf4j
public class LeagueSubscribeController {

    @Resource
    private LeagueSubscribeService subscribeService;
    @Resource
    private InnerCallHelper innerCallHelper;

    @GetMapping("/page")
    @ApiOperation("我的订阅记录")
    public CommonResult<PageResult<SubscribeRespVO>> getMySubscribePage(@Valid SubscribePageReqVO pageVO) {
        PageResult<SubscribeRespVO> pageResult = subscribeService.getLeagueSubscribePage(pageVO);
        return success(pageResult);
    }


    @PutMapping("/update")
    @ApiOperation("取消订阅、继续订阅")
    public CommonResult<Boolean> updateSubscribe(@Valid @RequestBody SubscribeUpdateReqVO updateReqVO) {
        subscribeService.updateSubscribe(updateReqVO);
        return success(true);
    }


    /**
     * 订阅续期 供bos后台直接下单且支付接口
     *
     * @param renewalVO
     * @return
     */
    @PostMapping("/subscribeOrderPay")
    public CommonResult<Boolean> subscribeOrderPay(@Valid @RequestBody SubscriptionRenewalDTO renewalVO) {
        subscribeService.subscribeRenewal(renewalVO);
        return success(true);
    }

    /**
     * @param subscriptionRenewalDTO
     * @return
     */
    @PostMapping("/orderPayHttpTest")
    public CommonResult<Boolean> orderPayHttpTest(@Valid @RequestBody SubscriptionRenewalDTO subscriptionRenewalDTO) {

        //String key = String.format(ORDER_LEAGUE_SUBSCRIBE, renewalVO.getUuid());
        //String val = renewalVO.getFlowId()+"_"+renewalVO.getUuid();
        String uuid = innerCallHelper.genCert(subscriptionRenewalDTO.getFlowId());

        String url = "http://localhost:18081/app-api/league/subscribe/subscribeOrderPay";
        //设置请求参数
        subscriptionRenewalDTO.setUuid(uuid);
        //存放请求头，可以存放多个请求头
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
//        headers.put("Authorization", "Bearer 22222");

        //发送post请求
        String postResult = HttpRequest.post(url).addHeaders(headers)
                //传输参数
                .body(JSONUtil.toJsonStr(subscriptionRenewalDTO))
                .execute()
                .body();
        log.info("post结果打印{}", postResult);


        return success(true);
    }


}

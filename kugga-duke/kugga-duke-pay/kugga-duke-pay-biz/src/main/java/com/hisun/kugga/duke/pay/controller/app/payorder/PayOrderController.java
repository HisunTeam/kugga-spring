package com.hisun.kugga.duke.pay.controller.app.payorder;

import com.hisun.kugga.duke.pay.api.order.OrderApi;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.service.payorder.PayOrderService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

@Api(tags = "支付模块 - 订单")
@RestController
@RequestMapping("/duke/pay")
@Validated
public class PayOrderController {

    @Resource
    private PayOrderService payOrderService;
    @Resource
    private WalletApi walletApi;
    @Resource
    private OrderApi orderApi;

    /*@PostMapping("/create")
    @ApiOperation("创建订单--测试用")
    public CommonResult<OrderCreateRspDTO> createOrder(@Valid @RequestBody OrderCreateReqDTO createReqVO) {
        return success(payOrderService.createOrder(createReqVO));
    }

    @PostMapping("/pay")
    @ApiOperation("支付--测试用")
    public CommonResult pay(@Valid @RequestBody PayReqDTO payReqDTO) {
        payOrderService.pay(payReqDTO);
        return success();
    }

    @PostMapping("/account")
    @ApiOperation("分账--测试用")
    public CommonResult doAccount(@Valid @RequestBody SplitAccountReqDTO splitAccountReqDTO) {
        payOrderService.splitAccount(splitAccountReqDTO);
        return success();
    }

    @PostMapping("/refund")
    @ApiOperation("退款--测试用")
    public CommonResult doRefund(@Valid @RequestBody RefundReqDTO refundReqDTO) {
        payOrderService.refund(refundReqDTO);
        return success();
    }

    @PostMapping("/create-account")
    @ApiOperation("创建钱包账户--测试用")
    public CommonResult<CreateAccountRspBody> createAccount() {
        CreateAccountRspBody account = walletApi.createAccount(new CreateAccountReqBody());
        return success(account);
    }

    @GetMapping("/account-detail")
    @ApiOperation("查询账户--测试用")
    public CommonResult<AccountDetailRspBody> accountDetail(@RequestParam String account) {
        AccountDetailRspBody detail = walletApi.accountDetail(new AccountDetailReqBody().setAccount(account));
        return success(detail);
    }*/

   /* @PostMapping("/red-packet")
    @ApiOperation("红包发放--测试用")
    public CommonResult<RedPacketApplyRspDTO> apply(@Valid @RequestBody RedPacketApplyReqDTO redPacketApplyReqDTO) {
        RedPacketApplyRspDTO redPacketApplyRspDTO = orderApi.redPacketApply(redPacketApplyReqDTO);
        return success(redPacketApplyRspDTO);
    }*/

    @GetMapping("/cancel")
    @ApiOperation("取消支付订单")
    public CommonResult cancel(@RequestParam("orderNo") String appOrderNo) {
        payOrderService.cancel(appOrderNo);
        return success();
    }

}

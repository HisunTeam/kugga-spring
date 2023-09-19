package com.hisun.kugga.duke.pay.controller.app.charge;

import com.hisun.kugga.duke.pay.controller.app.charge.vo.ChargeDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeReqVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeRspVO;
import com.hisun.kugga.duke.pay.service.chargeorder.ChargeService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author: zhou_xiong
 */
@Api(tags = "充值")
@RestController
@RequestMapping("/duke/charge")
@Validated
public class ChargeController {
    @Resource
    private ChargeService chargeService;

    @PostMapping("/preCharge")
    @ApiOperation("预充值")
    public CommonResult<PreChargeRspVO> preCharge(@Valid @RequestBody PreChargeReqVO preChargeReqVO) {
        return success(chargeService.preCharge(preChargeReqVO));
    }

    @GetMapping("/detail")
    @ApiOperation("查询充值订单状态")
    public CommonResult<ChargeDetailRspVO> detail(@RequestParam("orderNo") String walletOrderNo) {
        return success(chargeService.getChargeDetail(walletOrderNo));
    }

    @GetMapping("/cancel")
    @ApiOperation("取消充值订单")
    public CommonResult cancel(@RequestParam("orderNo") String walletOrderNo) {
        chargeService.cancel(walletOrderNo);
        return success();
    }

}

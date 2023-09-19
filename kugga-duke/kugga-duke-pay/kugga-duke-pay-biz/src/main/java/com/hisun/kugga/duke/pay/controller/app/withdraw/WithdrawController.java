package com.hisun.kugga.duke.pay.controller.app.withdraw;

import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalReqVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalRspVO;
import com.hisun.kugga.duke.pay.service.withdraw.WithdrawService;
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
@Api(tags = "提现")
@RestController
@RequestMapping("/duke/withdraw")
@Validated
public class WithdrawController {
    @Resource
    private WithdrawService withdrawService;

    @PostMapping("/PayPal")
    @ApiOperation("提现至PayPal")
    public CommonResult<WithdrawToPayPalRspVO> toPayPal(@Valid @RequestBody WithdrawToPayPalReqVO withdrawToPayPalReqVO) {
        // 钱包提现有问题，暂时直接报错
        // ServiceException.throwServiceException(BusinessErrorCodeConstants.TEMP_ERROR);
        return success(withdrawService.toPayPal(withdrawToPayPalReqVO));
    }

    @GetMapping("/detail")
    @ApiOperation("查询提现订单状态")
    public CommonResult<WithdrawDetailRspVO> detail(@RequestParam("orderNo") String walletOrderNo) {
        return success(withdrawService.getWithdrawDetail(walletOrderNo));
    }


}

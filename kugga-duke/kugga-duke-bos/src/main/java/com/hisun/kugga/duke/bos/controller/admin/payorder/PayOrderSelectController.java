package com.hisun.kugga.duke.bos.controller.admin.payorder;

import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.*;
import com.hisun.kugga.duke.bos.service.payorder.PayOrderSelectService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.excel.core.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "3管理后台 - 支付订单")
@RestController
@RequestMapping("/duke/pay-order")
@Validated
public class PayOrderSelectController {

    @Resource
    private PayOrderSelectService payOrderSelectService;

    @PostMapping("/page")
    @ApiOperation("分页查询订单")
    public CommonResult<PageResult<PayOrderRespVO>> pageLeagueDisplay(@Valid @RequestBody PayOrderPageReqVO reqVO) {
        return success(payOrderSelectService.pagePayOrder(reqVO));
    }


    @GetMapping("/export-excel")
    @ApiOperation("导出支付订单 Excel")
    public void exportPayOrderExcel(@Valid PayOrderExportReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        List<PayOrderExcelVO> payOrderList = payOrderSelectService.getPayOrderList(exportReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "交易订单数据.xls", "数据", PayOrderExcelVO.class, payOrderList);
    }

    @PostMapping("/pageChargeWithdraw")
    @ApiOperation("分页查询订单")
    public CommonResult<PageResult<ChargeWithdrawOrderRespVO>> pageChargeWithdrawDisplay(@Valid @RequestBody PayOrderPageReqVO reqVO) {
        return success(payOrderSelectService.pageChargeWithdrawOrder(reqVO));
    }


    @GetMapping("/exportChargeWithdraw-excel")
    @ApiOperation("导出支付订单 Excel")
    public void exportChargeWithdrawExcel(@Valid PayOrderExportReqVO exportReqVO,
                                          HttpServletResponse response) throws IOException {
        List<ChargeWithdrawOrderExcelVO> payOrderList = payOrderSelectService.listChargeWithdrawOrder(exportReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "充值提现订单数据.xls", "数据", ChargeWithdrawOrderExcelVO.class, payOrderList);
    }

    @GetMapping("/order-type")
    @ApiOperation("所有订单类型")
    public CommonResult<Map<String, String>> selectAllOrderType() {
        return success(payOrderSelectService.selectAllOrderType());
    }

    @GetMapping("/order-status")
    @ApiOperation("所有订单类型")
    public CommonResult<Map<String, String>> selectAllStatus() {
        return success(payOrderSelectService.selectAllStatus());
    }
    @GetMapping("/orderChargeWithdraw-status")
    @ApiOperation("所有订单类型")
    public CommonResult<Map<String, String>> selectAllChargeWithdrawStatus() {
        return success(payOrderSelectService.selectAllChargeWithdrawStatus());
    }

}

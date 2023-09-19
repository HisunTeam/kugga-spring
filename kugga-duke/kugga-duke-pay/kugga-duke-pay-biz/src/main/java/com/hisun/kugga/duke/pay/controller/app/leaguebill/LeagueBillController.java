package com.hisun.kugga.duke.pay.controller.app.leaguebill;

import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.RedPacketInfo;
import com.hisun.kugga.duke.pay.service.leaguebill.LeagueBillService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "公会账单")
@RestController
@RequestMapping("/duke/league-bill")
@Validated
public class LeagueBillController {

    @Resource
    private LeagueBillService leagueBillService;

    @GetMapping("/page")
    @ApiOperation("获得公会账单分页")
    public CommonResult<PageResult<LeagueBillPageRspVO>> getLeagueBillPage(@Valid LeagueBillPageReqVO pageVO) {
        PageResult<LeagueBillPageRspVO> pageResult = leagueBillService.pageQuery(pageVO);
        return success(pageResult);
    }

    @GetMapping("/red-packet/detail")
    @ApiOperation("通过billId查询红包发放详情")
    public CommonResult<List<RedPacketInfo>> redPacketDetail(@RequestParam("leagueId") Long leagueId,
                                                             @RequestParam("billId") Long billId) {
        List<RedPacketInfo> result = leagueBillService.redPacketDetail(leagueId, billId);
        return success(result);
    }

}

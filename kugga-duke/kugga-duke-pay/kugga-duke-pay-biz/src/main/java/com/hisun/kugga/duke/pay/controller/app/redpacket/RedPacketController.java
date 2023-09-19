package com.hisun.kugga.duke.pay.controller.app.redpacket;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.dto.RedPacketDetailReqDTO;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyReqDTO;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyRspDTO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.BonusCalculateRspVO;
import com.hisun.kugga.duke.pay.service.redpacket.RedPacketService;
import com.hisun.kugga.duke.vo.RedPacketDetailRspVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author: zhou_xiong
 */
@Api(tags = "红包")
@RestController
@RequestMapping("/red-packet")
@Validated
public class RedPacketController {
    @Resource
    private RedPacketService redPacketService;
    @Resource
    private InnerCallHelper innerCallHelper;

    @GetMapping("/calculate")
    @ApiOperation("红包金额计算")
    public CommonResult<BonusCalculateRspVO> calculate(@RequestParam("leagueId") Long leagueId) {
        return success(redPacketService.calculate(leagueId));
    }

    @GetMapping("/handout")
    @ApiOperation("红包发放")
    public CommonResult<RedPacketApplyRspDTO> handout(@RequestParam("redPacketId") Long redPacketId) {
        return success(redPacketService.handout(redPacketId));
    }

    @GetMapping("/detail")
    @ApiOperation("查询红包发放结果")
    public CommonResult<RedPacketDetailRspVO> detail(@RequestParam("appOrderNo") String appOrderNo) {
        return success(redPacketService.getRedPacketDetail(appOrderNo, false));
    }

    @PostMapping("/inner/detail")
    @ApiOperation("查询红包发放结果-内部调用")
    public CommonResult<RedPacketDetailRspVO> innerDetail(@RequestBody RedPacketDetailReqDTO redPacketDetailReqDTO) {
        innerCallHelper.verifyCert(redPacketDetailReqDTO, String.class,
                appOrderNo -> StrUtil.equals(redPacketDetailReqDTO.getAppOrderNo(), appOrderNo));
        return success(redPacketService.getRedPacketDetail(redPacketDetailReqDTO.getAppOrderNo(), true));
    }

    @PostMapping("/handout-test")
    @ApiOperation("红包发放测试接口")
    public CommonResult<RedPacketApplyRspDTO> handoutTest(@RequestBody RedPacketApplyReqDTO redPacketApplyReqDTO) {
        return success(redPacketService.redPacketApply(redPacketApplyReqDTO));
    }
}

package com.hisun.kugga.duke.system.controller.app.message;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesPageReqVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesRespVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesUpdateReqVO;
import com.hisun.kugga.duke.system.service.messages.MessagesService;
import com.hisun.kugga.duke.system.service.messages.client.MessageClient;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "A5-消息管理")
@RestController
@RequestMapping("/system/messages")
@Validated
@Slf4j
public class MessagesController {

    private static final String MESSAGE_TEMPLATE = "message:test";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MessagesService messagesService;
    @Resource
    private MessageClient messageClient;
    @Resource
    private InnerCallHelper innerCallHelper;

    @PostMapping("/sendMessageClient")
    @ApiOperation("1.消息client")
    public CommonResult<Long> sendMessageClient(@Valid @RequestBody SendMessageReqDTO messageReqDTO) {
        log.info("inner sendMessage, {}", messageReqDTO);
        innerCallHelper.verifyCert(messageReqDTO, Long.class, businessId -> ObjectUtil.equals(businessId, messageReqDTO.getBusinessId()));

        messageClient.sendMessage(messageReqDTO);
        return success();
    }

    @PutMapping("/update-deal")
    @ApiOperation("2.消息已处理")
    public CommonResult<Boolean> updateDealMessages(@Valid @RequestBody MessagesUpdateReqVO updateReqVO) {
        messagesService.updateDealMessages(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-task-deal")
    @ApiOperation("2.任务所有消息已处理")
    public CommonResult<Boolean> updateDealMessagesTask(@Valid @RequestBody MessagesUpdateReqDTO reqDTO) {
        messagesService.dealMessagesInner(reqDTO);
        return success(true);
    }

    @PutMapping("/update-read")
    @ApiOperation("3.消息已读")
    public CommonResult<Boolean> updateReadMessages(@Valid @RequestBody MessagesUpdateReqVO updateReqVO) {
        messagesService.updateReadMessages(updateReqVO);
        return success(true);
    }

    @PutMapping("/cleanRedSpot")
    @ApiOperation("4.清除小红点、一键已读")
    public CommonResult<Boolean> cleanRedSpot() {
        messagesService.cleanRedSpot(SecurityFrameworkUtils.getLoginUserId());
        return success(true);
    }


    @GetMapping("/getRedSpot")
    @ApiOperation("5.获取用户是否有未读消息")
    public CommonResult<Boolean> getRedSpot() {
        return success(messagesService.getAllUnRead(SecurityFrameworkUtils.getLoginUserId()));
    }

    @GetMapping("/page")
    @ApiOperation("6.获得消息分页")
    public CommonResult<PageResult<MessagesRespVO>> getMessagesPage(@Valid MessagesPageReqVO pageVO) {
        //todo test use
        if (ObjectUtil.isNull(pageVO.getReceiverId())) {
            pageVO.setReceiverId(SecurityFrameworkUtils.getLoginUserId());
        }
        PageResult<MessagesRespVO> pageResult = messagesService.getPageMessages(pageVO);

        return success(pageResult);
    }

}

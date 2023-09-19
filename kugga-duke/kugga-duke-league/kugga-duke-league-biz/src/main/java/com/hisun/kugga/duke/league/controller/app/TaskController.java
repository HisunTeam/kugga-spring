package com.hisun.kugga.duke.league.controller.app;

import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.service.TaskService;
import com.hisun.kugga.duke.league.vo.task.*;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-26 17:46
 */
@Api(tags = "task - 任务")
@RestController
@RequestMapping("/league/task")
@Validated
@Slf4j
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/init")
    @ApiOperation("初始化任务")
    public CommonResult<TaskInitResultVO> init(@Valid @RequestBody TaskInitVO vo) {
        TaskInitBO bo = JsonUtils.parseObject(JsonUtils.toJsonString(vo), TaskInitBO.class);
        TaskInitResultVO init = taskService.init(bo);
        return success(init);
    }

    @PostMapping("/create")
    @ApiOperation("创建任务")
    public CommonResult<TaskCreateResultVO> create(@Valid @RequestBody TaskCreateVO vo) {
        TaskCreateBO bo = JsonUtils.parseObject(JsonUtils.toJsonString(vo), TaskCreateBO.class);
//        LeagueTaskCreateBO bo = LeagueTaskConvert.INSTANCE.convert(vo);//框架编译转化方法有问题暂不放开,用JSON序列化
        TaskCreateResultVO create = taskService.create(bo);
        return success(create);
    }

    @PostMapping("/accept")
    @ApiOperation("接受任务")
    public CommonResult accept(@Valid @RequestBody TaskAcceptVO vo) {
        TaskAcceptBO bo = JsonUtils.parseObject(JsonUtils.toJsonString(vo), TaskAcceptBO.class);
        return taskService.accept(bo);
    }

    @PostMapping("/finish")
    @ApiOperation("完成任务")
    public CommonResult finish(@Valid @RequestBody TaskFinishVO vo) {
        TaskFinishBO bo = JsonUtils.parseObject(JsonUtils.toJsonString(vo), TaskFinishBO.class);
        TaskFinishResultBO finish = taskService.finish(bo);
        return success(finish);
    }
}

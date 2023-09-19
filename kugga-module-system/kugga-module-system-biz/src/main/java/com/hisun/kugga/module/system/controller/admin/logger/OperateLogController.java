package com.hisun.kugga.module.system.controller.admin.logger;

import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.common.util.collection.CollectionUtils;
import com.hisun.kugga.framework.common.util.collection.MapUtils;
import com.hisun.kugga.framework.excel.core.util.ExcelUtils;
import com.hisun.kugga.framework.operatelog.core.annotations.OperateLog;
import com.hisun.kugga.module.system.controller.admin.logger.vo.operatelog.OperateLogExcelVO;
import com.hisun.kugga.module.system.controller.admin.logger.vo.operatelog.OperateLogExportReqVO;
import com.hisun.kugga.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import com.hisun.kugga.module.system.controller.admin.logger.vo.operatelog.OperateLogRespVO;
import com.hisun.kugga.module.system.convert.logger.OperateLogConvert;
import com.hisun.kugga.module.system.dal.dataobject.logger.OperateLogDO;
import com.hisun.kugga.module.system.dal.dataobject.user.AdminUserDO;
import com.hisun.kugga.module.system.service.logger.OperateLogService;
import com.hisun.kugga.module.system.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理后台 - 操作日志")
@RestController
@RequestMapping("/system/operate-log")
@Validated
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;
    @Resource
    private AdminUserService userService;

    @GetMapping("/page")
    @ApiOperation("查看操作日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public CommonResult<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageReqVO reqVO) {
        PageResult<OperateLogDO> pageResult = operateLogService.getOperateLogPage(reqVO);

        // 获得拼接需要的数据
        Collection<Long> userIds = CollectionUtils.convertList(pageResult.getList(), OperateLogDO::getUserId);
        Map<Long, AdminUserDO> userMap = userService.getUserMap(userIds);
        // 拼接数据
        List<OperateLogRespVO> list = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(operateLog -> {
            OperateLogRespVO respVO = OperateLogConvert.INSTANCE.convert(operateLog);
            list.add(respVO);
            // 拼接用户信息
            MapUtils.findAndThen(userMap, operateLog.getUserId(), user -> respVO.setUserNickname(user.getNickname()));
        });
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @ApiOperation("导出操作日志")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:operate-log:export')")
    @OperateLog(type = EXPORT)
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogExportReqVO reqVO) throws IOException {
        List<OperateLogDO> list = operateLogService.getOperateLogs(reqVO);

        // 获得拼接需要的数据
        Collection<Long> userIds = CollectionUtils.convertList(list, OperateLogDO::getUserId);
        Map<Long, AdminUserDO> userMap = userService.getUserMap(userIds);
        // 拼接数据
        List<OperateLogExcelVO> excelDataList = OperateLogConvert.INSTANCE.convertList(list, userMap);
        // 输出
        ExcelUtils.write(response, "操作日志.xls", "数据列表", OperateLogExcelVO.class, excelDataList);
    }

}

package com.hisun.kugga.duke.system.api.params;


import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.duke.system.service.SystemParamsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统参数实现类
 *
 * @author zuocheng
 */
@Service
public class SystemParamsApiImpl implements SystemParamsApi {

    @Resource
    private SystemParamsService systemParamsService;

    @Override
    public SystemParamsRespDTO getSystemParams(String type, String key) {
        return systemParamsService.getSystemParams(type, key);
    }

    @Override
    public String getParamsByTypeAndKey(String type, String key) {
        return systemParamsService.getParamsByTypeAndKey(type, key);
    }
}

package com.hisun.kugga.duke.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.hisun.kugga.duke.enums.system.SystemParamEnum;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.duke.system.dal.dataobject.SystemParamsDO;
import com.hisun.kugga.duke.system.dal.mysql.SystemParamsMapper;
import com.hisun.kugga.duke.system.service.SystemParamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class SystemParamsServiceImpl implements SystemParamsService {
    @Resource
    private SystemParamsMapper systemParamsMapper;

    /**
     * {@link SystemParamEnum}
     *
     * @param type
     * @param key
     * @return
     */
    @Override
    public SystemParamsRespDTO getSystemParams(String type, String key) {
        SystemParamsDO systemParamsDO = systemParamsMapper.selectByParamKey(type, key);
        return Convert.convert(SystemParamsRespDTO.class, systemParamsDO);
    }

    @Override
    public String getParamsByTypeAndKey(String type, String key) {
        return systemParamsMapper.selectByParamKey(type, key).getValue();
    }
}

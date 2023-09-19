package com.hisun.kugga.duke.system.service;

import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;

/**
 * @author zuocheng
 */
public interface SystemParamsService {
    /**
     * 根据type与key查询参数
     *
     * @param type
     * @param key
     * @return
     */
    SystemParamsRespDTO getSystemParams(String type, String key);

    String getParamsByTypeAndKey(String type, String key);
}

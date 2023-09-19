package com.hisun.kugga.duke.system.api.params;

import com.hisun.kugga.duke.enums.system.SystemParamEnum;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;

/**
 * 系统参数获取接口
 *
 * @author zuocheng
 */
public interface SystemParamsApi {
    /**
     * 根据类型与KEY获取系统参数
     *
     * @param type
     * @param key
     * @return
     */
    SystemParamsRespDTO getSystemParams(String type, String key);

    /**
     * 根据类型与KEY获取系统参数 {@link SystemParamEnum}
     *
     * @param type
     * @param key
     * @return
     */
    String getParamsByTypeAndKey(String type, String key);
}

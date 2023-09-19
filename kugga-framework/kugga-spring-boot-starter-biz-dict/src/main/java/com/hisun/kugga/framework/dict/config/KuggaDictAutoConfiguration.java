package com.hisun.kugga.framework.dict.config;

import com.hisun.kugga.framework.dict.core.util.DictFrameworkUtils;
import com.hisun.kugga.module.system.api.dict.DictDataApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KuggaDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataApi dictDataApi) {
        DictFrameworkUtils.init(dictDataApi);
        return new DictFrameworkUtils();
    }

}

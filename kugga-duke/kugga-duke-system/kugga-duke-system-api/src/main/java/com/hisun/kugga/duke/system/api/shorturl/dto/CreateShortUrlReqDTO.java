package com.hisun.kugga.duke.system.api.shorturl.dto;

import com.hisun.kugga.duke.system.enums.EffectiveUnitEnum;
import com.hisun.kugga.duke.system.enums.ShortUrlTypeEnum;
import lombok.Data;

/**
 * @author zuocheng
 */
@Data
public class CreateShortUrlReqDTO {
    /**
     * url
     * 例:"https://www.baidu.com"
     */
    private String url;
    /**
     * 短链类型
     */
    private ShortUrlTypeEnum type;
    /**
     * 有效期单位（s:秒、m:分、H:时、D:天、M:月、Y:年）
     * {@link EffectiveUnitEnum}
     */
    private EffectiveUnitEnum effectiveUnit;
    /**
     * 有效期
     */
    private Integer effectivePeriod;
}

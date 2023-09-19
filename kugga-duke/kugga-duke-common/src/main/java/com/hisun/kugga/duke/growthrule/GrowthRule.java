package com.hisun.kugga.duke.growthrule;

import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.enums.GrowthType;

import java.util.function.Consumer;

/**
 * @author: zhou_xiong
 */
public interface GrowthRule {
    /**
     * 设置升级类型
     *
     * @return
     */
    GrowthType growthType();

    /**
     * 设置成长值限制，可以从配置文件读或者db，暂时先写死
     *
     * @return
     */
    GrowthLimit growthLimit();

    /**
     * 根据升级规则计算出成长值，然后基于成长值做其他操作
     *
     * @param t
     * @param consumer 成长值持久化操作
     * @return 成长值
     */
    <T extends GrowthDTO> Integer growthValue(T t, Consumer<T> consumer);
}

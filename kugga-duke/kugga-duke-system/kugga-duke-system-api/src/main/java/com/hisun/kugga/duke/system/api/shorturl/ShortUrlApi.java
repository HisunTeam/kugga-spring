package com.hisun.kugga.duke.system.api.shorturl;

import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlReqDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlRspDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.QueryShortUrlRspDTO;

/**
 * @author zuocheng
 */
public interface ShortUrlApi {
    /**
     * 生成短链接
     *
     * @param reqDTO
     * @return
     */
    CreateShortUrlRspDTO createShortUrl(CreateShortUrlReqDTO reqDTO);

    /**
     * 根据ID查询短链接信息
     *
     * @param id
     * @return
     */
    QueryShortUrlRspDTO queryShortUrlById(Long id);

}

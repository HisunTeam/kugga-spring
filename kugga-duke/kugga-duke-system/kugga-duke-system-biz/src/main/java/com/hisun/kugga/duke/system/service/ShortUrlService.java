package com.hisun.kugga.duke.system.service;

import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlReqDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlRspDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.QueryShortUrlRspDTO;
import com.hisun.kugga.duke.system.controller.app.shorturl.vo.ShortCodeRespVO;

/**
 * 短链服务
 *
 * @author zuocheng
 */
public interface ShortUrlService {
    /**
     * 生成短链接
     *
     * @param reqDTO
     * @return
     */
    CreateShortUrlRspDTO createShortUrl(CreateShortUrlReqDTO reqDTO);

    /**
     * 根据短链CODE,获取到长链信息
     *
     * @param shortCode
     * @return
     */
    String getLongUrl(String shortCode);

    /**
     * 根据ID查询短链接信息
     *
     * @param id
     * @return
     */
    QueryShortUrlRspDTO queryShortUrlById(Long id);

    /**
     * 根据ID查询短链接信息
     *
     * @param shortCode
     * @return
     */
    ShortCodeRespVO queryShortUrlByCode(String shortCode);
}

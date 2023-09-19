package com.hisun.kugga.duke.system.api.shorturl;

import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlReqDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.CreateShortUrlRspDTO;
import com.hisun.kugga.duke.system.api.shorturl.dto.QueryShortUrlRspDTO;
import com.hisun.kugga.duke.system.service.ShortUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 短链接服务
 *
 * @author zuocheng
 */
@Service
public class ShortUrlApiImpl implements ShortUrlApi {
    @Resource
    private ShortUrlService shortUrlService;

    @Override
    public CreateShortUrlRspDTO createShortUrl(CreateShortUrlReqDTO reqDTO) {
        return shortUrlService.createShortUrl(reqDTO);
    }

    @Override
    public QueryShortUrlRspDTO queryShortUrlById(Long id) {
        return shortUrlService.queryShortUrlById(id);
    }
}

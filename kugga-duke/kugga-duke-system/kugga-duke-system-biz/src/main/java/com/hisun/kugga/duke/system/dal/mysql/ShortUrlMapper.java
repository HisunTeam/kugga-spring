package com.hisun.kugga.duke.system.dal.mysql;

import com.hisun.kugga.duke.system.dal.dataobject.ShortUrlDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * 短链接绑定 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ShortUrlMapper extends BaseMapperX<ShortUrlDO> {

    /**
     * 根据长连接查询,是否存在有效的短链
     *
     * @param url
     * @param uri
     * @return
     */
    default ShortUrlDO selectLongEffect(String url, String uri) {
        return selectOne(new LambdaQueryWrapperX<ShortUrlDO>()
                .eq(ShortUrlDO::getUrl, url)
                .eq(ShortUrlDO::getUri, uri)
                .eq(ShortUrlDO::getExpireFlag, false)
                .ge(ShortUrlDO::getExpireTime, LocalDateTime.now().withNano(0))
                .orderByDesc(ShortUrlDO::getId));
    }

    /**
     * 查询生效的短连接
     *
     * @param reqDO
     * @return
     */
    default Long selectEffect(ShortUrlDO reqDO) {
        return selectCount(new LambdaQueryWrapperX<ShortUrlDO>()
                .eq(ShortUrlDO::getShotCode, reqDO.getShotCode())
                .eq(ShortUrlDO::getExpireFlag, reqDO.getExpireFlag())
                .le(ShortUrlDO::getCreateTime, reqDO.getCreateTime())
                .ge(ShortUrlDO::getExpireTime, reqDO.getExpireTime())
                .orderByDesc(ShortUrlDO::getId));
    }

    /**
     * 根据ShotCode查询,查询短链信息（取最新的一条）
     *
     * @param shotCode
     * @return
     */
    default ShortUrlDO selectShortUrl(String shotCode) {
        return selectOne(new LambdaQueryWrapperX<ShortUrlDO>()
                .eq(ShortUrlDO::getShotCode, shotCode)
                .orderByDesc(ShortUrlDO::getId));
    }
}

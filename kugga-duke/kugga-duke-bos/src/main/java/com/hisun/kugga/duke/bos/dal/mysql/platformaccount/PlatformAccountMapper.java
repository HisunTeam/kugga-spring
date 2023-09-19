package com.hisun.kugga.module.duke.dal.mysql.platformaccount;


import com.hisun.kugga.duke.bos.dal.dataobject.platformaccount.PlatformAccountDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台账户 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PlatformAccountMapper extends BaseMapperX<PlatformAccountDO> {

    default PlatformAccountDO selectAccountId() {
        return selectOne(new LambdaQueryWrapperX<PlatformAccountDO>()
                .isNotNull(PlatformAccountDO::getAccountId).last("limit 1")
        );
    }

}

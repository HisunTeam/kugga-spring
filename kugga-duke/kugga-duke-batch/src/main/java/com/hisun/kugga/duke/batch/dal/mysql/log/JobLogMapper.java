package com.hisun.kugga.duke.batch.dal.mysql.log;

import com.hisun.kugga.duke.batch.dal.dataobject.log.JobLogDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * Task Logs Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface JobLogMapper extends BaseMapperX<JobLogDO> {

}

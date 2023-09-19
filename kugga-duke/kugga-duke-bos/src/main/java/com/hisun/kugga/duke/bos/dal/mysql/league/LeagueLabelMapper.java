package com.hisun.kugga.duke.bos.dal.mysql.league;


import com.hisun.kugga.duke.bos.controller.admin.league.vo.LeagueLabelVO;
import com.hisun.kugga.duke.bos.dal.dataobject.league.LeagueDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 公会 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueLabelMapper extends BaseMapperX<LeagueDO> {

    /**
     * 查询公会标签
     *
     * @return
     */
    List<LeagueLabelVO> getLeagueLabels();

}

package com.hisun.kugga.duke.bos.dal.mysql.recommendation;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationRespVO;
import com.hisun.kugga.duke.bos.dal.dataobject.recommendation.DukeRecommendationDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 推荐报告 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DukeRecommendationMapper extends BaseMapperX<DukeRecommendationDO> {

    /**
     * 分页查询公会推荐列表
     *
     * @param page
     * @param firstName
     * @param lastName
     * @param email
     * @return
     */
    IPage<RecommendationRespVO> pageRecommendation(Page page, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email);


}

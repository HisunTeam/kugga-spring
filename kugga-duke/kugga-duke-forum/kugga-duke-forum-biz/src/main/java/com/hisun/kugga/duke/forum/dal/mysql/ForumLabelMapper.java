package com.hisun.kugga.duke.forum.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumLabelDO;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 论坛标签标签信息表 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-08
 */
@Mapper
public interface ForumLabelMapper extends BaseMapperX<ForumLabelDO> {

    /**
     * 查询一定时间范围内的热度标签列表
     * @param startTime
     * @param endTime
     * @param limitNum
     * @return
     */
    List<ForumLabelDO> selectRangeHotDesc(@Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          @Param("limitNum") int limitNum);

    /**
     * 模糊匹配标签列表
     *
     * @param str
     * @return
     */
    default PageResult<ForumLabelDO> selectVague(PageParam pageParam, String str){
        return selectPage(pageParam,new LambdaQueryWrapperX<ForumLabelDO>()
                .like(ForumLabelDO::getLabelName, str)
                .orderByDesc(ForumLabelDO::getCreateTime)
                .orderByDesc(ForumLabelDO::getHotNum)
        );
    }

    /**
     * 根据名称查询标签信息
     * @param labelName
     * @return
     */
    default ForumLabelDO selectByName(String labelName){
        return selectOne(new LambdaQueryWrapperX<ForumLabelDO>().eq(ForumLabelDO::getLabelName, labelName));
    }

    /**
     * 点踩加1
     * @param id
     * @return
     */
    default int hotPlusOne(long id){
        return update(null, new LambdaUpdateWrapper<ForumLabelDO>()
                .setSql("hot_num = hot_num + 1").eq(ForumLabelDO::getId, id));
    }

    /**
     * 点踩减1
     * @param id
     * @return
     */
    default int hotSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<ForumLabelDO>()
                .setSql("hot_num = hot_num - 1").eq(ForumLabelDO::getId, id));
    }
}

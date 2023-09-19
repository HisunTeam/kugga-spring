package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsImageDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 贴子图片存储表 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-10-25
 */
@Mapper
public interface PostsImageMapper extends BaseMapperX<PostsImageDO> {
    /**
     * 根据贴子ID查询,贴子图片列表只查前三张
     *
     * @param postsId
     * @return
     */
    default List<PostsImageDO> selectByPostsId(long postsId) {
        return selectList(new LambdaQueryWrapperX<PostsImageDO>()
                .eq(PostsImageDO::getPostsId, postsId)
                .orderByAsc(PostsImageDO::getId));
    }

    /**
     * 根据贴子ID删除贴子内容
     *
     * @param postsId
     * @return
     */
    default int deleteByPostsId(long postsId) {
        return delete(new LambdaQueryWrapperX<PostsImageDO>().eq(PostsImageDO::getPostsId, postsId));
    }
}

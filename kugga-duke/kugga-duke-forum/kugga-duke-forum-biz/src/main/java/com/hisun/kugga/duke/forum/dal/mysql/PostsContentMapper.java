package com.hisun.kugga.duke.forum.dal.mysql;


import com.hisun.kugga.duke.forum.dal.dataobject.PostsContentDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 贴子内容表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:32:47
 */
@Mapper
public interface PostsContentMapper extends BaseMapperX<PostsContentDO> {
    /**
     * 根据posts_id查询贴子内容
     *
     * @param postsId
     * @return
     */
    default List<PostsContentDO> selectByPostsId(Long postsId) {
        return selectList(new LambdaQueryWrapperX<PostsContentDO>()
                .eq(PostsContentDO::getPostsId, postsId)
                .orderByAsc(PostsContentDO::getId)
        );
    }

    /**
     * 根据postsId,删除内容
     *
     * @param postsId
     * @return
     */
    default int deleteByPostsId(Long postsId) {
        return delete(new LambdaQueryWrapperX<PostsContentDO>()
                .eq(PostsContentDO::getPostsId, postsId)
        );
    }


}

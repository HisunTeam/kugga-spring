package com.hisun.kugga.duke.forum.dal.mysql;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorDO;
import com.hisun.kugga.duke.forum.vo.PostsFloorVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 贴子回复楼层表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:33:27
 */
@Mapper
public interface PostsFloorMapper extends BaseMapperX<PostsFloorDO> {

    /**
     * 查询楼层列表(正序)
     *
     * @param page
     * @param postsId
     * @return
     */
    IPage<PostsFloorVO> pageFloorAsc(Page page, @Param("postsId") Long postsId);

    /**
     * 查询楼层列表(倒序)
     *
     * @param page
     * @param postsId
     * @return
     */
    IPage<PostsFloorVO> pageFloorDesc(Page page, @Param("postsId") Long postsId);

    /**
     * 根据msgId楬楼层信息
     *
     * @param msgId
     * @return
     */
    default PostsFloorDO selectByMsgId(String msgId) {
        return selectOne(new LambdaQueryWrapperX<PostsFloorDO>().eq(PostsFloorDO::getMsgId, msgId));
    }

    /**
     * 查询楼层信息
     *
     * @param id
     * @return
     */
    PostsFloorVO floor(@Param("id") Long id);

    /**
     * 点赞加1
     * @param id
     * @return
     */
    default int praisePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsFloorDO>()
                .setSql("praise_num = praise_num + 1").eq(PostsFloorDO::getId, id));
    }

    /**
     * 点赞减1
     * @param id
     * @return
     */
    default int praiseSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsFloorDO>()
                .setSql("praise_num = praise_num - 1").eq(PostsFloorDO::getId, id));
    }

    /**
     * 点踩加1
     * @param id
     * @return
     */
    default int tramplePlusOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsFloorDO>()
                .setSql("trample_num = trample_num + 1").eq(PostsFloorDO::getId, id));
    }

    /**
     * 点踩减1
     * @param id
     * @return
     */
    default int trampleSubtractOne(long id){
        return update(null, new LambdaUpdateWrapper<PostsFloorDO>()
                .setSql("trample_num = trample_num - 1").eq(PostsFloorDO::getId, id));
    }
}

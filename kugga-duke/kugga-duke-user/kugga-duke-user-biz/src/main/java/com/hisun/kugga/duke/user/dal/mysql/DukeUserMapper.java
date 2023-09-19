package com.hisun.kugga.duke.user.dal.mysql;

import com.hisun.kugga.duke.user.controller.vo.auth.UserAccountVo;
import com.hisun.kugga.duke.user.controller.vo.user.UserPageReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DukeUserMapper extends BaseMapperX<UserDO> {

    default PageResult<UserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getUsername, reqVO.getUsername())
                .eqIfPresent(UserDO::getEmail, reqVO.getEmail())
                .eqIfPresent(UserDO::getPassword, reqVO.getPassword())
                .eqIfPresent(UserDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(UserDO::getPhone, reqVO.getPhone())
                .eqIfPresent(UserDO::getWork, reqVO.getWork())
                .eqIfPresent(UserDO::getCountry, reqVO.getCountry())
                .eqIfPresent(UserDO::getAddress, reqVO.getAddress())
                .eqIfPresent(UserDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(UserDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(UserDO::getId));
    }


    default UserDO selectByMobile(String phone) {
        return selectOne(UserDO::getPhone, phone);
    }

    default UserDO selectByEmail(String email) {
        return selectOne(UserDO::getEmail, email);
    }

    default Long selectCountByUserName(String username) {
        return selectCount(UserDO::getUsername, username);
    }


    /**
     * 插入用户账户表
     *
     * @param userAccount
     */
    void insertUserAccount(UserAccountVo userAccount);

    /**
     * 通过用户id查询用户账户
     *
     * @param userAccount
     * @return
     */
    UserAccountVo selectAccountByUserId(UserAccountVo userAccount);

    /**
     * 查询某地区的序号
     *
     * @param regionPrefix
     * @return
     */
    Integer selectNumByRegionPrefix(@Param("regionPrefix") String regionPrefix);

    /**
     * 区号序号自增
     *
     * @param regionPrefix
     * @param num
     * @return
     */
    Integer updateAtGenAdd(@Param("regionPrefix") String regionPrefix, @Param("num") Integer num);
}

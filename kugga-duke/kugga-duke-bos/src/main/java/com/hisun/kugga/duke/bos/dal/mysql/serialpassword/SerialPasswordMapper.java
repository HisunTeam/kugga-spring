package com.hisun.kugga.duke.bos.dal.mysql.serialpassword;


import com.hisun.kugga.duke.bos.dal.dataobject.serialpassword.SerialPasswordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 序列密码信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SerialPasswordMapper extends BaseMapperX<SerialPasswordDO> {

    /**
     * 查询序列密码信息
     *
     * @param groupName 密码组名
     * @param sortId    密码顺序
     * @return
     */
    default SerialPasswordDO getSerialPassword(String groupName, Integer sortId) {
        return selectOne(new LambdaQueryWrapperX<SerialPasswordDO>()
                .eq(SerialPasswordDO::getSortId, sortId)
                .eq(SerialPasswordDO::getGroupName, groupName)
                .orderByDesc(SerialPasswordDO::getId).last("limit 1")
        );
    }

    /**
     * 查询序列密码信息
     *
     * @param groupName
     * @return
     */
    default List<SerialPasswordDO> getSerialPasswordList(String groupName) {
        return selectList(new LambdaQueryWrapperX<SerialPasswordDO>()
                .eq(SerialPasswordDO::getGroupName, groupName)
                .orderByDesc(SerialPasswordDO::getSortId).last("limit 2")
        );
    }


}

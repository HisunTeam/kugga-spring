package com.hisun.kugga.duke.pay.dal.mysql.userbill;

import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface UserBillMapper extends BaseMapperX<UserBillDO> {

    default PageResult<UserBillDO> selectPage(UserBillPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserBillDO>()
                .eqIfPresent(UserBillDO::getUserId, SecurityFrameworkUtils.getLoginUserId())
                .eq(UserBillDO::getDeleted, false)
                .orderByDesc(UserBillDO::getCreateTime)
                .select(UserBillDO::getCreateTime, UserBillDO::getRemark,
                        UserBillDO::getAmount, UserBillDO::getFee, UserBillDO::getStatus));
    }

}

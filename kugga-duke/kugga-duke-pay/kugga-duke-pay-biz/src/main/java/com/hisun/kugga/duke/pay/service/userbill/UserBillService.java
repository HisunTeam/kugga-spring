package com.hisun.kugga.duke.pay.service.userbill;

import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.pay.bo.BillBO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillCreateReqVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillUpdateReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 用户账单 Service 接口
 *
 * @author zhou_xiong
 */
public interface UserBillService {
    /**
     * 获得用户账单分页
     *
     * @param pageReqVO 分页查询
     * @return 用户账单分页
     */
    PageResult<UserBillPageRspVO> getUserBillPage(UserBillPageReqVO pageReqVO);

    /**
     * 如果订单没生成账单，就生成
     *
     * @param userBillDO
     */
    void insertIfNotExist(UserBillDO userBillDO);
}

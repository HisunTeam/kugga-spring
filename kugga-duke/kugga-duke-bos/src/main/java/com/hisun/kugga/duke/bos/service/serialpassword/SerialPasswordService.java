package com.hisun.kugga.duke.bos.service.serialpassword;


import com.hisun.kugga.duke.bos.controller.admin.serialpassword.vo.SerialPasswordUpdateReqVO;

/**
 * 序列密码信息 Service 接口
 *
 * @author 芋道源码
 */
public interface SerialPasswordService {

    /**
     * 查询用户编写推荐报告记录
     *
     * @param reqVO 请求VO
     */
    Boolean modifyPasswordStatus(SerialPasswordUpdateReqVO reqVO);

    /**
     * 查询用户编写推荐报告记录
     */
    Boolean judgePasswordEffective();

}

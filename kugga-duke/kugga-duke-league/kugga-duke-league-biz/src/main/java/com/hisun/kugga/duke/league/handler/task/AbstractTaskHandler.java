package com.hisun.kugga.duke.league.handler.task;

import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.enums.TaskStatusEnum;
import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.constants.TaskConstants;
import com.hisun.kugga.duke.league.dal.dataobject.TaskDO;
import com.hisun.kugga.duke.league.dal.mysql.TaskMapper;
import com.hisun.kugga.duke.league.utils.MyBeanUtils;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.hisun.kugga.duke.league.constants.TaskConstants.REQUIRED_PARAMETER_IS_EMPTY;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:26
 * 策略+工厂+模板 模式
 * 不用懒加载，业务场景不适用
 */
public abstract class AbstractTaskHandler implements InitializingBean {

    @Resource
    TaskMapper taskMapper;
    @Resource
    UserAccountApi userAccountApi;

    /**
     * 初始化任务（下单）命名避开关键词order
     *
     * @param bo
     * @return
     */
    abstract public TaskInitResultVO initTask(TaskInitBO bo);

    /**
     * 创建任务 （支付）
     *
     * @param bo
     * @return
     */
    abstract public TaskCreateResultVO createTask(TaskCreateBO bo);

    public CommonResult accept(TaskAcceptBO bo) {
        throw new ServiceException(TaskConstants.NOT_EXPECT_METHOD);
    }

    abstract public TaskFinishResultBO finish(TaskFinishBO vo);


    protected void insertTask(TaskInitBO bo) {
        insertTask(bo, null);
    }

    /**
     * 创建任务调用此方法
     * 可重写此方法自行实现
     * taskDO对象中非空属性会覆盖任务对象中的属性
     *
     * @param bo
     * @return
     */
    protected void insertTask(TaskInitBO bo, TaskDO taskDO) {
        TaskDO insert = TaskDO.builder()
                .id(null)
                .userId(getLoginUserId())
                .type(bo.getType())
                .status(TaskStatusEnum.TASK_STATUS_0)
                .payType(TaskPayTypeEnum.PAY)
                .amount(bo.getAmount())
                .orderRecord(null)
                .businessParams(null)
                .expiresTime(LocalDateTime.now().minusYears(-100L))
                .build();
        if (Objects.nonNull(taskDO)) {
            BeanUtils.copyProperties(taskDO, insert, MyBeanUtils.getNullPropertyNames(taskDO));
        }
        if (BigDecimal.ZERO.compareTo(insert.getAmount()) == 0) {
            insert.setPayType(TaskPayTypeEnum.FREE)
                    .setStatus(TaskStatusEnum.TASK_STATUS_1);
        }
        int result = taskMapper.insert(insert);
        if (result != 1) {
            throw new ServiceException(TaskConstants.TASK_CREATE_FAILED);
        }
        bo.setId(insert.getId());//放入任务ID
    }

    /**
     * 校验支付密码
     *
     * @param password
     * @param publicKey
     */
    protected void verifyPassword(String password, String publicKey) {
        AccountPwdVerifyDTO accountPwdVerifyDTO = new AccountPwdVerifyDTO();
        accountPwdVerifyDTO.setPassword(password);
        accountPwdVerifyDTO.setPublicKey(publicKey);
        if (!userAccountApi.verifyPayPassword(accountPwdVerifyDTO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PASSWORD_WRONG);
        }
    }

    /**
     * 校验支付参数
     *
     * @param bo
     */
    protected void checkPayTaskParams(TaskCreateBO bo) {
        if (StringUtils.isEmpty(bo.getAppOrderNo())) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isEmpty(bo.getPassword())) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isEmpty(bo.getPublicKey())) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
    }
}

package com.hisun.kugga.duke.bos.service.serialpassword;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.bos.controller.admin.serialpassword.vo.SerialPasswordUpdateReqVO;
import com.hisun.kugga.duke.bos.dal.dataobject.serialpassword.SerialPasswordDO;
import com.hisun.kugga.duke.bos.dal.mysql.serialpassword.SerialPasswordMapper;
import com.hisun.kugga.duke.bos.enums.PasswordInputFlagEnum;
import com.hisun.kugga.duke.bos.enums.SerialPasswordEnum;
import com.hisun.kugga.duke.bos.enums.SerialPasswordSortIdEnum;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil;
import com.hisun.kugga.framework.web.core.util.WebFrameworkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
 * 序列密码信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SerialPasswordServiceImpl implements SerialPasswordService {

    public static final String entered = PasswordInputFlagEnum.entered.getCode();
    public static final String notEntered = PasswordInputFlagEnum.not_entered.getCode();

    @Resource
    private SerialPasswordMapper serialPasswordMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean modifyPasswordStatus(SerialPasswordUpdateReqVO reqVO) {
        //查询序列密码信息
        SerialPasswordDO firstSerialPassword = serialPasswordMapper.getSerialPassword(SerialPasswordEnum.create_recommendation_log.getCode(), SerialPasswordSortIdEnum.first_password.getCode());
        SerialPasswordDO secondSerialPassword = serialPasswordMapper.getSerialPassword(SerialPasswordEnum.create_recommendation_log.getCode(), SerialPasswordSortIdEnum.second_password.getCode());

        if (ObjectUtil.isNull(firstSerialPassword) && ObjectUtil.isNull(secondSerialPassword)) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.DB_GET_FAILED);
        }

        Long loginUserId = WebFrameworkUtils.getLoginUserId();

        //判断是输入第一个密码，用户ID一致，密码一致,修改第一个密码的输入状态
        if (ObjectUtil.equal(firstSerialPassword.getUserId(), loginUserId)) {
            if (ObjectUtil.equal(firstSerialPassword.getSerialPassword(), reqVO.getSerialPassword())) {
                serialPasswordMapper.updateById(new SerialPasswordDO().setId(firstSerialPassword.getId()).setInputFlag(entered).setUpdateTime(LocalDateTime.now()));
                serialPasswordMapper.updateById(new SerialPasswordDO().setId(secondSerialPassword.getId()).setInputFlag(notEntered).setUpdateTime(LocalDateTime.now()));
                return true;
            }
        } else {
            //判断第二密码输入间隔时间，用户ID一致，密码一致,修改第二个密码的输入状态
            if (ObjectUtil.equal(secondSerialPassword.getUserId(), loginUserId)
                    && ObjectUtil.equal(firstSerialPassword.getInputFlag(), entered)
                    && judgeInterval(firstSerialPassword)
                    && ObjectUtil.equal(secondSerialPassword.getSerialPassword(), reqVO.getSerialPassword())) {
                serialPasswordMapper.updateById(new SerialPasswordDO().setId(secondSerialPassword.getId()).setInputFlag(entered).setUpdateTime(LocalDateTime.now()));
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean judgePasswordEffective() {
        //获取第二密码信息
        SerialPasswordDO serialPassword = serialPasswordMapper.getSerialPassword(SerialPasswordEnum.create_recommendation_log.getCode(), SerialPasswordSortIdEnum.second_password.getCode());
        if (ObjectUtil.isNull(serialPassword)) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.DB_GET_FAILED);

        }
        // 判断有效日期 是否输入密码
        if (judgeEffective(serialPassword)
                && ObjectUtil.equal(serialPassword.getInputFlag(), entered)
        ) {
            return true;
        }
        if (!judgeEffective(serialPassword)
                && ObjectUtil.equal(serialPassword.getInputFlag(), entered)
        ) {
            serialPasswordMapper.updateById(serialPassword.setInputFlag(notEntered).setUpdateTime(LocalDateTime.now()));
        }
        return false;
    }

    private Boolean judgeInterval(SerialPasswordDO firstPassword) {
        LocalDateTime updateTime = firstPassword.getUpdateTime();
        LocalDateTime intervalTime = LocalDateTime.now().minusMinutes(firstPassword.getIntervalTime());
        return updateTime.isAfter(intervalTime);
    }

    private Boolean judgeEffective(SerialPasswordDO second) {
        LocalDateTime updateTime = second.getUpdateTime();
        LocalDateTime effectTime = LocalDateTime.now().minusMinutes(second.getEffectiveTime());
        return updateTime.isAfter(effectTime);
    }


}

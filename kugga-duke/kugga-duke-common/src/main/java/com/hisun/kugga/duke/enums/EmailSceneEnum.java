package com.hisun.kugga.duke.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hisun.kugga.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 邮箱验证码发送场景的枚举
 *
 * @author Lin
 */
@Getter
@AllArgsConstructor
public enum EmailSceneEnum implements IntArrayValuable {

    /**
     * 用户注册
     */
    USER_REGISTER(001, "register", "user:register:", "用户注册"),
    /**
     * 密码重置
     */
    USER_RESET_PASSWORD(002, "reset", "user:resetPwd:", "用户密码重置"),


    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(EmailSceneEnum::getCode).toArray();

    /**
     * 场景编号
     */
    private final Integer code;
    /**
     * 验证场景
     */
    private final String scene;
    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 描述
     */
    private final String description;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static EmailSceneEnum getCodeByScene(Integer scene) {
        return ArrayUtil.firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene),
                values());
    }

}

package com.hisun.kugga.framework.common.exception;

import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

/**
 * 错误码对象
 * <p>
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 * <p>
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    /**
     * 中文提示  仅做提示作用
     */
    private String tip;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    /**
     * 中文提示构造
     *
     * @param code
     * @param message
     * @param tip
     */
    public ErrorCode(Integer code, String message, String tip) {
        this.code = code;
        this.msg = message;
        this.tip = tip;
    }

}

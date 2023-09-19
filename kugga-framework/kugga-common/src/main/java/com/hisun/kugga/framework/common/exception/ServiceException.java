package com.hisun.kugga.framework.common.exception;

import com.hisun.kugga.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务逻辑异常 Exception
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException {

    /**
     * 业务错误码
     *
     * @see ServiceErrorCodeRange
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    /*
     * 判断是否需要格式化参数，带参数的报错
     */
    private boolean isFormat = false;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    /**
     * 传参构造方法
     */
    public ServiceException(ErrorCode errorCode, String... params) {
        this.isFormat = true;
        this.code = errorCode.getCode();
        this.message = String.format(errorCode.getMsg(), (Object[]) params);
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public static void throwServiceException(ErrorCode errorCode) {
        throw new ServiceException(errorCode);
    }

}

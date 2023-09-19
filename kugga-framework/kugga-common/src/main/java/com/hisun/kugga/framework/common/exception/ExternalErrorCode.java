package com.hisun.kugga.framework.common.exception;

/**
 * @author: zhou_xiong
 */
public class ExternalErrorCode extends ErrorCode {
    private String externalCode;


    public ExternalErrorCode(Integer code, String message) {
        super(code, message);
    }

    public ExternalErrorCode(String externalCode, Integer code, String message, String tip) {
        super(code, message, tip);
        this.externalCode = externalCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }
}

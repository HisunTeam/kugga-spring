package com.hisun.kugga.duke.batch.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Task log status enumeration
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum JobLogStatusEnum {

    RUNNING(0), // running
    SUCCESS(1), // success
    FAILURE(2); // failed

    /**
     * status
     */
    private final Integer status;

}

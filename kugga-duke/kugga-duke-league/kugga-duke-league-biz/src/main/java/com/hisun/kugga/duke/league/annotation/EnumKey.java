package com.hisun.kugga.duke.league.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-11 11:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {FIELD, METHOD})
public @interface EnumKey {
}

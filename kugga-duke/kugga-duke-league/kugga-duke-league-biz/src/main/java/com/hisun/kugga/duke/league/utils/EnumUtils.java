package com.hisun.kugga.duke.league.utils;

import com.hisun.kugga.duke.league.annotation.EnumKey;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-11 11:05
 * @Version: 1.0  增强版（做了缓存，懒加载）  可迭代无注解版，向下兼容 或自定义key名版本
 */
public class EnumUtils {
    /**
     * 枚举类map;class为枚举的class;object为枚举实例的key(贴有注解@EnumKey);Enum<?>对应的枚举实例
     */
    private static final Map<Class<?>, Map<Object, Enum<?>>> ENUM_TYPE_TO_ENUM_VALUE_TO_ENUM_TABLE = new HashMap<>();

    /**
     * Title: valueOf Description:
     * 务必注意带有@EnumKey注解的字段类型，存什么类型参数就传什么类型！！！
     *
     * @param value     枚举的值 枚举实例key(贴有注解@EnumKey)
     * @param enumClass 枚举类
     */
    public static <E extends Enum<E>> E valueOf(Object value, Class<E> enumClass) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (Objects.isNull(enumClass)) {
            throw new IllegalArgumentException("enumClass can't be null");
        }
        // 当ENUM_TABLE没有该enumClass类型时将其添加到ENUM_TABLE中
        if (!ENUM_TYPE_TO_ENUM_VALUE_TO_ENUM_TABLE.containsKey(enumClass)) {
            addEnumClass(enumClass);
        }
        //put only,check in put
        E e = (E) ENUM_TYPE_TO_ENUM_VALUE_TO_ENUM_TABLE.get(enumClass).get(value);
        if (Objects.isNull(e)) {
            throw new IllegalArgumentException(value + " can't convert to " + enumClass);
        }
        return e;
    }

    private synchronized static <E extends Enum<E>> void addEnumClass(Class<E> enumClass) {
        //double check
        if (!ENUM_TYPE_TO_ENUM_VALUE_TO_ENUM_TABLE.containsKey(enumClass)) {
            Optional<Field> getValueFieldOptional = findEnumKeyField(enumClass);
            if (!getValueFieldOptional.isPresent()) {
                throw new IllegalArgumentException(enumClass + " must have a field with the annotation " + EnumKey.class);
            }
            getValueFieldOptional.ifPresent(field -> register(enumClass, enumConstant -> {
                try {
                    return field.get(enumConstant);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    private static <E extends Enum<E>> void register(Class<E> enumClass, Function<E, Object> getValueFunction) {
        E[] enumConstants = enumClass.getEnumConstants();
        Map<Object, Enum<?>> valueToEnumMap = new HashMap<>();
        for (E enumConstant : enumConstants) {
            Object enumConstantValue = getValueFunction.apply(enumConstant);
            valueToEnumMap.put(enumConstantValue, enumConstant);
        }
        // 将enumClass对应的map变为不可变map;
        ENUM_TYPE_TO_ENUM_VALUE_TO_ENUM_TABLE.put(enumClass, Collections.unmodifiableMap(valueToEnumMap));
    }

    private static <E extends Enum<E>> Optional<Field> findEnumKeyField(Class<E> enumClass) {
        return Arrays.stream(enumClass.getDeclaredFields())
                //过滤出有EnumKey注解的属性
                .filter(field -> Objects.nonNull(field.getAnnotation(EnumKey.class)))
                //将私有属性设为可以获取值
                .peek(field -> field.setAccessible(Boolean.TRUE)).findAny();
    }
}

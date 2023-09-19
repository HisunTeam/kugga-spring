package com.hisun.kugga.duke.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 短链接绑定 DO
 *
 * @author 芋道源码
 */
@TableName("duke_system_params")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemParamsDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 参数key
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数描述
     */
    private String description;


}

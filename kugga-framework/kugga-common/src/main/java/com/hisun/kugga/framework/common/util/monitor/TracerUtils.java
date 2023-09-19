package com.hisun.kugga.framework.common.util.monitor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.MDC;

/**
 * 链路追踪工具类
 * <p>
 * 考虑到每个 starter 都需要用到该工具类，所以放到 common 模块下的 util 包下
 *
 * @author 芋道源码
 */
public class TracerUtils {

    private final static String TRACE_ID = "tid";

    /**
     * 私有化构造方法
     */
    private TracerUtils() {
    }

    /**
     * 获得链路追踪编号，直接返回 SkyWalking 的 TraceId。
     * 如果不存在的话为空字符串！！！
     *
     * @return 链路追踪编号
     */
    public static String getTraceId() {
        String tid = TraceContext.traceId();
        if (StrUtil.isBlankIfStr(tid)) {
            String mdcTid = MDC.get(TRACE_ID);
            if (StrUtil.isBlankIfStr(mdcTid)) {
                tid = IdUtil.fastSimpleUUID();
                MDC.put(TRACE_ID, tid);
                return tid;
            }
            return mdcTid;
        }
        return tid;
    }

}

package com.hisun.kugga.duke.batch.dal.dataobject.log;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.*;

import java.util.Date;

/**
 * Execution Logs of Scheduled Tasks
 *
 * @author 芋道源码
 */
@TableName("infra_job_log")
@KeySequence("infra_job_log_seq") // Auto-increment primary key for Oracle, PostgreSQL, Kingbase, DB2, H2 databases. If using MySQL or similar databases, it is not required
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLogDO extends BaseDO {

    /**
     * Log ID
     */
    private Long id;
    /**
     * Job ID
     */
    private Long jobId;
    /**
     * Name of the handler
     */
    private String handlerName;
    /**
     * Handler parameters
     */
    private String handlerParam;
    /**
     * Execution index
     *
     * Used to differentiate between initial execution and retries. If it's a retry, the index is greater than 1.
     */
    private Integer executeIndex;

    /**
     * Start execution time
     */
    private Date beginTime;
    /**
     * End execution time
     */
    private Date endTime;
    /**
     * Duration of execution in milliseconds
     */
    private Integer duration;
    /**
     * Status
     */
    private Integer status;
    /**
     * Result data
     *
     * - When successful, it contains the result of {@link JobHandler#execute(String)}
     * - When failed, it contains the exception stack trace from {@link JobHandler#execute(String)}
     */
    private String result;

}

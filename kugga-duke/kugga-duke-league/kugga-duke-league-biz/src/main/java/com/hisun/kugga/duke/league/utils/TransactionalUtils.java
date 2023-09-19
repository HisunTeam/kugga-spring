package com.hisun.kugga.duke.league.utils;

import com.hisun.kugga.framework.utils.Functions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-09-08 15:45
 */
@Component
@AllArgsConstructor
public class TransactionalUtils {

    PlatformTransactionManager transactionManager;//JdbcTransactionManager

    /**
     * 执行事务
     *
     * @param func
     */
    public void execute(Functions func) {
        //开始事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus defStatus = transactionManager.getTransaction(def);
        try {
            //业务代码
            func.execute();
        } catch (RuntimeException e) {
            transactionManager.rollback(defStatus);
            throw e;
        }
        transactionManager.commit(defStatus);// 手动提交事务
    }
}

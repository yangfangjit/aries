package com.yangfang.aries.src;

import lombok.Data;

import java.util.Date;

/**
 * 定时任务信息承载对象
 *
 * @author 幽明
 * @serial 2018/11/16
 */
@Data
public abstract class AbstractTimerTask implements Runnable {

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 模块
     * TODO 换成枚举
     */
    private String module;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 执行时间点
     */
    private Date timestamp;

    /**
     * 定时任务的主键
     *
     * @return TimerTask主键
     */
    public String getKey() {
        return String.valueOf(businessId) + module + optType;
    }

}

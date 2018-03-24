package com.bugjc.ha.task;

/**
 * 需要具有重试功能的方法实现此接口
 * @author aoki
 * @create 2018-03-23
 */
public interface RetryStrategyService {

    /**
     * 自定义指数时间，即下一次重试执行规则
     * @param currentRetryNumber --当前已重试次数
     * @param retryCount         --最大重试次数
     * @return 0:默认当前重试次数*底数2，第一次重试等于1*2秒后执行重试
     */
    public int expTimeRule(int currentRetryNumber, int retryCount);

    /**
     * 策略运行的业务逻辑方法
     * @param obj
     * @return true:无需在重试，false:需要继续执行策略
     */
    public boolean businessLogicRun(Object obj) throws Exception;

    /**
     * 策略处理失败回调方法
     * 注意：此方法只在重试次数超过阈值还未成功得到业务逻辑方法正确的结果调用
     * @param obj
     */
    public void failureCallback(Object obj);

}

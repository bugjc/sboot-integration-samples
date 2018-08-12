package com.bugjc.tx.service;


/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
public interface OrderService{

    /**
     * 测试回滚
     */
    public void testRollback();

    /**
     * 测试循环回滚
     * @param i
     */
    public void testLoopRollback(int i);

    /**
     * 测试提交
     */
    public void testCommit();

    /**
     * 测试循环提交
     * @param i
     */
    public void testLoopCommit(int i);

}

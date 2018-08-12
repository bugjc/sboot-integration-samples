package com.bugjc.tx;

import com.bugjc.tx.service.OrderService;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class TransactionTest extends Tester{

    @Resource
    private OrderService orderService;

    @Test
    public void test() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread.sleep(5020);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/1000);
        System.out.println(9 * 0.8);
        System.out.println(new BigDecimal(7.2).compareTo(new BigDecimal(9)));
    }

    @Test
    public void testException(){
        try{
            int i = 10 / 0;
        }catch (Exception ex){
            ex = new RuntimeException(ex);
            System.out.println(ex);
        }
    }

    @Test
    public void testCommit() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        orderService.testCommit();
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
    }


}

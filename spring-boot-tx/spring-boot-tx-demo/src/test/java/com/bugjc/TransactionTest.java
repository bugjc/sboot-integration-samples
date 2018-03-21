package com.bugjc;

import org.junit.Test;

import java.math.BigDecimal;

public class TransactionTest {

    @Test
    public void test() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread.sleep(5020);
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)/1000);
        System.out.println(9 * 0.8);
        System.out.println(new BigDecimal(7.2).compareTo(new BigDecimal(9)));
    }
}

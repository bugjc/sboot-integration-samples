package com.bugjc.tx.service.impl;

import com.bugjc.ha.task.RetryStrategyService;
import com.bugjc.tx.dao.OrderMapper;
import com.bugjc.tx.dao.ProductMapper;
import com.bugjc.tx.model.Order;
import com.bugjc.tx.model.Product;
import com.bugjc.tx.service.OrderService;
import com.xiaoleilu.hutool.lang.Console;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
@Service
public class OrderServiceImpl implements OrderService,RetryStrategyService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public void testRollback() {

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setName("test rollback order");
        order.setCreateTime(new Date());
        orderMapper.insert(order);

        Product product = new Product();
        product.setName("test rollback product");
        product.setCreateTime(new Date());
        productMapper.insert(product);

        //抛出异常回滚
        int i = 10 / 0;

    }

    @Override
    public void testLoopRollback(int i) {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setName("test rollback order-"+i);
        order.setCreateTime(new Date());
        orderMapper.insert(order);

        Product product = new Product();
        product.setName("test rollback product-"+i);
        product.setCreateTime(new Date());
        productMapper.insert(product);
        if (i <= 0){
            //抛出异常回滚
            i = 10 / 0;
        }
        testLoopRollback(--i);

    }

    @Override
    public void testCommit() {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setName("test commit order");
        order.setCreateTime(new Date());
        orderMapper.insert(order);

        Product product = new Product();
        product.setName("test commit product");
        product.setCreateTime(new Date());
        productMapper.insert(product);
    }

    @Override
    public void testLoopCommit(int i) {
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setName("test commit order-"+i);
        order.setCreateTime(new Date());
        orderMapper.insert(order);

        Product product = new Product();
        product.setName("test commit product-"+i);
        product.setCreateTime(new Date());
        productMapper.insert(product);
        if (i <= 0){
            return;
        }
        testLoopCommit(--i);
    }

    @Override
    public int expTimeRule(int currentRetryNumber, int retryCount) {
        return 0;
    }

    @Override
    public boolean businessLogicRun(Object obj) throws Exception {
        Map<String,Object> objectMap = (Map<String, Object>) obj;
        String type = (String) objectMap.get("type");
        if (type.equals("testCommit")){
            this.testCommit();

        }else if (type.equals("testLoopCommit")){
            int param = (int) objectMap.get("loopCount");
            this.testLoopCommit(param);

        }else if (type.equals("testRollback")){
            this.testRollback();

        }else if (type.equals("testLoopRollback")){
            int param = (int) objectMap.get("loopCount");
            this.testLoopRollback(param);

        }

        return true;
    }

    @Override
    public void failureCallback(Object obj) {
        Console.error(obj);
    }
}

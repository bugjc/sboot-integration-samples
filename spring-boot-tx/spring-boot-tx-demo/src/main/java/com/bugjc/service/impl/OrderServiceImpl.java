package com.bugjc.service.impl;

import com.bugjc.dao.OrderMapper;
import com.bugjc.dao.ProductMapper;
import com.bugjc.model.Order;
import com.bugjc.model.Product;
import com.bugjc.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
@Service
public class OrderServiceImpl implements OrderService {
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
        if (i == 2){
            //抛出异常回滚
            i = 10 / 0;
        }
        testLoopRollback(++i);

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
        if (i == 2){
            return;
        }
        testLoopCommit(++i);
    }

}

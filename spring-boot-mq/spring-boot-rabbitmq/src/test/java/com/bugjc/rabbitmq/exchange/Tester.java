package com.bugjc.rabbitmq.exchange;


import com.bugjc.RabbitMQApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public abstract class Tester {}




package com.bugjc;

import com.bugjc.configurer.TxConfigurer;
import com.bugjc.ds.DynamicDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;

public class Test01 {

    private String name;

    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private TxConfigurer txConfigurer;

    @Test
    public void test2() throws IOException {
        System.out.println(txConfigurer.getMethod().get(0));
        System.out.println(txConfigurer.getServicePointcut());

    }

    @Test
    public void test1(){
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DynamicDataSource.class);
        beanFactory.registerBeanDefinition("test01", beanDefinitionBuilder.getBeanDefinition());

//        AnnotatedGenericBeanDefinition beanDefinition=new AnnotatedGenericBeanDefinition(Test01.class);
//        System.out.println(beanDefinition.getMetadata().getAnnotationTypes());
//        System.out.println(beanDefinition.isSingleton());
//        System.out.println(beanDefinition.getBeanClassName());

        DynamicDataSource test01 = applicationContext.getBean("test01",DynamicDataSource.class);
        System.out.println();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

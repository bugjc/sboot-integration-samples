package com.bugjc.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源注册
 * @author : aoki
 */
@Configuration
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    /**
     * 如配置文件中未指定数据源类型，使用该默认值
     */
    private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";

    /**
     * 数据源
     */
    private DataSource defaultDataSource;
    private Map<Object, Object> targetDataSources = new HashMap<Object, Object>();



    /**
     * 加载多数据源配置
     */
    @Override
    public void setEnvironment(Environment env) {
        initDataSource(env);
    }

    /**
     * 1、初始化数据源
     * @param env
     */
    private  void initDataSource(Environment env){
        //读取数据源配置
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        String dsPrefixes = propertyResolver.getProperty("names");
        String separator = ",";
        String separatorProperty = ".";
        for (int i = 0; i < dsPrefixes.split(separator).length; i++) {
            String dsPrefix = dsPrefixes.split(separator)[i];
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + separatorProperty);
            dsMap = propertyResolver.getSubProperties(dsPrefix + separatorProperty);
            DataSource ds = buildDataSource(dsMap);
            if (i == 0){
                defaultDataSource = ds;
            }
            targetDataSources.put(dsPrefix, ds);
            dataBinder(ds, env);
        }

    }

    /**
     * 创建DataSource
     *
     * @return
     * @author SHANHY
     * @create 2016年1月24日
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder
                    .create()
                    .driverClassName(driverClassName)
                    .url(url)
                    .username(username)
                    .password(password)
                    .type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为DataSource绑定更多数据
     *
     * @param dataSource
     * @param env
     * @author SHANHY
     * @create  2016年1月25日
     */
    private void dataBinder(DataSource dataSource, Environment env){
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setIgnoreNestedProperties(false);
        dataBinder.setIgnoreInvalidFields(false);
        dataBinder.setIgnoreUnknownFields(true);
    }

    /**
     * 把所有数据库都放在路由中
     * @return
     */
    @Bean(name="roundRobinDataSouceProxy")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {

        if (targetDataSources.isEmpty()){
            throw new NullPointerException("数据源未初始化...");
        }

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource(){

            @Override
            protected Object determineCurrentLookupKey() {
                String dbType = DataBaseContextHolder.getDBType();

                if(dbType == null){
                    dbType = "tx1";
                }

                return dbType;
            }
        };

        proxy.setDefaultTargetDataSource(defaultDataSource);//默认库
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
        //beanDefinitionRegistry.registerBeanDefinition("roundRobinDataSouceProxy",);

//        GenericBeanDefinition beanDefinitionDB = new GenericBeanDefinition();
//        beanDefinition.setBeanClass(DynamicDataSource.class);
//        beanDefinition.setSynthetic(true);
//        beanDefinitionRegistry.registerBeanDefinition("roundRobinDataSouceProxy", beanDefinitionDB);

        logger.info("Dynamic DataSource Registry");
    }
}

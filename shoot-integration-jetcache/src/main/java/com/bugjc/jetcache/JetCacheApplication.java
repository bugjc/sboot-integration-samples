package com.bugjc.jetcache;



import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 * @author qingyang
 * @date 2018/8/12 07:01
 */
@EnableMethodCache(basePackages = "com.bugjc.jetcache")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class JetCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(JetCacheApplication.class, args);
    }
}

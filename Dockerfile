FROM hub.c.163.com/wuxukun/maven-aliyun:3-jdk-8
## 复制源文件到app目录下
COPY . /app/spring-boot-example/
## 构建应用
RUN cd /app/spring-boot-example && mvn clean install -DskipTests
## 拷贝编译结果到指定目录
RUN mv /app/spring-boot-example/spring-boot-tx/spring-boot-tx-demo/target/*.jar /app/app-tx.jar \
    ## 清理编译痕迹
    && cd /app && rm -rf /app/spring-boot-example

VOLUME /app/tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app-tx.jar"]
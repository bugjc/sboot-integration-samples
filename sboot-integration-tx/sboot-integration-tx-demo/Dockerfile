FROM maven:3.5-jdk-8
#registry.cn-hangzhou.aliyuncs.com/chainone/centos7-jdk8-maven3.3.9
WORKDIR /app/spring-boot-tx
COPY spring-boot-tx .
COPY pom.xml /app
## 构建应用
RUN mvn clean install -DskipTests
## 拷贝编译结果到指定目录
RUN mv /app/spring-boot-tx/spring-boot-tx-demo/target/*.jar /app/app-tx.jar \
    ## 清理编译痕迹
    && cd /app && rm -rf /app/spring-boot-tx

VOLUME /app
## 暴露容器内端口
EXPOSE 8080
## 启动服务
ENTRYPOINT ["java","-jar","/app/app-tx.jar"]
CMD ["--SERVER_PORT=8080"]
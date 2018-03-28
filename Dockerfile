FROM hub.c.163.com/wuxukun/maven-aliyun:3-jdk-8
        #构建应用
RUN mvn clean package \
        #拷贝编译结果到指定目录
        && mv spring-boot-tx/spring-boot-tx-demo/target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build

VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
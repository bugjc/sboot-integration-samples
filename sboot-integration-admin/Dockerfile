FROM maven:3.5.2-alpine as builder
MAINTAINER aoki <qing.muyi@foxmail.com>
WORKDIR /app/sboot-integration-admin
COPY sboot-integration-admin .
COPY pom.xml /app
# 编译打包 (jar包生成路径：/app/target)
RUN mvn package -Dmaven.test.skip=true


FROM openjdk:8-jre-alpine
MAINTAINER aoki <qing.muyi@foxmail.com>
WORKDIR /app
COPY --from=builder /app/sboot-integration-admin/target/sboot-integration-admin-1.0.jar .
VOLUME /app
## 暴露容器内端口
EXPOSE 8081
## 启动服务
ENTRYPOINT ["java","-jar","/app/spring-boot-admin-1.0.jar"]
CMD ["--SERVER_PORT=8081"]
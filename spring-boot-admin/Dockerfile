FROM maven:3.5.2-alpine as builder
MAINTAINER aoki <qing.muyi@foxmail.com>
WORKDIR /app/spring-boot-admin
COPY spring-boot-admin .
COPY pom.xml /app
RUN ls -ls && pwd
# 编译打包 (jar包生成路径：/app/target)
RUN mvn package -Dmaven.test.skip=true


FROM openjdk:8-jre-alpine
MAINTAINER aoki <qing.muyi@foxmail.com>
WORKDIR /app
COPY --from=builder /app/spring-boot-admin/target/spring-boot-admin-1.0.jar .
## TODO 磁盘挂载
VOLUME /app/tmp
## 暴露容器内端口
EXPOSE 8081
## 启动服务
ENTRYPOINT ["java","-jar","/app/spring-boot-admin-1.0.jar"]
CMD ["--SERVER_PORT=8081"]
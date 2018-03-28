FROM hub.c.163.com/wuxukun/maven-aliyun:3-jdk-8
## 复制源文件到app目录下
COPY . /app/
## 进入源文件
RUN cd /app
## 构建应用
RUN mvn clean package
## 重命名执行文件
RUN mv spring-boot-tx/spring-boot-tx-demo/target/*.jar /app/app.jar

VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
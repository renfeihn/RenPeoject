# Docker image for springboot application
# VERSION 0.0.1
# Author: bolingcavalry

### 基础镜像
FROM java:8

#作者
MAINTAINER renfei <renfeihn@gmail.com>

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/docker-test-1.0.0-SNAPSHOT.jar app.jar

#启动容器时的进程
ENTRYPOINT ["java","-jar","/app.jar"]

#暴露8080端口
EXPOSE 8001
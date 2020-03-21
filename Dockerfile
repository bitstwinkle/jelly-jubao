FROM centos:7
MAINTAINER 核心系统 BITSTWINKLE 研发团队 <bitstwinkle@yeah.net>

# 部署应用版本
VOLUME /tmp
ARG APP_JAR_FILE=target/z-startup.jar
COPY ${APP_JAR_FILE} /home/admin/app/app.jar

# 安装打包必备软件
RUN yum -y install wget unzip telnet

# 准备 JDK/Tomcat 系统变量
ENV JAVA_HOME /usr/java/latest
ENV PATH $PATH:$JAVA_HOME/bin
ENV ADMIN_HOME /home/admin

# 下载安装 JDK 8
RUN wget http://edas-hz.oss-cn-hangzhou.aliyuncs.com/agent/prod/files/jdk-8u191-linux-x64.rpm -O /tmp/jdk-8u191-linux-x64.rpm && \
  yum -y install /tmp/jdk-8u191-linux-x64.rpm && \
  rm -rf /tmp/jdk-8u191-linux-x64.rpm

# 增加容器内中⽂支持
ENV LANG="en_US.UTF-8"

# 增强 Webshell 使⽤体验
ENV TERM=xterm

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone


# 将启动命令写入启动脚本 start.sh
RUN mkdir -p /home/admin
RUN mkdir -p /home/admin/logs
RUN mkdir -p /home/admin/logs/elecwatt
RUN echo '$JAVA_HOME/bin/java -jar $CATALINA_OPTS /home/admin/app/app.jar'> /home/admin/start.sh && chmod +x /home/admin/start.sh
WORKDIR $ADMIN_HOME
CMD ["/bin/bash", "/home/admin/start.sh"]

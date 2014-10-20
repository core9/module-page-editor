FROM ubuntu:latest

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN apt-get update
RUN apt-get install software-properties-common -y
RUN apt-add-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get install oracle-java8-installer -y

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle


RUN \
  apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10 && \
  echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' > /etc/apt/sources.list.d/mongodb.list && \
  apt-get update && \
  apt-get install -y mongodb-org && \
  rm -rf /var/lib/apt/lists/*

# Define mountable directories.
VOLUME ["/data/db"]

# Define working directory.
WORKDIR /data



RUN apt-get update && apt-get install -y openssh-server supervisor
RUN mkdir /var/run/sshd

RUN groupadd webedit && useradd webedit -s /bin/bash -m -g webedit -G webedit && adduser webedit sudo
RUN echo 'webedit:webedit' |chpasswd
RUN echo 'root:screencast' |chpasswd

ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

RUN rm -rf /core9 && mkdir -p /core9
ADD ./build/install/module-page-editor /core9


expose  27017 22
cmd     ["/usr/bin/supervisord", "-n"]
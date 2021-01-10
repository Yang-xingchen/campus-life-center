# campus-life-center
基于spring cloud的校园一站式服务中心，便捷提供校园所需的服务。

该项目为个人学习SOA相关知识所用，不建议实际使用，若使用，后果自负。

## 环境
java
> openjdk version "14" 2020-03-17
>
> OpenJDK Runtime Environment (build 14+36-1461)
>
> OpenJDK 64-Bit Server VM (build 14+36-1461, mixed mode, sharing)

mysql
> Ver 8.0.17 for Linux on x86_64 (MySQL Community Server - GPL)

nacos
> 1.3.2

rabbitmq
> 3.7-management

npm
> 7.0.11


## 目录/模块

- config
nacos配置参考

- web
web界面

- user-center `port:10000`
用户中心，管理用户信息。

- notice `port:10100`
通知管理

- todo `port:10200`
待办管理

- info `port:10300`
信息管理

- ...

## 技术栈

### 后端

1. spring cloud alibaba

1. spring boot

1. mybatis

1. redis

1. nacos

1. swagger2

### 前端

1. vue

1. npm

1. less

# campus-life-center
基于spring cloud的校园一站式服务中心，便捷提供校园所需的服务。

该项目为个人学习SOA相关知识所用，不建议实际使用，若使用，后果自负。

---
当前为第一阶段：通知管理系统。

## 环境
java
> openjdk version "11"

mysql
> Ver 8.0.17 for Linux on x86_64 (MySQL Community Server - GPL)

nacos
> 1.3.2

rabbitmq
> 3.7-management

npm
> 7.0.11


## 目录/模块

- docs
文档, 详情见[README.md](./docs/README.md)。

- web
web界面, 详情见[README.md](./web/README.md)。

- common
通用模块，无启动程序，为其他项目提供便捷方法。
  
- mbg
mybatis逆向工程模块，自动创建数据库实体类及mapper函数。

- gateway
网关。

- user-center
用户中心。

- notice
通知管理。

- todo
待办管理。

- info
信息管理。
  
- comment
评论模块。

- ...

## 技术栈

### 后端

1. spring cloud alibaba

1. spring boot

1. mybatis

1. nacos

1. redis

1. rabbitmq

1. zipkin

1. swagger2

1. ...

### 前端

1. vue

1. npm

1. less

1. antd

1. ...
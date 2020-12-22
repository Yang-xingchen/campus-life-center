# 用户中心

用户中心，管理用户信息。

## 数据库表

### SQL

[表结构](./src/main/resources/schema.sql)
[初始数据](./src/main/resources/data.sql)

## 说明

### 结构说明
account: 账户
> 密码字段和安全密码字段使用BCryptPasswordEncoder加密
>
> 初始存在系统管理员，除正常权限外可对系统进行控制

organization: 组织
> 账户可加入组织，通过组织内的角色获得对组织的权限
>
> 组织由账户创建，创建者拥有该组织的一切权限
>
> 初始存在管理员组织，由系统管理员创建

permission: 权限
> 由string的值组成

signInLog: 登录记录
> 记录登录情况

securityLog: 安全记录
> 记录开启安全模式情况


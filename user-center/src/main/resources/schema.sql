-- clc.account definition

CREATE TABLE `account` (
  `id` varchar(32) NOT NULL COMMENT '登录id',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `gender` int(2) NOT NULL DEFAULT '2' COMMENT '性别',
  `create_data` datetime DEFAULT NULL COMMENT '创建日期',
  `security_key` varchar(256) DEFAULT NULL COMMENT '安全密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.organization definition

CREATE TABLE `organization` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent` int(10) unsigned DEFAULT NULL COMMENT '父id',
  `hide` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否隐藏',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `creator` varchar(32) NOT NULL COMMENT '创建者',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `visibility` int(16) DEFAULT NULL COMMENT '可见性',
  `create_data` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


-- clc.`role` definition

CREATE TABLE `role` (
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(256) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.permission definition

CREATE TABLE `permission` (
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(16) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;


-- clc.role_permission definition

CREATE TABLE `role_permission` (
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `rid` int(16) unsigned NOT NULL COMMENT '角色id',
  `pid` int(16) unsigned NOT NULL COMMENT '权限id',
  PRIMARY KEY (`oid`,`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_organization definition

CREATE TABLE `account_organization` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `hide` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否隐藏',
  `account_accept` bit(1) NOT NULL DEFAULT b'0' COMMENT '账户是否同意',
  `organization_accept` bit(1) NOT NULL DEFAULT b'0' COMMENT '组织是否同意',
  PRIMARY KEY (`aid`,`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.sign_in_log definition

CREATE TABLE `sign_in_log` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `sign_in_time` datetime NOT NULL COMMENT '登录时间',
  `sign_out_time` datetime DEFAULT NULL COMMENT '退出时间',
  `ip` varchar(16) DEFAULT NULL COMMENT '登录ip',
  `source` int(11) DEFAULT NULL COMMENT '登录途径: 0,帐号密码;1.已登录token',
  `type` int(11) DEFAULT NULL COMMENT '退出登录类型',
  `token` varchar(64) DEFAULT NULL COMMENT '登录时token',
  PRIMARY KEY (`aid`,`sign_in_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.security_log definition

CREATE TABLE `security_log` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  PRIMARY KEY (`aid`,`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_organization_role definition

CREATE TABLE `account_organization_role` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `id` int(16) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`aid`,`oid`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
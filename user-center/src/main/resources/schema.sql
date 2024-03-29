-- clc.account definition

CREATE TABLE `account` (
  `id` varchar(32) NOT NULL COMMENT '登录id',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `gender` int(2) NOT NULL DEFAULT '2' COMMENT '性别',
  `create_data` datetime DEFAULT NULL COMMENT '创建日期',
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
  `visibility` int(10) DEFAULT NULL COMMENT '可见性',
  `create_data` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
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


-- clc.`role` definition

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(256) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_organization_role definition

CREATE TABLE `account_organization_role` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `id` int(10) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`aid`,`oid`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.permission definition

CREATE TABLE `permission` (
  `id` int(10) unsigned NOT NULL COMMENT 'id',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.role_permission definition

CREATE TABLE `role_permission` (
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `rid` int(16) unsigned NOT NULL COMMENT '角色id',
  `pid` int(16) unsigned NOT NULL COMMENT '权限id',
  PRIMARY KEY (`oid`,`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.sign_in_log definition

CREATE TABLE `sign_in_log` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `sign_in_time` datetime NOT NULL COMMENT '登录时间',
  `sign_out_time` datetime DEFAULT NULL COMMENT '退出时间',
  `ip` varchar(16) DEFAULT NULL COMMENT '登录ip',
  `source` int(11) DEFAULT NULL COMMENT '登录途径',
  `type` int(11) DEFAULT NULL COMMENT '退出登录类型',
  `token` varchar(64) DEFAULT NULL COMMENT '登录时token',
  PRIMARY KEY (`aid`,`sign_in_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.condition_account definition

CREATE TABLE `condition_account` (
  `ref` varchar(64) NOT NULL COMMENT '引用',
  `aid` varchar(32) NOT NULL COMMENT '成员',
  PRIMARY KEY (`ref`,`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

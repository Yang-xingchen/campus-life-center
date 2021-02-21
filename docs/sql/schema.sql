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
  `id` int(16) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(256) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


-- clc.account_organization_role definition

CREATE TABLE `account_organization_role` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  `id` int(16) unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`aid`,`oid`,`id`)
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


-- clc.security_log definition

CREATE TABLE `security_log` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  PRIMARY KEY (`aid`,`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- clc.notice definition

CREATE TABLE `notice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `creator` varchar(32) NOT NULL COMMENT '创建者',
  `organization` int(10) unsigned DEFAULT NULL COMMENT '发布组织id',
  `publish_status` int(8) DEFAULT '1' COMMENT '状态: 0.删除; 1,创建; 2.待审核; 3.发布中; 4.发布完成',
  `visibility` int(4) DEFAULT '0' COMMENT '可见性: 0,公开; 1,仅通知成员; 2,仅自己',
  `importance` int(4) unsigned NOT NULL DEFAULT '3' COMMENT '重要程度: 0,最低; 5,最高',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本，更新时自增',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '正文内容',
  `content_type` int(4) NOT NULL DEFAULT '0' COMMENT '正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `public_type` int(4) NOT NULL DEFAULT '0' COMMENT '通知类型: 0,消息; 1,事件; 2,活动',
  `start_time` datetime DEFAULT NULL COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` datetime DEFAULT NULL COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  `ref` varchar(64) NOT NULL COMMENT '引用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


-- clc.notice_tag definition

CREATE TABLE `notice_tag` (
  `nid` bigint(20) unsigned NOT NULL,
  `tag` varchar(32) NOT NULL COMMENT '标签内容',
  PRIMARY KEY (`nid`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.notice_info definition

CREATE TABLE `notice_info` (
  `nid` bigint(20) unsigned NOT NULL COMMENT '通知id',
  `ref` varchar(64) NOT NULL COMMENT '引用',
  PRIMARY KEY (`nid`,`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.notice_update_log definition

CREATE TABLE `notice_update_log` (
  `id` bigint(20) unsigned NOT NULL COMMENT '通知id',
  `version` int(10) unsigned NOT NULL COMMENT '版本',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  `public_type` int(4) NOT NULL DEFAULT '0' COMMENT '通知类型: 0,消息; 1,事件; 2,活动',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '正文内容',
  `content_type` int(4) NOT NULL DEFAULT '0' COMMENT '正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML',
  `importance` int(11) NOT NULL DEFAULT '3' COMMENT '重要程度: 0,最低; 5,最高',
  `start_time` datetime DEFAULT NULL COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` datetime DEFAULT NULL COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  PRIMARY KEY (`id`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_notice definition

CREATE TABLE `account_notice` (
  `nid` bigint(20) unsigned NOT NULL COMMENT '通知id',
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `looked` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已读',
  `top` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否置顶',
  `del` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `relative_importance` int(8) NOT NULL DEFAULT '0' COMMENT '相对重要程度',
  `notice_importance` int(4) unsigned NOT NULL,
  PRIMARY KEY (`nid`,`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.notice_condition definition

CREATE TABLE `notice_condition` (
  `nid` bigint(20) unsigned NOT NULL COMMENT '通知id',
  `ref` varchar(64) NOT NULL COMMENT '条件引用',
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.condition_organization definition

CREATE TABLE `condition_organization` (
  `ref` varchar(64) NOT NULL COMMENT '引用',
  `oid` int(10) unsigned NOT NULL COMMENT '组织',
  `belong` bit(1) NOT NULL DEFAULT b'1' COMMENT '属于',
  `subscribe` bit(1) NOT NULL DEFAULT b'1' COMMENT '关注',
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_subscribe definition

CREATE TABLE `account_subscribe` (
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `oid` int(10) unsigned NOT NULL COMMENT '组织id',
  PRIMARY KEY (`aid`,`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- clc.todo definition

CREATE TABLE `todo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'todo id',
  `content` varchar(64) NOT NULL COMMENT '内容',
  `ref` varchar(64) NOT NULL,
  `type` int(8) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;


-- clc.account_todo definition

CREATE TABLE `account_todo` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'todo id',
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `finish` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否完成',
  `top` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否置顶',
  `add_list` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否加入列表',
  PRIMARY KEY (`id`,`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.condition_todo definition

CREATE TABLE `condition_todo` (
  `ref` varchar(64) NOT NULL COMMENT '引用',
  `tid` bigint(20) unsigned NOT NULL COMMENT 'todo id',
  `finish` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否完成',
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- clc.info definition

CREATE TABLE `info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `hide` bit(1) DEFAULT b'0' COMMENT '是否隐藏',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT '类型: 0.文本; 1.组合; 2.单选/多选; 3.文件',
  `multiple` bit(1) NOT NULL DEFAULT b'0' COMMENT '允许多个',
  `default_visibility` int(4) NOT NULL DEFAULT '0' COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


-- clc.info_text definition

CREATE TABLE `info_text` (
  `id` bigint(20) unsigned NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型: 0.文本; 1.数字; 2.正则',
  `regular` varchar(128) DEFAULT NULL,
  `sample` varchar(64) NOT NULL DEFAULT '' COMMENT '示例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.info_composite definition

CREATE TABLE `info_composite` (
  `id` bigint(20) unsigned NOT NULL,
  `pid` bigint(20) unsigned NOT NULL COMMENT 'info id, type 值必须为2',
  `composite_index` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.info_radio definition

CREATE TABLE `info_radio` (
  `id` bigint(20) unsigned NOT NULL,
  `text` varchar(64) NOT NULL,
  PRIMARY KEY (`id`,`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.info_file definition

CREATE TABLE `info_file` (
  `id` bigint(20) unsigned NOT NULL,
  `path` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.ref_info_root definition

CREATE TABLE `ref_info_root` (
  `ref` varchar(64) NOT NULL COMMENT '引用，区分不同提交',
  `root` bigint(20) NOT NULL COMMENT '根信息id，区分不同来源',
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_submit definition

CREATE TABLE `account_submit` (
  `root` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '根信息id, 区分不同来源',
  `id` bigint(20) unsigned NOT NULL,
  `aid` varchar(32) NOT NULL,
  `multiple_index` int(16) NOT NULL DEFAULT '0',
  `content` varchar(512) DEFAULT NULL COMMENT '提交的内容',
  PRIMARY KEY (`root`,`id`,`aid`,`multiple_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_save_info definition

CREATE TABLE `account_save_info` (
  `aid` varchar(32) NOT NULL,
  `id` bigint(20) unsigned NOT NULL,
  `multiple_index` int(16) NOT NULL DEFAULT '0',
  `content` varchar(512) DEFAULT NULL,
  `code` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否加密',
  `visibility` int(4) NOT NULL DEFAULT '0' COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
  PRIMARY KEY (`aid`,`id`,`multiple_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.organization_save_info definition

CREATE TABLE `organization_save_info` (
  `oid` int(10) unsigned NOT NULL,
  `id` bigint(20) unsigned NOT NULL,
  `multiple_index` int(16) NOT NULL DEFAULT '0',
  `content` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`oid`,`id`,`multiple_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.condition_info definition

CREATE TABLE `condition_info` (
  `ref` varchar(64) NOT NULL COMMENT '引用',
  `iid` bigint(20) unsigned NOT NULL COMMENT '信息 id',
  `text` varchar(32) NOT NULL COMMENT '值',
  `type` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '类型:0x00,通用;0x10数字',
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- clc.comment definition

CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent` bigint(20) unsigned DEFAULT NULL,
  `aid` varchar(32) NOT NULL,
  `publish_time` datetime NOT NULL,
  `content` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


-- clc.ref_comment definition

CREATE TABLE `ref_comment` (
  `id` bigint(20) unsigned NOT NULL,
  `ref` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ref` (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


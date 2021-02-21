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
  `type` int(8) unsigned NOT NULL COMMENT '来源: 0,成员; 1, 组织; 2,待办; 3,信息',
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
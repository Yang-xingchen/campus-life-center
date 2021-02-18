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


-- clc.info_file definition

CREATE TABLE `info_file` (
  `id` bigint(20) unsigned NOT NULL,
  `path` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.info_radio definition

CREATE TABLE `info_radio` (
  `id` bigint(20) unsigned NOT NULL,
  `text` varchar(64) NOT NULL,
  PRIMARY KEY (`id`,`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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


-- clc.account_submit definition

CREATE TABLE `account_submit` (
  `root` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '根信息id, 区分不同来源',
  `id` bigint(20) unsigned NOT NULL,
  `aid` varchar(32) NOT NULL,
  `multiple_index` int(16) NOT NULL DEFAULT '0',
  `content` varchar(512) DEFAULT NULL COMMENT '提交的内容',
  PRIMARY KEY (`root`,`id`,`aid`,`multiple_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.ref_info_root definition

CREATE TABLE `ref_info_root` (
  `ref` varchar(64) NOT NULL COMMENT '引用，区分不同提交',
  `root` bigint(20) NOT NULL COMMENT '根信息id，区分不同来源',
  PRIMARY KEY (`ref`)
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
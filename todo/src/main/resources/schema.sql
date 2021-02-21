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
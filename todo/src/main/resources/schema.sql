-- clc.todo definition

CREATE TABLE `todo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'todo id',
  `content` varchar(64) NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;


-- clc.ref_todo definition

CREATE TABLE `ref_todo` (
  `ref` varchar(64) NOT NULL COMMENT '引用',
  `id` bigint(20) unsigned NOT NULL COMMENT 'todo id',
  `type` int(8) unsigned NOT NULL DEFAULT '0' COMMENT '来源类型: 0.用户; 1.通知',
  PRIMARY KEY (`ref`, `id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.account_todo definition

CREATE TABLE `account_todo` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'todo id',
  `aid` varchar(32) NOT NULL COMMENT '账户id',
  `finish` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否完成',
  `top` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否置顶',
  `add_list` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否加入列表',
  PRIMARY KEY (`id`,`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
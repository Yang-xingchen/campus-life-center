CREATE TABLE notice(
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
  `organization` INT UNSIGNED COMMENT '组织id',
  `visibility` BIT(1) DEFAULT 0 COMMENT '可见性: 0,公开; 1,私有',
  `importance` INT(4) NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `public_type` INT NOT NULL DEFAULT 0 COMMENT '通知类型: 0,消息; 1,事件; 2.活动',
  `version` INT NOT NULL DEFAULT 1 COMMENT '版本，更新时自增',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `content_type` INT(4) NOT NULL DEFAULT 0 COMMENT '正文文本格式类型: 0,纯文本; (待添加)',
  `create_time` DATETIME COMMENT '创建日期',
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  `todo_ref` VARCHAR(64) COMMENT 'todo 引用',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 信息收集
CREATE TABLE notice_info(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `name` VARCHAR(64) COMMENT '收集名',
  `ref` VARCHAR(64) COMMENT '引用',
  PRIMARY KEY (`nid`, `ref`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- TAG
CREATE TABLE notice_tag(
  `nid` BIGINT UNSIGNED NOT NULL,
  `tag` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`nid`, `tag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 组织关注列表
CREATE TABLE account_subscribe(
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `oid` INT UNSIGNED COMMENT '组织id',
  PRIMARY KEY (`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户接收通知操作
CREATE TABLE account_notice(
   `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
   `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
   `looked` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
   `top` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
   `del` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   `relative_importance` INT(8) NOT NULL DEFAULT 0 COMMENT '相对重要程度',
   PRIMARY KEY (`nid`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 更新日记
CREATE TABLE notice_update_log(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `version` INT UNSIGNED COMMENT '版本',
  `update_time` DATETIME NOT NULL COMMENT '更新日期',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `importance` INT NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `notice_time` DATETIME COMMENT '通知日期',
  PRIMARY KEY (`nid`, `version`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通知接收账户条件
CREATE TABLE publish_organization(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `oid` INT UNSIGNED NOT NULL COMMENT '组织id',
  `dynamic` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否动态',
  `belong` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否从属于',
  `subscribe` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否关注',
  PRIMARY KEY (`nid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE publish_todo(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `tid` INT UNSIGNED NOT NULL COMMENT 'todo id',
  `dynamic` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否动态',
  `finish` BIT(1) NOT NULL DEFAULT 1 COMMENT '是否完成',
  PRIMARY KEY (`nid`, `tid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE publish_info(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知 id',
  `ref` BIGINT UNSIGNED NOT NULL COMMENT '信息引用',
  `iid` BIGINT UNSIGNED NOT NULL COMMENT '信息 id',
  `dynamic` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否动态',
  `type` INT(8) COMMENT '类型',
  `type_value` VARCHAR(32) COMMENT '值',
  PRIMARY KEY (`nid`, `ref`, `iid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

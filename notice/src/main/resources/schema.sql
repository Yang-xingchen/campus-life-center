CREATE TABLE notice(
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
  `organization` INT UNSIGNED COMMENT '组织id',
  `visibility` BIT(1) DEFAULT 0 COMMENT '可见性: 0,公开; 1,私有',
  `importance` INT(4) NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `public_type` INT NOT NULL DEFAULT 0 COMMENT '通知类型: 0,消息; 1,事件; 2.活动',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `create_time` DATETIME COMMENT '创建日期',
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- TAG
CREATE TABLE notice_tag(
  `nid` BIGINT UNSIGNED NOT NULL,
  `tag` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`nid`, `tag`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通知列表
CREATE TABLE account_subscribe(
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `oid` INT UNSIGNED COMMENT '组织id',
  PRIMARY KEY (`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -- 用户接收的通知
CREATE TABLE account_notice(
   `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
   `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
   `is_read` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
   `is_top` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
   `is_delete` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   `relative_importance` INT(8) NOT NULL DEFAULT 0 COMMENT '相对重要程度',
   PRIMARY KEY (`nid`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 更新情况
CREATE TABLE notice_update_log(
  `uid` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '更新id',
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `update_time` DATETIME NOT NULL COMMENT '更新日期',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `importance` INT NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `notice_time` DATETIME COMMENT '通知日期',
  PRIMARY KEY (`uid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通知待办事项
CREATE TABLE notice_todo(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `id` INT UNSIGNED NOT NULL COMMENT 'todo id',
  `type` INT(8) NOT NULL DEFAULT 0 COMMENT '类型: 0, 简单值, 见value字段; 1, 收集信息',
  `value` VARCHAR(32) COMMENT '简单值的值或收集信息id',
  PRIMARY KEY (`id`, `nid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_notice_todo(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `id` INT UNSIGNED NOT NULL COMMENT 'todo id',
  `aid` VARCHAR(32) NOT NULL,
  `finish` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否完成',
  `is_top` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `is_add` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否加入列表',
  PRIMARY KEY (`nid`, `id`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 动态通知
CREATE TABLE dynamic_organization_observe(
  `nid` BIGINT UNSIGNED NOT NULL,
  `oid` INT UNSIGNED NOT NULL,
  `is_belong` BIT(1) NOT NULL DEFAULT 0,
  `is_subscribe` BIT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`nid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE dynamic_todo_observe(
  `nid` BIGINT UNSIGNED NOT NULL,
  `tid` INT UNSIGNED NOT NULL,
  `is_finish` BIT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`nid`, `tid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE dynamic_info_observe(
  `nid` BIGINT UNSIGNED NOT NULL,
  `tid` INT UNSIGNED NOT NULL,
  `iid` BIGINT UNSIGNED NOT NULL,
  `type` INT(8),
  `value` VARCHAR(32),
  PRIMARY KEY (`nid`, `tid`, `iid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
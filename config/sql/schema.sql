CREATE TABLE account(
    `sign_id` VARCHAR(32) UNIQUE NOT NULL COMMENT '登录id',
    `name` VARCHAR(32) NOT NULL COMMENT '名称',
    `password` VARCHAR(256) NOT NULL COMMENT '密码',
    `gender` INT(2) NOT NULL DEFAULT 2 COMMENT '性别',
    `create_data` DATETIME COMMENT '创建日期',
	`security_key` VARCHAR(256) COMMENT '安全密码',
    PRIMARY KEY (`sign_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE organization(
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    `parent` INT UNSIGNED COMMENT '父id',
    `type` VARCHAR(32) COMMENT '类型',
    `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
    `name` VARCHAR(64) NOT NULL COMMENT '名称',
    `visibility` INT(16) COMMENT '可见性',
    `create_data` DATETIME COMMENT '创建日期',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission(
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    `name` VARCHAR(16) NOT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_organization(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
    `oid` INT NOT NULL COMMENT '组织id',
    `role` INT(16) NOT NULL COMMENT '角色',
    `role_name` VARCHAR(256) NOT NULL COMMENT '角色名',
    PRIMARY KEY (`aid`, `oid`, `role`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permission(
	`oid` INT NOT NULL COMMENT '组织id',
    `rid` INT NOT NULL COMMENT '角色id',
    `pid` INT NOT NULL COMMENT '权限id',
    PRIMARY KEY (`oid`, `rid`, `pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sign_in_log(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
    `sign_in_id` VARCHAR(32) COMMENT '登录id',
    `sign_in_time` DATETIME NOT NULL COMMENT '登录时间',
    `sign_out_time` DATETIME COMMENT '退出时间',
    `ip` VARCHAR(16) COMMENT '登录ip',
    `source` INT COMMENT '登录途径',
	`type` INT COMMENT '退出登录类型',
	`token` VARCHAR(64) COMMENT '登录时token',
    PRIMARY KEY (`aid`, `sign_in_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE security_log(
	`aid` VARCHAR(32) NOT NULL COMMENT '账户id',
	`input_time` DATETIME NOT NULL COMMENT '进入时间',
	PRIMARY KEY (`aid`, `input_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE notice(
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
  `organization` INT UNSIGNED COMMENT '组织id',
  `visibility` BIT(1) DEFAULT 0 COMMENT '可见性: 0,公开; 1,私有',
  `importance` INT(4) NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `public_type` INT NOT NULL DEFAULT 0 COMMENT '通知类型: 0,消息; 1,事件; 2,活动',
  `version` INT NOT NULL DEFAULT 1 COMMENT '版本，更新时自增',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `content_type` INT(4) NOT NULL DEFAULT 0 COMMENT '正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML',
  `create_time` DATETIME COMMENT '创建日期',
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  `todo_ref` VARCHAR(64) COMMENT 'todo 引用',
  `file_ref` VARCHAR(64) COMMENT '文件引用路径',
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
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
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
  `iid` BIGINT UNSIGNED NOT NULL COMMENT '信息 id',
  `dynamic` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否动态',
  `text` VARCHAR(32) COMMENT '值',
  PRIMARY KEY (`nid`, `iid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE todo(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'todo id',
  `source` VARCHAR(64) NOT NULL COMMENT '来源',
  `title` VARCHAR(64) NOT NULL COMMENT '值',
  INDEX (`source`),
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_todo(
  `id` BIGINT UNSIGNED NOT NULL COMMENT 'todo id',
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `finish` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否完成',
  `top` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `add_list` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否加入列表',
  PRIMARY KEY (`id`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE info(
    `id` BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `type` INT(4) NOT NULL DEFAULT 0 COMMENT '类型: 0.文本; 1.组合; 2.单选',
    `multiple` BIT(1) NOT NULL DEFAULT 0 COMMENT '允许多个',
    `persistent_source` VARCHAR(64) COMMENT '非持久化来源',
    `default_visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_text(
    `id` BIGINT UNSIGNED NOT NULL,
    `sample` VARCHAR(64),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_composite(
    `id` BIGINT UNSIGNED NOT NULL,
    `pid` BIGINT UNSIGNED NOT NULL COMMENT 'info id, type 值必须为2',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_radio(
    `id` BIGINT NOT NULL,
    `text` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`id`, `text`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 收集
CREATE TABLE info_list(
    `source` VARCHAR(64) NOT NULL,
    `id` BIGINT NOT NULL,
    `list_order` INT(16),
    PRIMARY KEY(`source`, `id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_account_list(
    `source` VARCHAR(64) NOT NULL,
    `id` BIGINT NOT NULL,
    `aid` VARCHAR(32) NOT NULL,
    `index` INT(16) DEFAULT 0,
    `text` VARCHAR(512),
    PRIMARY KEY (`source`, `id`, `aid`, `index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户信息
CREATE TABLE account_info(
    `aid` VARCHAR(32) NOT NULL,
    `id` BIGINT NOT NULL,
    `index` INT(16) DEFAULT 0,
    `text` VARCHAR(512),
    `code` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否加密',
    `visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY(`aid`, `id`, `index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

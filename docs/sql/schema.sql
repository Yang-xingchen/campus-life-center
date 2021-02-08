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
    `hide` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏',
    `type` VARCHAR(32) COMMENT '类型',
    `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
    `name` VARCHAR(64) NOT NULL COMMENT '名称',
    `visibility` INT(16) COMMENT '可见性',
    `create_data` DATETIME COMMENT '创建日期',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission(
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
    `type` INT(8) UNSIGNED COMMENT '类型: 0, 系统管理; 1, 组织管理; 2. 通知管理',
    `name` VARCHAR(16) NOT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_organization(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
    `oid` INT NOT NULL COMMENT '组织id',
    `hide` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏',
    `account_accept` BIT(1) NOT NULL DEFAULT 0 COMMENT '账户是否同意',
    `organization_accept` BIT(1) NOT NULL DEFAULT 0 COMMENT '组织是否同意',
    PRIMARY KEY(`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role(
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
  `publish_status` INT(8) DEFAULT 1 COMMENT '状态: 0.删除; 1,创建; 2.待审核; 3.发布中; 4.发布完成',
  `visibility` INT(4) DEFAULT 0 COMMENT '可见性: 0,公开; 1,仅通知成员; 2,仅自己',
  `importance` INT(4) NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `version` INT NOT NULL DEFAULT 1 COMMENT '版本，更新时自增',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `content_type` INT(4) NOT NULL DEFAULT 0 COMMENT '正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML',
  `create_time` DATETIME COMMENT '创建日期',
  `public_type` INT NOT NULL DEFAULT 0 COMMENT '通知类型: 0,消息; 1,事件; 2,活动',
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  `todo_ref` VARCHAR(64) COMMENT 'todo 引用',
  `file_ref` VARCHAR(64) NOT NULL COMMENT '文件引用路径',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 信息收集
CREATE TABLE notice_info(
  `nid` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
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
  `id` BIGINT UNSIGNED NOT NULL COMMENT '通知id',
  `version` INT UNSIGNED COMMENT '版本',
  `update_time` DATETIME NOT NULL COMMENT '更新日期',
  `public_type` INT NOT NULL DEFAULT 0 COMMENT '通知类型: 0,消息; 1,事件; 2,活动',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `content_type` INT(4) NOT NULL DEFAULT 0 COMMENT '正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML',
  `importance` INT NOT NULL DEFAULT 3 COMMENT '重要程度: 0,最低; 5,最高',
  `start_time` DATETIME COMMENT 'type==0: null; type==1: 日期; type==2: 开始日期',
  `end_time` DATETIME COMMENT 'type==0: null; type==1: null; type==2: 截止日期',
  PRIMARY KEY (`id`, `version`)
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
  `ref` VARCHAR(64) NOT NULL COMMENT '来源',
  `title` VARCHAR(64) NOT NULL COMMENT '值',
  INDEX (`ref`),
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
    `hide` BIT(1) DEFAULT 0 COMMENT '是否隐藏',
    `type` INT(4) NOT NULL DEFAULT 0 COMMENT '类型: 0.文本; 1.组合; 2.单选/多选; 3.文件',
    `multiple` BIT(1) NOT NULL DEFAULT 0 COMMENT '允许多个',
    `default_visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_text(
    `id` BIGINT UNSIGNED NOT NULL,
    `type` INT NOT NULL DEFAULT 0 COMMENT '类型: 0.文本; 1.数字; 2.正则',
    `regular` VARCHAR(128),
    `sample` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '示例',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_composite(
    `id` BIGINT UNSIGNED NOT NULL,
    `pid` BIGINT UNSIGNED NOT NULL COMMENT 'info id, type 值必须为2',
    `composite_index` INT NOT NULL COMMENT '顺序',
    PRIMARY KEY (`id`, `pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_radio(
    `id` BIGINT UNSIGNED NOT NULL,
    `text` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`id`, `text`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_file(
    `id` BIGINT UNSIGNED NOT NULL,
    `multiple_index` INT(16) NOT NULL DEFAULT 0,
    `path` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`id`, `multiple_index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 收集
CREATE TABLE account_submit(
    `root` BIGINT NOT NULL DEFAULT 0 COMMENT '根信息id, 区分不同来源',
    `id` BIGINT NOT NULL,
    `aid` VARCHAR(32) NOT NULL,
    `multiple_index` INT(16) DEFAULT 0,
    `text` VARCHAR(512) COMMENT '提交的内容',
    PRIMARY KEY (`root`, `id`, `aid`, `multiple_index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ref_root(
    `ref` VARCHAR(64) NOT NULL COMMENT '引用，区分不同提交',
    `root` BIGINT NOT NULL  COMMENT '根信息id，区分不同来源',
    PRIMARY KEY (`ref`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户信息
CREATE TABLE account_save_info(
    `aid` VARCHAR(32) NOT NULL,
    `id` BIGINT NOT NULL,
    `multiple_index` INT(16) DEFAULT 0,
    `text` VARCHAR(512),
    `code` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否加密',
    `visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY(`aid`, `id`, `multiple_index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE organization_save_info(
    `oid` INT UNSIGNED NOT NULL,
    `id` BIGINT NOT NULL,
    `multiple_index` INT(16) DEFAULT 0,
    `text` VARCHAR(512),
    PRIMARY KEY(`oid`, `id`, `multiple_index`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

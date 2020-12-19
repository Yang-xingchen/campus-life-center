CREATE TABLE notice(
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `creator` VARCHAR(32) NOT NULL COMMENT '创建者',
  `organization` INT UNSIGNED COMMENT '组织id',
  `visibility` TINYINT DEFAULT 0 COMMENT '可见性: 0:公开; 1:仅通知账户',
  `public_type` TINYINT DEFAULT 0 COMMENT '发布类型: 0:组织内成员; 1:订阅列表(见account_subscribe); 2: 静态名单列表(account_notice); 3: 动态条件(见notice_condition)',
  `create_time` DATETIME COMMENT '创建日期',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `importance` TINYINT NOT NULL DEFAULT 3 COMMENT '重要程度: 0:最低; 5:最高',
  `time` DATETIME COMMENT '通知日期',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通知列表
CREATE TABLE account_subscribe(
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `oid` INT UNSIGNED COMMENT '组织id',
  PRIMARY KEY (`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE notice_condition(
  `nid` INT UNSIGNED NOT NULL COMMENT '通知id',
  `condition_type` TINYINT NOT NULL COMMENT '条件类型: 0:二值按钮; 1:个人信息',
  `condition_key` VARCHAR(32),
  `condition_operation` TINYINT,
  `condition_value` VARCHAR(32),
  PRIMARY KEY (`nid`, `condition_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 更新情况
CREATE TABLE notice_update(
  `uid` INT UNSIGNED AUTO_INCREMENT COMMENT '更新id',
  `nid` INT UNSIGNED NOT NULL COMMENT '通知id',
  `update_time` DATETIME NOT NULL COMMENT '更新日期',
  `title` VARCHAR(64) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文内容',
  `importance` TINYINT NOT NULL DEFAULT 3 COMMENT '重要程度: 0:最低; 5:最高',
  `time` DATETIME COMMENT '通知日期',
  PRIMARY KEY (`uid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通知事项
CREATE TABLE notice_mate(
  `nid` INT UNSIGNED NOT NULL COMMENT '通知id',
  `type` TINYINT NOT NULL DEFAULT 0 COMMENT '类型: 0: 简单值, 见value字段; 1:按钮; 2: 收集信息',
  `value` VARCHAR(32) COMMENT '简单值的值',
  PRIMARY KEY (`nid`, `type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -- 二值按钮
CREATE TABLE notice_button(
  `id` INT UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `unfinished_value` VARCHAR(32) NOT NULL,
  `finish_value` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_notice_finish_button(
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `nbid` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`aid`, `nbid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -- 信息收集
CREATE TABLE notice_information(
  `nid` INT UNSIGNED NOT NULL COMMENT '通知id',
  `iid` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`nid`, `iid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户接收的通知
CREATE TABLE account_notice(
  `nid` INT UNSIGNED NOT NULL COMMENT '通知id',
  `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
  `read` TINYINT NOT NULL DEFAULT 0 COMMENT '是否已读',
  `top` TINYINT NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `delete` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
  `relative_importance` TINYINT NOT NULL DEFAULT 0 COMMENT '相对重要程度',
  PRIMARY KEY (`nid`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

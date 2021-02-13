-- --用户模块 -- --
CREATE TABLE account(
    `id` VARCHAR(32) UNIQUE NOT NULL COMMENT '登录id',
    `name` VARCHAR(32) NOT NULL COMMENT '名称',
    `password` VARCHAR(256) NOT NULL COMMENT '密码',
    `gender` INT(2) NOT NULL DEFAULT 2 COMMENT '性别',
    `create_data` DATETIME COMMENT '创建日期',
	`security_key` VARCHAR(256) COMMENT '安全密码',
    PRIMARY KEY (`id`)
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
    `id` INT(16) UNSIGNED AUTO_INCREMENT COMMENT 'id',
    `name` VARCHAR(16) NOT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_organization(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
    `oid` INT UNSIGNED NOT NULL COMMENT '组织id',
    `hide` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏',
    `account_accept` BIT(1) NOT NULL DEFAULT 0 COMMENT '账户是否同意',
    `organization_accept` BIT(1) NOT NULL DEFAULT 0 COMMENT '组织是否同意',
    PRIMARY KEY(`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
    `oid` INT UNSIGNED NOT NULL COMMENT '组织id',
    `id` INT(16) UNSIGNED NOT NULL COMMENT '角色id',
    `name` VARCHAR(256) NOT NULL COMMENT '角色名',
    PRIMARY KEY (`aid`, `oid`, `id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permission(
	`oid` INT UNSIGNED UNSIGNED NOT NULL COMMENT '组织id',
    `rid` INT(16) UNSIGNED NOT NULL COMMENT '角色id',
    `pid` INT(16) UNSIGNED NOT NULL COMMENT '权限id',
    PRIMARY KEY (`oid`, `rid`, `pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sign_in_log(
    `aid` VARCHAR(32) NOT NULL COMMENT '账户id',
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
	`start_time` DATETIME NOT NULL COMMENT '开始时间',
	PRIMARY KEY (`aid`, `start_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

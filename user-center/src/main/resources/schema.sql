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

CREATE TABLE securityLog(
	`aid` VARCHAR(32) NOT NULL COMMENT '账户id',
	`input_time` DATETIME NOT NULL COMMENT '进入时间',
	PRIMARY KEY (`aid`, `input_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

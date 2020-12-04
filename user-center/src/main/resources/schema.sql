CREATE TABLE account(
    `sign_id` VARCHAR(32) UNIQUE NOT NULL COMMENT '',
    `name` VARCHAR(32) NOT NULL COMMENT '',
    `password` VARCHAR(256) NOT NULL COMMENT '',
    `gender` TINYINT COMMENT '',
    `create_data` DATETIME COMMENT '',
	`security_key` VARCHAR(256) COMMENT '',
    PRIMARY KEY (`sign_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE organization(
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT '',
    `creator` VARCHAR(32) NOT NULL COMMENT '',
    `name` VARCHAR(64) NOT NULL COMMENT '',
    `visibility` TINYINT COMMENT '',
    `create_data` DATETIME COMMENT '',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission(
    `id` INT UNSIGNED AUTO_INCREMENT COMMENT '',
    `name` VARCHAR(16) NOT NULL COMMENT '',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_organization(
    `aid` VARCHAR(32) NOT NULL COMMENT '',
    `oid` INT NOT NULL COMMENT '',
    `role` TINYINT NOT NULL COMMENT '',
    PRIMARY KEY (`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permission(
	`oid` INT NOT NULL COMMENT '',
    `rid` INT NOT NULL COMMENT '',
    `pid` INT NOT NULL COMMENT '',
    PRIMARY KEY (`oid`, `rid`, `pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE signInLog(
    `aid` VARCHAR(32) NOT NULL COMMENT '',
    `sign_in_time` DATETIME NOT NULL COMMENT '',
    `sign_out_time` DATETIME COMMENT '',
    `ip` VARCHAR(16) COMMENT '',
    `source` TINYINT COMMENT '',
	`type` TINYINT COMMENT '',
	`cookie` VARCHAR(64) COMMENT '',
    PRIMARY KEY (`aid`, `sign_in_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE securityLog(
	`aid` VARCHAR(32) NOT NULL COMMENT '',
	`input_time` DATETIME NOT NULL COMMENT '',
	PRIMARY KEY (`aid`, `input_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

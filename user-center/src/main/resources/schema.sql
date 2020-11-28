CREATE TABLE account(
    `sign_id` VARCHAR(32) UNIQUE NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `gender` TINYINT,
    `create_data` DATETIME,
	`security_key` VARCHAR(256),
    PRIMARY KEY (`sign_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE organization(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `creator` VARCHAR(32) NOT NULL,
    `name` VARCHAR(64) NOT NULL,
    `visibility` TINYINT,
    `create_data` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permission(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE account_organization(
    `aid` VARCHAR(32) NOT NULL,
    `oid` INT NOT NULL,
    `role` TINYINT NOT NULL,
    PRIMARY KEY (`aid`, `oid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE role_permission(
	`oid` INT NOT NULL,
    `rid` INT NOT NULL,
    `pid` INT NOT NULL,
    PRIMARY KEY (`oid`, `rid`, `pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE signInLog(
    `aid` VARCHAR(32) NOT NULL,
    `sign_in_time` DATETIME NOT NULL,
    `sign_out_time` DATETIME,
    `ip` VARCHAR(16),
    `source` TINYINT,
	`type` TINYINT,
	`cookie` VARCHAR(64),
    PRIMARY KEY (`aid`, `sign_in_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE securityLog(
	`aid` VARCHAR(32) NOT NULL,
	`input_time` DATETIME NOT NULL,
	PRIMARY KEY (`aid`, `input_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

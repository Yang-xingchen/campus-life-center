CREATE TABLE info(
    `id` BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `type` INT(4) NOT NULL DEFAULT 0 COMMENT '类型: 0.文本; 1.数组/对象; 2.单选',
    `persistent_source` VARCHAR(64) COMMENT '非持久化来源',
    `default_visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_text(
    `id` BIGINT UNSIGNED NOT NULL,
    `sample` VARCHAR(64),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE info_array(
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
    `text` VARCHAR(512),
    PRIMARY KEY (`source`, `id`, `aid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户信息
CREATE TABLE account_info(
    `aid` VARCHAR(32) NOT NULL,
    `id` BIGINT NOT NULL,
    `text` VARCHAR(512),
    `code` BIT(1) NOT NULL DEFAULT 0 COMMENT '是否加密',
    `visibility` INT(4) NOT NULL DEFAULT 0 COMMENT '公开度: 0.公开; 1.统计; 2.管理员; 3.私密',
    PRIMARY KEY(`aid`, `id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

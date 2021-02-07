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

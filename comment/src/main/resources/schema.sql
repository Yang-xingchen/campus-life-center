-- -- 评论模块 -- --
CREATE TABLE comment(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `parent` BIGINT UNSIGNED,
    `aid` VARCHAR(32) NOT NULL,
    `publish_time` DATETIME NOT NULL,
    `content` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE comment_ref(
    `id` BIGINT UNSIGNED NOT NULL,
    `ref` VARCHAR(64) NOT NULL,
    INDEX (`ref`),
    PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
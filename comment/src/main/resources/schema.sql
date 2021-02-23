-- clc.comment definition

CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `parent` bigint(20) unsigned DEFAULT NULL,
  `aid` varchar(32) NOT NULL,
  `publish_time` datetime NOT NULL,
  `content` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- clc.ref_comment definition

CREATE TABLE `ref_comment` (
  `id` bigint(20) unsigned NOT NULL,
  `ref` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ref` (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
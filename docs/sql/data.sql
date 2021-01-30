INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("root", "超级管理员", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    ("root", "system", "系统管理员", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("root", 1, 0, "系统管理员");

-- test data --
INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("user", "测试用户1", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK");

INSERT INTO organization
    (`creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    ("root", "测试年级1", "XX年级", 0, now()),
    ("root", "测试班级1", "XX班", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("user", 2, 1, "测试学生"),
    ("user", 3, 0, "测试管理员");
-- test data --
INSERT INTO notice
    (`id`,`creator`, `organization`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`, `create_time`, `start_time`, `end_time`, `todo_ref`, `file_ref`)
VALUES
    (1, "root", 1, 0, 1, 0, 1,
        "环境配置", "通知管理系统环境配置", 0, NOW(), NULL, NULL, "testRef1", NULL),
    (2, "root", 1, 0, 2, 1, 1,
        "模块建立", "# 各*模块*的建立", 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, "testRef2", NULL),
    (3, "root", 1, 0, 5, 2, 2,
        "代码编写", "<p style='color: red'>代码</p>", 2, NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY), NULL, NULL),
    (4, "root", 2, 0, 1, 0, 1,
        "数据收集", "收集测试数据", 0, NOW(), NULL, NULL, NULL, NULL),
    (5, "root", 2, 0, 3, 1, 1,
        "卫检", "XX社区卫生检查", 0, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, NULL, "testRef1");

INSERT INTO notice_info
    (`nid`, `ref`)
VALUES
    (4, 'testRef1'),
    (4, 'testRef2');

INSERT INTO notice_tag
    (`nid`, `tag`)
VALUES
    (1, "系统"),
    (2, "系统"),
    (3, "系统"),
    (4, "系统"),
    (4, "基础");

INSERT INTO account_subscribe
    (`aid`, `oid`)
VALUES
    ("root", 1),
    ("user", 1);

INSERT INTO account_notice
    (`nid`, `aid`, `looked`, `top`, `del`, `relative_importance`)
VALUES
    (1, "root", 1, 0, 1, 0),
    (2, "root", 1, 0, 0, 1),
    (3, "root", 1, 1, 0, -1),
    (4, "root", 0, 0, 0, 0),
    (5, "root", 0, 0, 0, -2);
    
INSERT INTO notice_update_log
    (`nid`, `version`, `update_time`, `title`, `content`, `importance`, `start_time`, `end_time`)
VALUES
    (3, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), "代码编写", "被更新内容", 5, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY));

INSERT INTO publish_organization
    (`nid`, `oid`, `dynamic`, `belong`, `subscribe`)
VALUES
    (4, 1, 0, 1, 1),
    (4, 2, 1, 1, 1);

INSERT INTO publish_info
    (`nid`, `iid`, `dynamic`, `text`)
VALUES
    (5, 3, 0, 'XX');
INSERT INTO todo
    (`id`, `ref`, `title`)
VALUES
    (1, 'testRef1', 'java'),
    (2, 'testRef1', 'docker'),
    (3, 'testRef1', 'nodejs'),
    (4, 'testRef2', 'user-center'),
    (5, 'testRef2', 'notice'),
    (6, 'testRef2', 'todo'),
    (7, 'testRef2', 'info'),
    (8, 'testRef2', 'comment');

INSERT INTO account_todo
    (`id`, `aid`, `finish`, `top`, `add_list`)
VALUES
    (1, 'root', 1, 0, 0),
    (2, 'root', 1, 0, 1),
    (3, 'root', 1, 0, 1),
    (4, 'root', 1, 0, 0),
    (5, 'root', 1, 0, 1),
    (6, 'root', 1, 0, 0),
    (7, 'root', 0, 1, 1),
    (8, 'root', 0, 0, 1);
-- base data
INSERT INTO info
    (`id`, `name`, `hide`, `type`, `multiple`, `default_visibility`)
VALUES
    (1, '手机号', 0, 0, 1, 0),
    (2, '住宿信息', 0, 1, 0, 0),
    (3, '宿舍社区', 0, 2, 0, 0),
    (4, '宿舍楼层', 0, 0, 0, 1),
    (5, '宿舍房间号', 0, 0, 0, 2),
    (6, '班级信息', 0, 1, 0, 0),
    (7, '年级', 0, 1, 0, 0),
    (8, '专业', 0, 1, 0, 0),
    (9, '班级', 0, 1, 0, 0),
    (10, '基础数据', 1, 1, 0, 2),
    (11, '住宿信息', 1, 1, 0, 2);

INSERT INTO info_text
    (`id`, `type`, `sample`)
VALUES
    (1, 1, '12345678910'),
    (4, 1, '4'),
    (5, 1, '17');

INSERT INTO info_composite
    (`id`, `pid`, `composite_index`)
VALUES
    (3, 2, 0),
    (4, 2, 0),
    (5, 2, 0),
    (7, 6, 0),
    (8, 6, 1),
    (9, 6, 2),
    (1, 10, 0),
    (2, 11, 0);

INSERT INTO info_radio
    (`id`, `text`)
VALUES
    (3, 'XX社区'),
    (3, 'YY社区'),
    (3, 'ZZ社区'),
    (7, '16级'),
    (7, '17级'),
    (7, '18级'),
    (7, '19级'),
    (7, '20级'),
    (8, 'XX专业'),
    (8, 'YY专业'),
    (9, '1班'),
    (9, '2班'),
    (9, '3班'),
    (9, '4班');

-- test data
INSERT INTO account_submit
    (`root`, `id`, `multiple_index`, `aid`, `text`)
VALUES
    (10, 10, 0, 'root', NULL),
    (10, 1, 0, 'root', '13812345678'),
    (10, 1, 1, 'root', '13800000000'),
    (11, 11, 0, 'root', NULL),
    (11, 2, 0, 'root', ''),
    (11, 3, 0, 'root', 'XX社区'),
    (11, 4, 0, 'root', '4'),
    (11, 5, 0, 'root', '17');

INSERT INTO ref_root
    (`ref`, `root`)
VALUES
    ('testRef1', 10),
    ('testRef2', 11);

INSERT INTO account_save_info
    (`aid`, `id`, `multiple_index`, `text`, `code`, `visibility`)
VALUES
    ('root', 1, 0, '13812345678', 0, 0),
    ('root', 1, 1, '13800000000', 0, 0),
    ('root', 2, 0, '', 0, 0),
    ('root', 3, 0, 'XX社区', 0, 0),
    ('root', 4, 0, '4', 0, 1),
    ('root', 5, 0, '17', 0, 2);

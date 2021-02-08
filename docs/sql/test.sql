INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("user", "测试用户1", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK");

INSERT INTO organization
    (`id`, `parent`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (2, 1, "root", "测试年级1", "XX年级", 0, now()),
    (3, 2, "root", "测试班级1", "XX班", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `hide`, `account_accept`, `organization_accept`)
VALUES
    ("user", 1, 0, 1, 1),
    ("user", 2, 0, 1, 1),
    ("user", 3, 0, 1, 1);

INSERT INTO role
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("user", 2, 1, "测试学生"),
    ("user", 3, 0, "测试管理员");

INSERT INTO role_permission
    (`oid`, `rid`, `pid`)
VALUES
    (2, 1, 7),
    (3, 0, 6),
    (3, 0, 11);
INSERT INTO notice
    (`id`,`creator`, `organization`, `publish_status`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`,
        `create_time`, `start_time`, `end_time`,
        `todo_ref`, `file_ref`)
VALUES
    (1, "root", 1, 4, 0, 1, 0, 1,
        "环境配置", "通知管理系统环境配置", 0,
        NOW(), NULL, NULL,
        "testRef1", "testRef1"),
    (2, "root", 1, 4, 0, 2, 1, 1,
        "模块建立", "# 各*模块*的建立", 1,
        NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL,
        "testRef2", "testRef2"),
    (3, "root", 1, 4, 0, 5, 2, 2,
        "代码编写", "<p style='color: red'>代码</p>", 2,
        NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY),
        NULL, "testRef3"),
    (4, "root", 2, 4, 0, 1, 0, 1,
        "数据收集", "收集测试数据", 0,
        NOW(), NULL, NULL,
        NULL, "testRef4"),
    (5, "root", 2, 4, 0, 3, 1, 1,
        "卫检", "XX社区卫生检查", 0,
        NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL,
        NULL, "testRef5");

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
    (`id`, `version`, `update_time`,
        `public_type`, `title`, `content`, `content_type`,
         `importance`, `start_time`, `end_time`)
VALUES
    (3, 1, DATE_ADD(NOW(), INTERVAL 1 DAY),
        2, "代码编写", "被更新内容", 1,
        5, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY));

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

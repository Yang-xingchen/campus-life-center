INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("admin1", "测试专业管理", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("admin2", "测试年级管理", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("admin3", "测试XX班管理", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("admin4", "测试YY班管理", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("user1", "测试用户1", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("user2", "测试用户2", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("user3", "测试用户3", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("user4", "测试用户4", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK"),
    ("user5", "测试用户5", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK");

INSERT INTO organization
    (`id`, `parent`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (2, 1, "root", "测试专业", "XX专业", 0, now()),
    (3, 2, "admin1", "测试年级", "XX年级", 0, now()),
    (4, 3, "admin1", "测试班级", "XX班", 0, now()),
    (5, 3, "admin2", "测试班级", "YY班", 0, now()),
    (6, 1, "root", "测试社区", "XX社区", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `hide`, `account_accept`, `organization_accept`)
VALUES
    ("admin1", 1, 0, 1, 1),
    ("admin2", 1, 0, 1, 1),
    ("admin3", 1, 0, 1, 1),
    ("admin4", 1, 0, 1, 1),
    ("user1", 1, 0, 1, 1),
    ("user2", 1, 0, 1, 1),
    ("user3", 1, 0, 1, 1),
    ("user4", 1, 0, 1, 1),
    ("user5", 1, 0, 1, 1),
    ("admin1", 2, 0, 1, 1),
    ("admin2", 2, 0, 1, 1),
    ("admin3", 2, 0, 1, 1),
    ("admin4", 2, 0, 1, 1),
    ("user1", 2, 0, 1, 1),
    ("user2", 2, 0, 1, 1),
    ("user3", 2, 0, 1, 1),
    ("user4", 2, 0, 1, 1),
    ("user5", 2, 0, 1, 1),
    ("admin2", 3, 0, 1, 1),
    ("admin3", 3, 0, 1, 1),
    ("admin4", 3, 0, 1, 1),
    ("user1", 3, 0, 1, 1),
    ("user2", 3, 0, 1, 1),
    ("user3", 3, 0, 1, 1),
    ("user4", 3, 0, 1, 1),
    ("user5", 3, 0, 1, 1),
    ("admin3", 4, 0, 1, 1),
    ("admin4", 5, 0, 1, 1),
    ("user1", 4, 0, 1, 1),
    ("user2", 4, 0, 1, 1),
    ("user3", 4, 0, 1, 0),
    ("user4", 5, 0, 0, 1),
    ("user5", 5, 0, 1, 0);

INSERT INTO role
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("root", 2, 0, "创建者"),
    ("admin1", 3, 0, "创建者"),
    ("admin1", 4, 0, "创建者"),
    ("admin2", 5, 0, "创建者"),
    ("root", 6, 0, "创建者"),
    ("admin1", 2, 1, "管理员");
    ("admin2", 3, 1, "管理员");
    ("admin3", 4, 1, "管理员");
    ("admin4", 5, 1, "管理员");

INSERT INTO role_permission
    (`oid`, `rid`, `pid`)
VALUES
    (2, 0, 101),
    (2, 0, 102),
    (2, 0, 103),
    (2, 0, 205),
    (3, 0, 101),
    (3, 0, 102),
    (3, 0, 103),
    (3, 0, 205),
    (4, 0, 101),
    (4, 0, 102),
    (4, 0, 103),
    (4, 0, 205),
    (5, 0, 101),
    (5, 0, 102),
    (5, 0, 103),
    (5, 0, 205),
    (6, 0, 101),
    (6, 0, 102),
    (6, 0, 103),
    (6, 0, 205),
    (2, 1, 101),
    (2, 1, 102),
    (2, 1, 205),
    (3, 1, 101),
    (3, 1, 102),
    (3, 1, 205),
    (4, 1, 101),
    (4, 1, 102),
    (4, 1, 205),
    (5, 1, 101),
    (5, 1, 102),
    (5, 1, 205);
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
    (5, "root", 6, 4, 0, 3, 1, 1,
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
    ("user1", 6),
    ("user2", 6),
    ("user3", 6);

INSERT INTO account_notice
    (`nid`, `aid`, `looked`, `top`, `del`, `relative_importance`)
VALUES
    (1, "root", 1, 0, 1, 0),
    (2, "root", 1, 0, 0, 1),
    (3, "root", 1, 1, 0, -1),
    (4, "root", 0, 0, 0, 0),
    (5, "root", 0, 0, 0, -2),
    (5, "user1", 0, 0, 0, -1),
    (5, "user2", 1, 0, 0, -2);
--  (5, "user3", 0, 0, 0, -1)
    
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
    (5, 6, 0, 0, 1);

INSERT INTO publish_info
    (`nid`, `iid`, `dynamic`, `text`)
VALUES
    (5, 3, 0, 'XX社区');
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

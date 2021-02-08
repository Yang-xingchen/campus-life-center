INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("root", "超级管理员", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`id`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (1, "root", "system", "系统", 0, now());

INSERT INTO permission
    (`id`, `type`, `name`)
VALUES
    (1, 0, "account:add"),
    (2, 1, "child:create"),
    (3, 1, "child:del"),
    (4, 1, "member:add"),
    (5, 1, "member:del"),
    (6, 1, "info"),
    (7, 2, "importance:1"),
    (8, 2, "importance:2"),
    (9, 2, "importance:3"),
    (10, 2, "importance:4"),
    (11, 2, "importance:5");

INSERT INTO account_organization
    (`aid`, `oid`, `hide`, `account_accept`, `organization_accept`)
VALUES
    ("root", 1, 1, 1, 1);

INSERT INTO role
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("root", 1, 0, "系统管理员");

INSERT INTO role_permission
    (`oid`, `rid`, `pid`)
VALUES
    (1, 0, 1),
    (1, 0, 2),
    (1, 0, 3),
    (1, 0, 4),
    (1, 0, 5),
    (1, 0, 6),
    (1, 0, 11);
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
    (11, '住宿信息', 1, 1, 0, 2),
    (12, '头像', 1, 3, 0, 0),
    (13, '描述', 1, 0, 0, 0);

INSERT INTO info_text
    (`id`, `type`, `sample`)
VALUES
    (1, 1, '12345678910'),
    (4, 1, '4'),
    (5, 1, '17'),
    (13, 0, 'XXX');

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

INSERT INTO info_file
    (`id`, `multiple_index`, `path`)
VALUES
    (12, 0, 'head');

INSERT INTO account_save_info
    (`aid`, `id`, `multiple_index`, `text`, `code`, `visibility`)
VALUES
    ('root', 12, 0, '/info/head/root/0/77045517_p4.jpg', 0, 0);

INSERT INTO organization_save_info
    (`oid`, `id`, `multiple_index`, `text`)
VALUES
    (1, 13, 0, '系统管理员组, 管理系统内部事物。');

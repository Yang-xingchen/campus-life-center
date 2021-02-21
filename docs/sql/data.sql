INSERT INTO account
    (`id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("root", "超级管理员", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`id`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (1, "root", "system", "系统", 0, now());

INSERT INTO permission
    (`id`, `name`)
VALUES
    (1, "account"),
    (2, "monitor"),
    (101, "info"),
    (102, "child"),
    (103, "member"),
    (201, "importance:1"),
    (202, "importance:2"),
    (203, "importance:3"),
    (204, "importance:4"),
    (205, "importance:5");

INSERT INTO account_organization
    (`aid`, `oid`, `hide`, `account_accept`, `organization_accept`)
VALUES
    ("root", 1, 1, 1, 1);

INSERT INTO role
    (`id`, `name`)
VALUES
    (1, "系统管理员");

INSERT INTO account_organization_role
    (`aid`, `oid`, `id`)
VALUES
    ('root', 1, 1);

INSERT INTO role_permission
    (`oid`, `rid`, `pid`)
VALUES
    (1, 1, 1),
    (1, 1, 2),
    (1, 1, 101),
    (1, 1, 102),
    (1, 1, 103),
    (1, 1, 205);


INSERT INTO info
    (`id`, `name`, `hide`, `type`, `multiple`, `default_visibility`)
VALUES
    (1, '手机号', 0, 0, 1, 0),
    (2, 'QQ', 0, 0, 1, 0),
    (3, '微信', 0, 0, 1, 0),
    (4, '邮箱', 0, 0, 1, 0),
    (5, '头像', 1, 3, 0, 0),
    (6, '描述', 1, 0, 0, 0),
    (7, '住宿信息', 0, 1, 0, 0),
    (8, '宿舍社区', 0, 2, 0, 0),
    (9, '宿舍楼层', 0, 0, 0, 1),
    (10, '宿舍房间号', 0, 0, 0, 2),
    (11, '班级信息', 0, 1, 0, 0),
    (12, '年级', 0, 2, 0, 0),
    (13, '专业', 0, 2, 0, 0),
    (14, '班级', 0, 2, 0, 0),
    (15, '基础数据', 1, 1, 0, 2),
    (16, '住宿信息', 1, 1, 0, 2);

INSERT INTO info_text
    (`id`, `type`, `sample`)
VALUES
    (1, 1, '12345678910'),
    (2, 1, '123456789'),
    (3, 0, '123456789'),
    (4, 0, '123456789@xx.com'),
    (6, 0, 'XXX'),
    (9, 1, '4'),
    (10, 1, '17');

INSERT INTO info_composite
    (`id`, `pid`, `composite_index`)
VALUES
    (1, 15, 0),
    (2, 15, 1),
    (3, 15, 2),
    (4, 15, 3),
    (5, 15, 4),
    (7, 16, 0),
    (8, 7, 0),
    (9, 7, 1),
    (10, 7, 2),
    (11, 15, 5),
    (12, 11, 0),
    (13, 11, 1),
    (14, 11, 2);


INSERT INTO info_radio
    (`id`, `text`)
VALUES
    (8, 'XX社区'),
    (8, 'YY社区'),
    (8, 'ZZ社区'),
    (12, '16级'),
    (12, '17级'),
    (12, '18级'),
    (12, '19级'),
    (12, '20级'),
    (13, 'XX专业'),
    (13, 'YY专业'),
    (14, '1班'),
    (14, '2班'),
    (14, '3班'),
    (14, '4班');

INSERT INTO info_file
    (`id`, `path`)
VALUES
    (5, 'head');

INSERT INTO account_save_info
    (`aid`, `id`, `multiple_index`, `content`, `code`, `visibility`)
VALUES
    ('root', 5, 0, '/info/head/root/77045517_p4.jpg', 0, 0);

INSERT INTO organization_save_info
    (`oid`, `id`, `multiple_index`, `content`)
VALUES
    (1, 6, 0, '系统管理员组, 管理系统内部事物。');



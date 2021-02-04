INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("root", "超级管理员", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`id`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (1, "root", "system", "系统管理员", 0, now());

INSERT INTO permission
    (`id`, `type`, `name`)
VALUES
    (1, 0, "create"),
    (2, 0, "edit"),
    (3, 0, "del"),
    (4, 1, "add"),
    (5, 1, "del"),
    (6, 1, "role"),
    (7, 2, "importance:1"),
    (8, 2, "importance:2"),
    (9, 2, "importance:3"),
    (10, 2, "importance:4"),
    (11, 2, "importance:5");

INSERT INTO account_organization
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
    (1, 0, 7),
    (1, 0, 8),
    (1, 0, 9),
    (1, 0, 10),
    (1, 0, 11);

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

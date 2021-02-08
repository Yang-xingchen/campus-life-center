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

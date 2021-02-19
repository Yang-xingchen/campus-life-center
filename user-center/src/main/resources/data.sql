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

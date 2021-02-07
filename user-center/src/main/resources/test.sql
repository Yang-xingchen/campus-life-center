INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("user", "测试用户1", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK");

INSERT INTO organization
    (`id`, `parent`, `creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    (2, NULL, "root", "测试年级1", "XX年级", 0, now()),
    (3, 2, "root", "测试班级1", "XX班", 0, now());

INSERT INTO account_organization
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

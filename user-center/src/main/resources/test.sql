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
    ("root", 2, 0, 1, 1),
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
    ("user5", 5, 0, 1, 0),
    ("root", 6, 0, 1, 1);

INSERT INTO role
    (`aid`, `oid`, `id`, `name`)
VALUES
    ("root", 2, 0, "创建者"),
    ("admin1", 3, 0, "创建者"),
    ("admin1", 4, 0, "创建者"),
    ("admin2", 5, 0, "创建者"),
    ("root", 6, 0, "创建者"),
    ("admin1", 2, 1, "管理员"),
    ("admin2", 3, 1, "管理员"),
    ("admin3", 4, 1, "管理员"),
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

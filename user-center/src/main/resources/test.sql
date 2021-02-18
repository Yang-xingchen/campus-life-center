INSERT INTO account
    (`id`, `name`, `password`, `gender`, `create_data`, `security_key`)
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
    (`id`, `name`)
VALUES
    (2, "创建者"),
    (3, "创建者"),
    (4, "创建者"),
    (5, "创建者"),
    (6, "创建者"),
    (7, "管理员"),
    (8, "管理员"),
    (9, "管理员"),
    (10, "管理员");


INSERT INTO account_organization_role
    (`aid`, `oid`, `id`)
VALUES
    ("root", 2, 2),
    ("admin1", 3, 3),
    ("admin1", 4, 4),
    ("admin2", 5, 5),
    ("root", 6, 6),
    ("admin1", 2, 7),
    ("admin2", 3, 8),
    ("admin3", 4, 9),
    ("admin4", 5, 10);

INSERT INTO role_permission
    (`oid`, `rid`, `pid`)
VALUES
    (2, 2, 101),
    (2, 2, 102),
    (2, 2, 103),
    (2, 2, 205),
    (3, 3, 101),
    (3, 3, 102),
    (3, 3, 103),
    (3, 3, 205),
    (4, 4, 101),
    (4, 4, 102),
    (4, 4, 103),
    (4, 4, 205),
    (5, 5, 101),
    (5, 5, 102),
    (5, 5, 103),
    (5, 5, 205),
    (6, 6, 101),
    (6, 6, 102),
    (6, 6, 103),
    (6, 6, 205),
    (2, 7, 101),
    (2, 7, 102),
    (2, 7, 205),
    (3, 8, 101),
    (3, 8, 102),
    (3, 8, 205),
    (4, 9, 101),
    (4, 9, 102),
    (4, 9, 205),
    (5, 10, 101),
    (5, 10, 102),
    (5, 10, 205);

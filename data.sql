INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("root", "root", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    ("root", "system", "root", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("root", 1, 0, "root");

-- test data --
INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES
    ("user", "user", "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK", 1, now(), "$2a$10$g5pEvedakVztoRyp8uTRe.veWln9MwZjs.y9CEhQ3mKnXREiIe6EK");

INSERT INTO organization
    (`creator`, `type`, `name`, `visibility`, `create_data`)
VALUES
    ("root", "test", "parent", 0, now()),
    ("root", "test", "child", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `role`, `role_name`)
VALUES
    ("user", 2, 1, "member"),
    ("user", 3, 0, "root");
-- test data --
INSERT INTO notice
    (`id`,`creator`, `organization`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`, `create_time`, `start_time`, `end_time`, `todo_ref`)
VALUES
    (1, "root", 1, 0, 1, 0, 1,
        'rr1010', 'content', 1, NOW(), NULL, NULL, '1'),
    (2, "root", 1, 0, 3, 1, 1,
        'rr1031', 'content', 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, '2'),
    (3, "root", 1, 0, 5, 2, 1,
        'rr1052', 'content', 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY), '3'),
    (4, "root", 2, 0, 1, 0, 1,
        'rp2010', 'content', 1, NOW(), NULL, NULL, '4'),
    (5, "root", 2, 0, 2, 0, 1,
        'rp2020', 'content', 1, NOW(), NULL, NULL, '5'),
    (6, "root", 2, 0, 3, 0, 1,
        'rp2030', 'content', 1, NOW(), NULL, NULL, '6'),
    (7, "root", 2, 0, 3, 0, 1,
        'rp2030', 'content', 1, NOW(), NULL, NULL, '7');

INSERT INTO account_subscribe
    (`aid`, `oid`)
VALUES
    ("root", 1),
    ("user", 1);

INSERT INTO account_notice
    (`nid`, `aid`, `looked`, `top`, `del`, `relative_importance`)
VALUES
    (1, 'root', 0, 0, 0, 0),
    (2, 'root', 0, 0, 0, 0),
    (3, 'root', 0, 0, 0, 0),
    (4, 'root', 0, 1, 0, 0),
    (5, 'root', 1, 1, 0, 0),
    (6, 'root', 0, 0, 1, -1),
    (7, 'root', 0, 0, 0, 2);

INSERT INTO publish_organization
    (`nid`, `oid`, `dynamic`, `belong`, `subscribe`)
VALUES
    (1, 1, 0, 1, 1),
    (2, 1, 0, 1, 1),
    (3, 1, 0, 1, 1),
    (4, 1, 0, 1, 1),
    (5, 1, 0, 1, 1),
    (6, 1, 0, 1, 1),
    (7, 1, 0, 1, 1);

INSERT INTO notice_tag
    (`nid`, `tag`)
VALUES
    (1, 'test1'),
    (2, 'test1'),
    (2, 'test2'),
    (3, 'test2'),
    (3, 'test3');
INSERT INTO todo
    (`id`, `source`, `title`)
VALUES
    (1, '1', 'n1_i1'),
    (2, '2', 'n1_i2_a'),
    (3, '3', 'n1_i3_ta'),
    (4, '4', 'n1_i4_fta'),
    (5, '1', 'n2_i1_fa'),
    (6, '1', 'n3_i1_f'),
    (7, '2', 'n3_i2_a');

INSERT INTO account_todo
    (`id`, `aid`, `finish`, `top`, `add_list`)
VALUES
    (1, 'root', 0, 0, 0),
    (2, 'root', 0, 0, 1),
    (3, 'root', 0, 1, 1),
    (4, 'root', 1, 1, 1),
    (5, 'root', 1, 0, 1),
    (6, 'root', 1, 0, 0),
    (7, 'root', 0, 0, 1);
-- test data
INSERT INTO info
    (`id`, `name`, `type`, `persistent_source`, `default_visibility`)
VALUES
    (1, '手机号', 0, NULL, 0),
    (2, '宿舍', 1, NULL, 0),
    (3, '宿舍社区', 2, NULL, 0),
    (4, '宿舍楼层', 0, NULL, 1),
    (5, '宿舍房间号', 0, NULL, 2);

INSERT INTO info_text
    (`id`, `sample`)
VALUES
    (1, '12345678910'),
    (4, '4'),
    (5, '17');

INSERT INTO info_array
    (`id`, `pid`)
VALUES
    (3, 2),
    (4, 2),
    (5, 2);

INSERT INTO info_radio
    (`id`, `text`)
VALUES
    (3, 'XX社区'),
    (4, 'YY社区'),
    (5, 'ZZ社区');

INSERT INTO account_info
    (`aid`, `id`, `text`, `code`, `visibility`)
VALUES
    ('root', 1, '13812345678', 0, 0),
    ('root', 3, 'XX社区', 0, 0),
    ('root', 4, '4', 0, 1),
    ('root', 5, '17', 0, 2);

-- test data --
INSERT INTO notice
    (`creator`, `organization`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`, `create_time`, `start_time`, `end_time`)
VALUES
    ("root", 1, 0, 1, 0, 1,
        'rr1010', 'content', 1, NOW(), NULL, NULL),
    ("root", 1, 0, 3, 1, 1,
        'rr1031', 'content', 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL),
    ("root", 1, 0, 5, 2, 1,
        'rr1052', 'content', 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY)),
    ("root", 2, 0, 1, 0, 1,
        'rp2010', 'content', 1, NOW(), NULL, NULL),
    ("root", 2, 0, 2, 0, 1,
        'rp2020', 'content', 1, NOW(), NULL, NULL),
    ("root", 2, 0, 3, 0, 1,
        'rp2030', 'content', 1, NOW(), NULL, NULL),
    ("root", 2, 0, 3, 0, 1,
        'rp2030', 'content', 1, NOW(), NULL, NULL);

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

INSERT INTO notice_tag
    (`nid`, `tag`)
VALUES
    (1, 'test1'),
    (2, 'test1'),
    (2, 'test2'),
    (3, 'test2'),
    (3, 'test3');

INSERT INTO notice_todo
    (`nid`, `id`, `type`, `type_value`)
VALUES
    (1, 1, 0, 'n1_i1'),
    (1, 2, 0, 'n1_i2_a'),
    (1, 3, 0, 'n1_i3_ta'),
    (1, 4, 0, 'n1_i4_fta'),
    (2, 1, 0, 'n2_i1_fa'),
    (3, 1, 0, 'n3_i1_f'),
    (3, 2, 0, 'n3_i2_a');

INSERT INTO account_notice_todo
    (`nid`, `id`, `aid`, `finish`, `top`, `add_list`)
VALUES
    (1, 1, 'root', 0, 0, 0),
    (1, 2, 'root', 0, 0, 1),
    (1, 3, 'root', 0, 1, 1),
    (1, 4, 'root', 1, 1, 1),
    (2, 1, 'root', 1, 0, 1),
    (3, 1, 'root', 1, 0, 0),
    (3, 2, 'root', 0, 0, 1);

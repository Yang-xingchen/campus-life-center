-- test data --
INSERT INTO notice
    (`creator`, `organization`, `visibility`, `importance`, `public_type`, `title`, `content`, `create_time`, `start_time`, `end_time`)
VALUES
    ("root", 1, 0, 1, 0, 'r1010', 'content', NOW(), NULL, NULL),
    ("root", 1, 0, 3, 1, 'r1031', 'content', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL),
    ("root", 1, 0, 5, 2, 'r1052', 'content', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY));

INSERT INTO account_subscribe
    (`aid`, `oid`)
VALUES
    ("root", 1);

INSERT INTO account_notice
    (`nid`, `aid`, `is_read`, `is_top`, `is_delete`, `relative_importance`)
VALUES
    (1, 'root', 0, 0, 0, 0),
    (2, 'root', 0, 0, 0, 0),
    (3, 'root', 0, 0, 0, -1);

INSERT INTO notice_tag
    (`nid`, `tag`)
VALUES
    (1, 'test1'),
    (2, 'test1'),
    (2, 'test2'),
    (3, 'test2'),
    (3, 'test3');

INSERT INTO notice_todo
    (`nid`, `id`, `type`, `value`)
VALUES
    (1, 1, 0, 'n1i1fa'),
    (1, 2, 0, 'n1i2f'),
    (1, 3, 0, 'n1i3a'),
    (1, 4, 0, 'n1i4'),
    (2, 1, 0, 'n2i1fa'),
    (3, 1, 0, 'n3i1a'),
    (3, 2, 0, 'n3i2a');

INSERT INTO account_notice_todo
    (`nid`, `id`, `aid`, `finish`, `is_top`, `is_add`)
VALUES
    (1, 1, 'root', 1, 0, 1),
    (1, 2, 'root', 1, 0, 0),
    (1, 3, 'root', 0, 0, 1),
    (1, 4, 'root', 0, 0, 0),
    (2, 1, 'root', 1, 0, 1),
    (3, 1, 'root', 0, 0, 1),
    (3, 2, 'root', 0, 0, 1);

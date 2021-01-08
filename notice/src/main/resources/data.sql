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

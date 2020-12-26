-- test data --
INSERT INTO notice
    (`creator`, `organization`, `visibility`, `importance`, `public_type`, `title`, `content`, `create_time`, `start_time`, `end_time`)
VALUES
    ("root", 1, 0, 0, 0, 'r1000', 'content', NOW(), NULL, NULL),
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

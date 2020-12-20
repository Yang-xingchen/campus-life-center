-- test data --
INSERT INTO notice
    (`creator`, `organization`, `visibility`, `public_type`, `create_time`, `title`, `content`, `importance`, `time`)
VALUES
    ("root", 1, 0, 0, NOW(), 'r1000', 'content', 0, DATE_ADD(NOW(), INTERVAL 1 DAY)),
    ("root", 1, 0, 1, NOW(), 'r1013', 'content', 3, DATE_ADD(NOW(), INTERVAL 1 DAY)),
    ("root", 1, 0, 2, NOW(), 'r1025', 'content', 5, DATE_ADD(NOW(), INTERVAL 1 DAY));

INSERT INTO account_subscribe
    (`aid`, `oid`)
VALUES
    ("root", 1);

INSERT INTO account_notice
    (`nid`, `aid`, `is_read`, `top`, `is_delete`, `relative_importance`)
VALUES
    (3, 'root', 0, 0, 0, -1);

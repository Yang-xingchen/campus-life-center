INSERT INTO account_submit
    (`root`, `id`, `multiple_index`, `aid`, `content`)
VALUES
    (10, 10, 0, 'root', NULL),
    (10, 1, 0, 'root', '13812345678'),
    (10, 1, 1, 'root', '13800000000'),
    (11, 11, 0, 'root', NULL),
    (11, 2, 0, 'root', ''),
    (11, 3, 0, 'root', 'XX社区'),
    (11, 4, 0, 'root', '4'),
    (11, 5, 0, 'root', '17');

INSERT INTO ref_root
    (`ref`, `root`)
VALUES
    ('testRef1', 10),
    ('testRef2', 11);

INSERT INTO account_save_info
    (`aid`, `id`, `multiple_index`, `content`, `code`, `visibility`)
VALUES
    ('root', 1, 0, '13812345678', 0, 0),
    ('root', 1, 1, '13800000000', 0, 0),
    ('root', 2, 0, '', 0, 0),
    ('root', 3, 0, 'XX社区', 0, 0),
    ('root', 4, 0, '4', 0, 1),
    ('root', 5, 0, '17', 0, 2);

INSERT INTO account_submit
    (`root`, `id`, `multiple_index`, `aid`, `content`)
VALUES
    (15, 15, 0, 'root', NULL),
    (15, 1, 0, 'root', '13812345678'),
    (15, 1, 1, 'root', '13800000000'),
    (16, 16, 0, 'root', NULL),
    (16, 7, 0, 'root', ''),
    (16, 8, 0, 'root', 'XX社区'),
    (16, 9, 0, 'root', '4'),
    (16, 10, 0, 'root', '17');

INSERT INTO ref_root
    (`ref`, `root`)
VALUES
    ('testRef1', 15),
    ('testRef2', 16);

INSERT INTO account_save_info
    (`aid`, `id`, `multiple_index`, `content`, `code`, `visibility`)
VALUES
    ('root', 1, 0, '13812345678', 0, 0),
    ('root', 1, 1, '13800000000', 0, 0),
    ('root', 7, 0, '', 0, 0),
    ('root', 8, 0, 'XX社区', 0, 0),
    ('root', 9, 0, '4', 0, 1),
    ('root', 10, 0, '17', 0, 2);

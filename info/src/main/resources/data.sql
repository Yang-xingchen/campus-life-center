-- base data
INSERT INTO info
    (`id`, `name`, `hide`, `type`, `multiple`, `default_visibility`)
VALUES
    (1, '手机号', 0, 0, 1, 0),
    (2, '住宿信息', 0, 1, 0, 0),
    (3, '宿舍社区', 0, 2, 0, 0),
    (4, '宿舍楼层', 0, 0, 0, 1),
    (5, '宿舍房间号', 0, 0, 0, 2),
    (6, '班级信息', 0, 1, 0, 0),
    (7, '年级', 0, 1, 0, 0),
    (8, '专业', 0, 1, 0, 0),
    (9, '班级', 0, 1, 0, 0),
    (10, '基础数据', 1, 1, 0, 2),
    (11, '住宿信息', 1, 1, 0, 2);

INSERT INTO info_text
    (`id`, `type`, `sample`)
VALUES
    (1, 1, '12345678910'),
    (4, 1, '4'),
    (5, 1, '17');

INSERT INTO info_composite
    (`id`, `pid`, `composite_index`)
VALUES
    (3, 2, 0),
    (4, 2, 0),
    (5, 2, 0),
    (7, 6, 0),
    (8, 6, 1),
    (9, 6, 2),
    (1, 10, 0),
    (2, 11, 0);

INSERT INTO info_radio
    (`id`, `text`)
VALUES
    (3, 'XX社区'),
    (3, 'YY社区'),
    (3, 'ZZ社区'),
    (7, '16级'),
    (7, '17级'),
    (7, '18级'),
    (7, '19级'),
    (7, '20级'),
    (8, 'XX专业'),
    (8, 'YY专业'),
    (9, '1班'),
    (9, '2班'),
    (9, '3班'),
    (9, '4班');

-- test data
INSERT INTO account_submit
    (`root`, `id`, `multiple_index`, `aid`, `text`)
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
    (`aid`, `id`, `multiple_index`, `text`, `code`, `visibility`)
VALUES
    ('root', 1, 0, '13812345678', 0, 0),
    ('root', 1, 1, '13800000000', 0, 0),
    ('root', 2, 0, '', 0, 0),
    ('root', 3, 0, 'XX社区', 0, 0),
    ('root', 4, 0, '4', 0, 1),
    ('root', 5, 0, '17', 0, 2);

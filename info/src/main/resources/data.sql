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
    (3, 'YY社区'),
    (3, 'ZZ社区');

INSERT INTO info_list
    (`source`, `id`, `list_order`)
VALUES
    ('testRef1', 1, 0),
    ('testRef2', 2, 0);

INSERT INTO account_info
    (`aid`, `id`, `text`, `code`, `visibility`)
VALUES
    ('root', 1, '13812345678', 0, 0),
    ('root', 2, '', 0, 0),
    ('root', 3, 'XX社区', 0, 0),
    ('root', 4, '4', 0, 1),
    ('root', 5, '17', 0, 2);

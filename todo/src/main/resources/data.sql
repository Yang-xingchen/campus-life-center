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

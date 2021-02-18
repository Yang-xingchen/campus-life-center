INSERT INTO todo
    (`id`, `content`)
VALUES
    (1, 'java'),
    (2, 'docker'),
    (3, 'nodejs'),
    (4, 'user-center'),
    (5, 'notice'),
    (6, 'todo'),
    (7, 'info'),
    (8, 'comment');

INSERT INTO ref_todo
    (`ref`, `id`, `type`)
VALUES
    (1, 'testRef1', 1),
    (2, 'testRef1', 1),
    (3, 'testRef1', 1),
    (4, 'testRef2', 1),
    (5, 'testRef2', 1),
    (6, 'testRef2', 1),
    (7, 'testRef2', 1),
    (8, 'testRef2', 1);

INSERT INTO account_todo
    (`id`, `aid`, `finish`, `top`, `add_list`)
VALUES
    (1, 'root', 1, 0, 0),
    (2, 'root', 1, 0, 1),
    (3, 'root', 1, 0, 1),
    (4, 'root', 1, 0, 0),
    (5, 'root', 1, 0, 1),
    (6, 'root', 1, 0, 0),
    (7, 'root', 0, 1, 1),
    (8, 'root', 0, 0, 1);

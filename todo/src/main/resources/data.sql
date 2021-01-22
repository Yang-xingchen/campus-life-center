INSERT INTO todo
    (`id`, `ref`, `title`)
VALUES
    (1, 'testRef1', 'java'),
    (2, 'testRef1', 'docker'),
    (3, 'testRef1', 'nodejs'),
    (4, 'testRef2', 'user-center'),
    (5, 'testRef2', 'notice'),
    (6, 'testRef2', 'todo'),
    (7, 'testRef2', 'info'),
    (8, 'testRef2', 'comment');

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

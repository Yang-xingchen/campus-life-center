INSERT INTO comment
    (`id`, `parent`, `aid`, `publish_time`, `content`)
VALUES
    (1, NULL, 'root', NOW(), '测试评论1'),
    (2, 1, 'root', NOW(), '补充测试评论1'),
    (3, NULL, 'root', NOW(), '测试评论2');

INSERT INTO ref_comment
    (`id`, `ref`)
VALUES
    (1, 'testRef3'),
    (3, 'testRef3');
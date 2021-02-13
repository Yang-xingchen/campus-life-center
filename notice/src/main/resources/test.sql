INSERT INTO notice
    (`id`,`creator`, `organization`, `publish_status`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`,
        `create_time`, `start_time`, `end_time`,
        `ref`)
VALUES
    (1, "root", 1, 4, 0, 1, 0, 1,
        "环境配置", "通知管理系统环境配置", 0,
        NOW(), NULL, NULL,
        "testRef1"),
    (2, "root", 1, 4, 0, 2, 1, 1,
        "模块建立", "# 各*模块*的建立", 1,
        NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL,
        "testRef2"),
    (3, "root", 1, 4, 0, 5, 2, 2,
        "代码编写", "<p style='color: red'>代码</p>", 2,
        NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY),
        "testRef3"),
    (4, "root", 2, 4, 0, 1, 0, 1,
        "数据收集", "收集测试数据", 0,
        NOW(), NULL, NULL,
        "testRef4"),
    (5, "root", 6, 4, 0, 3, 1, 1,
        "卫检", "XX社区卫生检查", 0,
        NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL,
        "testRef5");

INSERT INTO notice_info
    (`nid`, `ref`)
VALUES
    (4, 'testRef1'),
    (4, 'testRef2');

INSERT INTO notice_tag
    (`nid`, `tag`)
VALUES
    (1, "系统"),
    (2, "系统"),
    (3, "系统"),
    (4, "系统"),
    (4, "基础");

INSERT INTO account_subscribe
    (`aid`, `oid`)
VALUES
    ("user1", 6),
    ("user2", 6),
    ("user3", 6);

INSERT INTO account_notice
    (`nid`, `aid`, `looked`, `top`, `del`, `relative_importance`)
VALUES
    (1, "root", 1, 0, 1, 0),
    (2, "root", 1, 0, 0, 1),
    (3, "root", 1, 1, 0, -1),
    (4, "root", 0, 0, 0, 0),
    (5, "root", 0, 0, 0, -2),
    (5, "user1", 0, 0, 0, -1),
    (5, "user2", 1, 0, 0, -2);
--  (5, "user3", 0, 0, 0, -1)
    
INSERT INTO notice_update_log
    (`id`, `version`, `update_time`,
        `public_type`, `title`, `content`, `content_type`,
         `importance`, `start_time`, `end_time`)
VALUES
    (3, 1, DATE_ADD(NOW(), INTERVAL 1 DAY),
        2, "代码编写", "被更新内容", 1,
        5, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY));

INSERT INTO publish_organization
    (`nid`, `oid`, `dynamic`, `belong`, `subscribe`)
VALUES
    (4, 1, 0, 1, 1),
    (5, 6, 0, 0, 1);

INSERT INTO publish_info
    (`nid`, `iid`, `dynamic`, `text`)
VALUES
    (5, 3, 0, 'XX社区');

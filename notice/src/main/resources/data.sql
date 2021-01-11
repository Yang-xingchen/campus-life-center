-- test data --
INSERT INTO notice
    (`id`,`creator`, `organization`, `visibility`, `importance`, `public_type`, `version`,
        `title`, `content`, `content_type`, `create_time`, `start_time`, `end_time`, `todo_ref`)
VALUES
    (1, "root", 1, 0, 1, 0, 1,
        "环境配置", "通知管理系统环境配置", 1, NOW(), NULL, NULL, "testRef1"),
    (2, "root", 1, 0, 2, 1, 1,
        "模块建立", "各模块的建立", 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, "testRef2"),
    (3, "root", 1, 0, 5, 2, 2,
        "代码编写", "代码", 1, NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY), NULL),
    (4, "root", 2, 0, 1, 0, 1,
        "数据收集", "收集测试数据", 1, NOW(), NULL, NULL, NULL),
    (5, "root", 2, 0, 3, 1, 1,
        "卫检", "XX社区卫生检查", 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), NULL, NULL);

INSERT INTO notice_info
    (`nid`, `name`, `ref`)
VALUES
    (4, "基础数据", "testRef1"),
    (4, "住宿信息", "testRef2");

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
    ("root", 1),
    ("user", 1);

INSERT INTO account_notice
    (`nid`, `aid`, `looked`, `top`, `del`, `relative_importance`)
VALUES
    (1, "root", 1, 0, 1, 0),
    (2, "root", 1, 0, 0, 1),
    (3, "root", 1, 1, 0, -1),
    (4, "root", 0, 0, 0, 0),
    (5, "root", 0, 0, 0, -2);
    
INSERT INTO notice_update_log
    (`nid`, `version`, `update_time`, `title`, `content`, `importance`, `start_time`, `end_time`)
VALUES
    (3, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), "代码编写", "被更新内容", 5, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 17 DAY));

INSERT INTO publish_organization
    (`nid`, `oid`, `dynamic`, `belong`, `subscribe`)
VALUES
    (4, 1, 0, 1, 1),
    (4, 2, 1, 1, 1);

INSERT INTO publish_info
    (`nid`, `iid`, `dynamic`, `text`)
VALUES
    (5, 3, 0, 'XX');

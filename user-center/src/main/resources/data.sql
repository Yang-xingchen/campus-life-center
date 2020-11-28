INSERT INTO account
    (`sign_id`, `name`, `password`, `gender`, `create_data`, `security_key`)
VALUES ("root", "root", "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa", 1, now(), "$2a$10$DK7I87Iq5INTfLggdnxHk.w9Vj6vJPwXeOVgOFAo7qtkfLqLmhsUa");

INSERT INTO organization
    (`creator`, `name`, `visibility`, `create_data`)
VALUES ("root", "root", 0, now());

INSERT INTO account_organization
    (`aid`, `oid`, `role`)
VALUES ("root", 1, 0);
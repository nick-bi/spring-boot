create table t_test
(
    id             bigint auto_increment comment '菜单ID'
        primary key,
    name           varchar(36)             not null comment '菜单名称'
)comment 'test';


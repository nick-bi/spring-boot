create table sys_menu
(
    id             bigint auto_increment comment '菜单ID'
        primary key,
    menu           varchar(36)             not null comment '菜单名称',
    is_frame       tinyint      default 0  not null comment '是否外链',
    component      varchar(100)            null comment '组件',
    component_name varchar(100)            null comment '组件名称',
    path           varchar(100) default '' null comment '请求地址',
    redirect       varchar(100)            null comment '重定向',
    pid            bigint                  not null comment '父级id',
    type           int(1)                  not null comment '类型:1目录,2菜单,3按钮',
    visible        tinyint      default 0  not null comment '菜单状态:显示,隐藏',
    permission     varchar(100) default '' null comment '权限标识',
    cache          tinyint      default 0  null comment '缓存',
    icon           varchar(100) default '' null comment '菜单图标',
    sort           int(4)                  not null comment '排序',
    create_by      bigint                  not null comment '创建者',
    create_time    datetime                not null comment '创建时间',
    remark         varchar(64)  default '' null comment '备注',
    enabled        tinyint      default 1  null,
    update_by      bigint                  null,
    update_time    datetime                null,
    constraint uniq_menu
        unique (menu)
)comment '菜单权限表';


create table department
(
    DeptID       int auto_increment
        primary key,
    DeptName     varchar(45) not null,
    Contact      varchar(45) not null,
    ContactPhone varchar(45) not null,
    Email        varchar(45) not null,
    Address      text        not null,
    Property     varchar(45) null
);

create table bookinfo
(
    BookID      int auto_increment
        primary key,
    BookName    varchar(255)                 not null,
    PublishTime date                         null,
    Author      varchar(255)                 not null,
    Publisher   varchar(255)                 null,
    Category    varchar(255)   default '综合'  not null,
    Pages       int                          not null,
    Price       decimal(10, 2) default 20.00 not null,
    PicturePath varchar(255)                 null,
    BookStatus  tinyint        default 0     null,
    DeptID      int            default 1     null,
    constraint bookinfo_department_DeptID_fk
        foreign key (DeptID) references department (DeptID)
);

create table bookflow
(
    FlowID     int auto_increment
        primary key,
    BookID     int           not null comment '图书ID',
    OwnerUnit  int           not null comment '所属单位ID',
    FlowToUnit int           not null comment '流入单位ID',
    FlowTime   datetime      not null,
    ReturnTime datetime      null,
    Reason     text          not null,
    Applicant  varchar(255)  not null,
    Contact    varchar(255)  not null,
    Remarks    text          null,
    flowStatus int default 0 not null,
    constraint bookflow_department_DeptID_fk
        foreign key (FlowToUnit) references department (DeptID),
    constraint bookflow_department_DeptID_fk_2
        foreign key (OwnerUnit) references department (DeptID),
    constraint fk_Book
        foreign key (BookID) references bookinfo (BookID)
);

create index fk_Book_idx
    on bookflow (BookID);

create table role
(
    RoleID     int auto_increment
        primary key,
    RoleName   varchar(45)                         not null,
    CreateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    RoleStatus tinyint(1)                          not null,
    Remarks    text                                null
);

create table user
(
    UserID        int auto_increment
        primary key,
    Username      varchar(45)                           not null,
    Password      varchar(45)                           not null,
    RealName      varchar(45)                           null,
    ContactPhone  varchar(45)                           null,
    Email         varchar(45)                           not null,
    Address       text                                  null,
    WorkID        varchar(45) default '0'               null,
    IsRegistered  tinyint(1)  default 0                 null,
    CreateTime    timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    Gender        char                                  null,
    AvatarPath    varchar(255)                          null,
    AccountStatus tinyint(1)  default 1                 null,
    LastLoginTime timestamp                             null,
    DeptID        int         default 1                 null,
    isDeleted     int         default 0                 not null
);

create table bookborrow
(
    BorrowID     int auto_increment
        primary key,
    BookID       int           not null,
    UserID       int           not null,
    BorrowTime   datetime      not null,
    ReturnTime   datetime      null,
    Reason       text          not null,
    Contact      varchar(255)  not null,
    Remarks      text          null,
    BorrowStatus int default 0 null,
    constraint fk_Account1
        foreign key (UserID) references user (UserID),
    constraint fk_Book1
        foreign key (BookID) references bookinfo (BookID)
);

create index fk_Account_idx
    on bookborrow (UserID);

create index fk_Book_idx
    on bookborrow (BookID);

create table userrole
(
    UserID int not null,
    RoleId int not null,
    primary key (UserID, RoleId),
    constraint userrole_role_RoleID_fk
        foreign key (RoleId) references role (RoleID),
    constraint userrole_user_UserID_fk
        foreign key (UserID) references user (UserID)
);

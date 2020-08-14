create table sys_user(
                         id int auto_increment primary key,
                         fullname varchar(64),
                         username varchar(32) not null,
                         password_hash varchar(128) not null,
                         salt varchar(128) not null,
                         status varchar(10),
                         data_created DATETIME ,
                         last_updated DATETIME
);

create table sys_role(
                         id int auto_increment primary key,
                         role_name varchar(32) not null,
                         data_created DATETIME ,
                         last_updated DATETIME
);

create table sys_permission(
                               id int auto_increment primary key,
                               permission_name varchar(64) not null,
                               permission varchar(64) not null,
                               data_created DATETIME ,
                               last_updated DATETIME
);

create table sys_role_permission(
                                    id int auto_increment primary key,
                                    role_id int not null,
                                    permission_id int not null,
                                    data_created DATETIME ,
                                    last_updated DATETIME
);

create table sys_user_role(
                              id int auto_increment primary key,
                              user_id int not null,
                              role_id int not null,
                              data_created DATETIME ,
                              last_updated DATETIME
);
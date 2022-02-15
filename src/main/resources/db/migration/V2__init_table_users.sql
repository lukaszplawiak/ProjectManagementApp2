create table users
(
    id         BIGINT primary key auto_increment,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    email      varchar(100) not null unique,
    password   varchar(100) not null,
    created_on DATETIME,
    updated_on DATETIME,
    enabled    boolean
)
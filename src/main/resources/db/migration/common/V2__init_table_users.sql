create table users
(
    id         BIGSERIAL primary key,
    first_name varchar(50)  not null,
    last_name  varchar(50)  not null,
    email      varchar(100) not null unique,
    password   varchar(100) not null,
    created_on timestamp,
    updated_on timestamp,
    enabled    boolean
)
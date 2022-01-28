create table users
(
    id BIGINT(20) primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    created_on DATETIME not null,
    updated_on DATETIME null
);
drop table if exists roles;
create table roles
(
    id BIGINT(20) primary key auto_increment,
    name varchar(50) not null
)
drop table if exists users_roles;
create table users_roles
(
    user_id BIGINT(20) not null,
    roles_id BIGINT(20) not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users(id),
    foreign key (roles_id) references roles(id)
)
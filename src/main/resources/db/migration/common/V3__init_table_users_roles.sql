create table users_roles
(
    user_id  BIGINT not null,
    roles_id BIGINT not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users (id),
    foreign key (roles_id) references roles (id)
)
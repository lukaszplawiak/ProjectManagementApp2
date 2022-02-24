create table users_roles
(
    user_id  BIGSERIAL not null,
    roles_id BIGSERIAL not null,
    primary key (user_id, roles_id),
    foreign key (user_id) references users (id),
    foreign key (roles_id) references roles (id)
)
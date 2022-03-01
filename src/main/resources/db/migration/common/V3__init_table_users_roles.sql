create table users_roles
(
    id       BIGSERIAL primary key,
    user_id  BIGINT not null,
    roles_id BIGINT not null,
    foreign key (user_id) references users (id),
    foreign key (roles_id) references roles (id)
)
create table tasks
(
    id         BIGSERIAL primary key,
    name       varchar(50)  not null,
    comment    varchar(255) not null,
    deadline   DATE         not null,
    done       boolean,
    created_on timestamp,
    updated_on timestamp,
    project_id BIGSERIAL not null,
    user_id    BIGSERIAL not null,
    foreign key (project_id) references projects (id),
    foreign key (user_id) references users (id)
)
create table tasks
(
    id         BIGINT primary key auto_increment,
    name       varchar(50)  not null,
    comment    varchar(255) not null,
    deadline   DATE         not null,
    done       bit,
    created_on DATETIME,
    updated_on DATETIME,
    project_id BIGINT not null,
    user_id    BIGINT not null,
    foreign key (project_id) references projects (id),
    foreign key (user_id) references users (id)
)
create table tasks
(
    id         BIGINT(20) primary key auto_increment,
    name       varchar(50) not null,
    comment    varchar(255),
    deadline   DATE        not null,
    done       bit,
    created_on DATETIME    not null,
    updated_on DATETIME,
    project_id BIGINT(20)  not null,
    user_id    BIGINT(20)  not null,
    foreign key (project_id) references projects (id),
    foreign key (user_id) references users (id)
)
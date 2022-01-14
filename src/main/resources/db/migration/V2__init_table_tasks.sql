drop table if exists tasks;
create table tasks
(
    id BIGINT primary key auto_increment,
    name varchar(50) not null,
    comment varchar(255),
    deadline DATETIME null,
    done bit,
    created_on DATETIME null,
    updated_on DATETIME null,
    project_id BIGINT not null,
    foreign key (project_id) references projects(id)
)
drop table if exists projects;
create table projects
(
    id BIGINT primary key auto_increment,
    title varchar(50) not null,
    description varchar(255),
    deadline DATE not null,
    done bit,
    created_on DATETIME null,
    updated_on DATETIME null
)

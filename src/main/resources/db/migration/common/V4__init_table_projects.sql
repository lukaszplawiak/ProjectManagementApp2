create table projects
(
    id          BIGSERIAL primary key,
    title       varchar(50)  not null,
    description varchar(255) not null,
    deadline    DATE         not null,
    done        boolean,
    created_on  timestamp,
    updated_on  timestamp,
    user_id     BIGINT       not null,
    foreign key (user_id) references users (id)
)
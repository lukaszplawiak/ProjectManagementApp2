create table projects
(
    id          BIGINT primary key auto_increment,
    title       varchar(50)  not null,
    description varchar(255) not null,
    deadline    DATE         not null,
    done        bit,
    created_on  DATETIME     not null,
    updated_on  DATETIME,
    user_id     BIGINT not null,
    foreign key (user_id) references users (id)
)
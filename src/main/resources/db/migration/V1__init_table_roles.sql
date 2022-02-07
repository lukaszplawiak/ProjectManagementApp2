create table roles
(
    id   BIGINT(20) primary key auto_increment,
    name varchar(50) not null
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MANAGER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_SUPER_ADMIN');
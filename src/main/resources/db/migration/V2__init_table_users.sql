drop table if exists users;
create table users
(
    id BIGINT(20) primary key auto_increment,
    first_name varchar(50) not null CHECK (first_name <> ''),
    last_name varchar(50) not null CHECK (last_name <> ''),
    email varchar(255) not null unique CHECK (email <> ''),
    password varchar(255) not null CHECK (password <> ''),
    created_on DATETIME not null,
    updated_on DATETIME null
);
-- DROP TRIGGER IF EXISTS `user_before_insert`;
--
-- DELIMITER $$
--
-- CREATE TRIGGER `user_before_insert` BEFORE INSERT ON `users`
--     FOR ROWS first_name, last_name, email, password
-- BEGIN
--     IF NEW.`swid` IS NULL OR NEW.`swid` = '' THEN
--         SET NEW.`swid` = CONCAT('{', uuid(), '}');
-- END IF;
-- END$$

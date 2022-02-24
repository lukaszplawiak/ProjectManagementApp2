insert into ROLES (NAME) VALUES ('ROLE_USER');
insert into ROLES (NAME) VALUES ('ROLE_MANAGER');
insert into ROLES (NAME) VALUES ('ROLE_ADMIN');
insert into ROLES (NAME) VALUES ('ROLE_SUPER_ADMIN');

insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Lukasz', 'Plawiak', 'lukpla@gmail.com', '$2a$10$hwnVtSaZbd4CKbPQlkzXoOJ/uzcxbORE3J9XC3m/rYclNkvXCOzui', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('John', 'Smith', 'johnsmith@gmail.com', '$2a$10$CPFHGy0Ra8s4y.xHB/9J6udWCr47GLWbcPHiXsDUe1w5CpvMAA6ra', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Jan', 'Kowalski', 'jankowalski@gmail.com', '$2a$10$QA2lZxUCd7kc5hZsQtFC4OCm2HvrzFQ4xxxaIHBf5aOu3rEntCp9m', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Mickey', 'Mouse', 'mickeymouse@gmail.com', '$2a$10$m8wpHAu5e7bcPhcbfEPaHuYmnQCTx0QULmWdNNFVIbV1rFb4JZ5Z2', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Geralt', 'Rivia', 'geraltrivi@gmail.com', '$2a$10$UseEyYEdNZDTLhkmAVaPueR7CPSWLorI8bU24yfiybui7BZ5qPP2G', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Mona', 'Liza', 'monaliza@gmail.com', '$2a$10$0c.3DjUrHcgvwGd/Hym4tuRsj.SgMtpj1c7tTgYkGKLObtOeYAk32', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Mona2', 'Liza2', 'monaliza2@gmail.com', '$2a$10$0c.3DjUrHcgvwGd/Hym4tuRsj.SgMtpj1c7tTgYkGKLObtOeYAk32', '2022-02-10 09:37:00.000000', null, null);
insert into USERS (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CREATED_ON, UPDATED_ON, ENABLED) VALUES ('Mona2', 'Liza2', 'monaliza3@gmail.com', '$2a$10$0c.3DjUrHcgvwGd/Hym4tuRsj.SgMtpj1c7tTgYkGKLObtOeYAk32', '2022-02-10 09:37:00.000000', null, null);

insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 3 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 4 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 2, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 2, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 3, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 4, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 5, 3 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 6, 4 );
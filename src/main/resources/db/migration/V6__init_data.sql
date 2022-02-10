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

insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 3 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 1, 4 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 2, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 3, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 4, 3 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 5, 1 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 5, 2 );
insert into USERS_ROLES (USER_ID, ROLES_ID) VALUES ( 6, 4 );

insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID) VALUES ('SOLID learn', 'Learn all 5 solid principles!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 1);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID) VALUES ('KISS and DRY', 'Remember what is all about!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 1);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID) VALUES ('TEST code', 'Always writes tests and run tests before commit!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 2);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID) VALUES ('SECURE yourself', 'Security is most important non business features!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 2);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID) VALUES ('CLIENT first', 'Focus on client needs, not coders wants!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 5);

insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 1', 'Task 1 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 2', 'Task 2 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 3', 'Task 3 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 1', 'Task 1 of project 2', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 2, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 2', 'Task 2 of project 2', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 2, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 1', 'Task 1 of project 3', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 3, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 2', 'Task 2 of project 3', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 3, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 3', 'Task 3 of project 3', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 3, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 1', 'Task 1 of project 4', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 4, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 2', 'Task 2 of project 4', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 4, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 1', 'Task 1 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID) VALUES ('Task 2', 'Task 2 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 2);
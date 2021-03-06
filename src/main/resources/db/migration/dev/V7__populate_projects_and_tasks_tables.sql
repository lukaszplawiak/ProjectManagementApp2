insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('SOLID learn', 'Learn all 5 solid principles!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 1);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('KISS and DRY', 'Remember what is all about!', '2022-02-15', false, '2022-02-10 09:53:00.000000', null, 1);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('TEST code', 'Always writes tests and run tests before commit!', '2022-02-15', false,
        '2022-02-10 09:53:00.000000', null, 4);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('SECURE yourself', 'Security is most important non business features!', '2022-02-15', false,
        '2022-02-10 09:53:00.000000', null, 2);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('CLIENT first', 'Focus on client needs, not coders wants!', '2022-02-15', false, '2022-02-10 09:53:00.000000',
        null, 5);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('CLIENT first2', 'Focus on client needs, not coders wants!2', '2022-02-15', false, '2022-02-10 09:53:00.000000',
        null, 4);
insert into PROJECTS (TITLE, DESCRIPTION, DEADLINE, DONE, CREATED_ON, UPDATED_ON, USER_ID)
VALUES ('CLIENT first3', 'Focus on client needs, not coders wants!3', '2022-02-15', true, '2022-02-10 09:53:00.000000',
        null, 4);

insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 1', 'Task 1 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 2', 'Task 2 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 3', 'Task 3 of project 1', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 1, 4);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 1', 'Task 1 of project 2', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 2, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 2', 'Task 2 of project 2', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 2, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 1', 'Task 1 of project 3', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 3, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 2', 'Task 2 of project 3', '2022-03-15', true, '2022-02-10 10:01:00.000000', null, 3, 1);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 3', 'Task 3 of project 3', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 3, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 1', 'Task 1 of project 4', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 4, 3);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 2', 'Task 2 of project 4', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 4, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 1', 'Task 1 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 5);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 2', 'Task 2 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 2);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 3', 'Task 3 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 3);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 4', 'Task 4 of project 5', '2022-03-15', false, '2022-02-10 10:01:00.000000', null, 5, 3);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 5', 'Task 5 of project 5', '2022-03-15', true, '2022-02-10 10:01:00.000000', null, 2, 3);
insert into TASKS (NAME, COMMENT, DEADLINE, DONE, CREATED_ON, UPDATED_ON, PROJECT_ID, USER_ID)
VALUES ('Task 6', 'Task 6 of project 5', '2022-03-15', true, '2022-02-10 10:01:00.000000', null, 5, 3);
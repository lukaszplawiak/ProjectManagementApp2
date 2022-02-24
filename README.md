# ProjectManagementApp2
Backend of web service with REST architecture, secure with JWT. 


## Project's aim
The main goals of this project were to practice the acquired knowledge in the field of java, spring boot and sql. In addition, I wrote this project in order to include it as a demonstration project in my resume.


## General info
* The app is a “todo-app” style with CRUD extended features and PDF report generator feature, secure with JWT.
* Users with appropriate permissions can add new users to the web service.
* Depending on the assigned role, the user can create, edit and delete a project. 
* Then user can create a task assigned to the project. Tasks can also be edited, deleted and toggled.
* After completing all tasks assigned to the project, the project automatically changes its state to the finished one.
* Users with appropriate permissions can generate summary pdf reports.


## Technologies
* Java 11
* Spring (Boot, Security, JPA, WEB)
* JWT
* JUnit
* Mockito
* IText7
<<<<<<< HEAD
* MySql, H2
=======
* PostgreSQL
>>>>>>> 04b53447eb5b918dc98d33813f811017abfe3b4f
* FlyWay
* Maven
* Hibernate
* IntelliJ Idea
* Mac OS


## General app's features:
* Create User
* Create Role, assain role to user
* Create, update, delete Project 
* Create task and assain to a previously created project, update, delete
* Toggle task: change state of task's attribute 'done' to true
* Modification of any project or task can only be done by the user who previously created that project or task
* Project or Task can be created only with date set after current date
* User can only toggle his own taks.
* When all tasks assigned to a given project change state to true,
the state of that project will automatically change to true
* When a project has state set to true, any modification of that project and related tasks is impossible 
* User with appropriate role can generate pdf report


## Details app's features:
With ROLE_USER:
* Task: create, read task by Id, read tasks by done, read tasks by project Id, update, toggle, delete 
* Project: Read project by Id
* Report: none
* User: none

With ROLE_MANAGER:
* Task: create, read task by Id, read tasks by done, read tasks by project Id, update, toggle, delete 
* Project: create, read project by Id, read all projects, read projects by done, update, delete
* Report: generate given pdf report
* User: none

With ROLE_ADMIN:
* Task: read task by Id, read tasks by done, read tasks by project Id
* Project: read project by Id, read all projects, read projects by done
* Report: generate given pdf report
* User: add role to user, read all users, read all roles

With ROLE_SUPER_ADMIN:
* Task: read task by Id, read tasks by done, read tasks by project Id
* Project: read project by Id, read all projects, read projects by done
* Report: generate given pdf report
* User: save user, save role, add role to user, read all users, read all roles


## Start up instruction:
Required to run:
* JDK
<<<<<<< HEAD
=======
* PostgreSQL server
>>>>>>> 04b53447eb5b918dc98d33813f811017abfe3b4f
* IntelliJ
* Postman (for testing api purpose)

To download a project please copy this path: https://github.com/lukaszplawiak/ProjectManagementApp2.git 
* Next please open IntelliJ and go to: File -> New -> Project from Version Control...
* Next please paste link above to "URL: " filed
* Next please choose directory path and click button "Clone"
<<<<<<< HEAD
* After clonning please use shortcut to start a program, mac: control + R or win: Alt + Shift + F10
=======
* After clonning please repository, please sure that you postgreSQL server running
* Next please please connect to the server and and execute following statement:
* 'create database project_management_app2;' - for prod profile
* 'create database project_management_app2local;' - for local profile
* 'create database project_management_app2test;' - for test profile
* Next please back to IntelliJ, in 'application-prod.properties' file please configure 'spring.datasource.username' and 'spring.datasource.password' with the correct datasource for your database
* Same step with 'application-local.properties' and 'application-test.properties'
* Then please select a profile in application.properties file
* Use shortcut to start a program, mac: control + R or win: Alt + Shift + F10
>>>>>>> 04b53447eb5b918dc98d33813f811017abfe3b4f

To import Postman collection, please open Postman 
* Next go to your Workspace, click "Import" button, click "Upload Files"
* Next find directory from step above and there choose "ProjectManagementApp2.postman_collection2.json"
* Next click "Open" button

With the running application, you can test the api using the Postman collection.
<<<<<<< HEAD
=======

* Please select one of the requests starting with name 'login with ...'
* Next please click 'Send" button
* Next please choose 'Headers' from response then please copy the 'Value' for the key 'Authorization'
* Next please choose one of the requests from the same list as before
* Next please click 'Headers', find 'Authorization' key and paste in 'Value' field previously copied string
* Then please send request

For proper api testing, please check the earlier section "General app's features" and "Details app's features".
>>>>>>> 04b53447eb5b918dc98d33813f811017abfe3b4f


## Status
Ready to use


## Contact
Created by Lukasz Plawiak lukasz.b.plawiak@gmail.com - feel free to contact me!

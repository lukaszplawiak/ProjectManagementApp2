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
* MySql, H2
* FlyWay
* Maven
* Hibernate
* IntelliJ Idea
* Mac OS


## Status
Ready to use


## App's features:
* Create User,
* Create Role, assain role to user
* Create, update, delete Project 
* Create task and assain to a previously created project, update, delete, 
* Toggle task: change state of task's attribute 'done' to true;
* Modification of any project or task can only be done by the user who previously created that project or task.
* User can only toggle his own taks.
* When all tasks assigned to a given project change state to true, 
the state of that project will automatically change to true.
* When a project has state set to true, any modification of that project and related tasks is impossible 
* User with appropriate role can generate pdf report


## Contact
Created by Lukasz Plawiak lukasz.b.plawiak@gmail.com - feel free to contact me!

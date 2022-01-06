# ProjectManagementApp2
Backend for project management application

## General info
A simple application that is an extension of the popular ToDoApp-style application, 
that allows you to create, edit and delete projects, then create tasks assigned 
to a given project, edit, delete and toggle previously created tasks. 
After completing all tasks assigned to the project, the project automatically 
changes its state to the finished one.

## Technologies
* Java 11
* Spring Boot 2.6.2
* Maven
* Hibernate
* FlyWay
* H2 db


## Status
Work in progress...


## App's functions:
* Create Project with fields: title, description, deadline
* Update project
* Delete project
* Create task with fields: name, comment, deadline; 
assigned to a previously created project
* Update task
* Delete task
* Toggle task: change state 'done' to true; 
When all tasks assigned to a given project change state to true, 
the state of the project will automatically change to true.
When a project has state set to true, you cannot assign a new task to it, 
nor edit an existing task.


## Contact
Created by Lukasz Plawiak lukaszplawiakzero1@gmail.com - feel free to contact me!

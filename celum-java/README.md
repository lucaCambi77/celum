
## Task ##

Design and implement web application for management of school courses. A course has following properties:
- (mandatory) Name
- (mandatory) Date
Student should have following properties:
- (mandatory) First name
- (mandatory) Last name
- (mandatory) Email
- (optional) Phone
Administrator of this application sees CRUDs for courses and students and is able to assign/remove students to/from courses.
Technology constraints:
- Application should be split into backend and frontend part
- Spring Boot 2+, Spring Data, MongoDB
- Angular 7+, Angular CLI

Bonus:
- use NgRx
- write, how would the domain model look like in relational database
- up to 5 students can participate in a course. Additionally, a student can take up to 3 different courses

## Notes ##

The application use Embedded MongoDb. As users (students) and courses are in a many to many relation, we save the ObjectId as references.
In test section there are also the entities for the relation model.


## Install and run the application
To use the application you need java 8 installed in your machine. This is a spring boot application with embedded tomcat

There is a default profiles with which you can run the application

To install 

	- mvn clean install

To run  

	- java -jar target/*.jar

[![CircleCI](https://circleci.com/gh/jfspps/SRM-Spring.svg?style=svg)](https://circleci.com/gh/jfspps/SRM-Spring)

# Student Record Management SRM #
SRM (student record management) stores and processes academic data.

This project was initially developed with [NodeJS and MySQL](https://github.com/jfspps/SRM_Node) and then moved across here as a Spring 5 based project. The port is intended to showcase my learning of the Spring framework.

The SQL schema (/dbSchema), admin login (previously NodeJS scripts, /dbscripts) and SRS documents (/docs) have been transferred across from the NodeJS repo for reference.

__After building and running the app, enter `localhost:8080` from any browser to execute SRM.__

__Frameworks and tools: Java 11 and Spring 5__

## Development stages ##

The development of SRM will focus initially on the Parents' Portal, accessible through a web browser. Further effort will then, when required (and probably expected!), also focus on the school-end of the app.

### Parents' portal ###

1. Develop underlying POJOs and map with Hibernate
2. Populate the database with example student data and list data in a browser
3. User authentication and authorisation testing

Use the currently populated tables to verify students results processing the following:

1. Averages by component (homework, test, mock exams, coursework etc.)
2. Mapping to percentage uniform marks (PUM)
3. Letter grade assignment (comparison between two SQL tables)

#### Thymeleaf interface ####

1. Display pages, showing all columns:
   a. Personal data and school admin entry page
   b. Assignment info entry page
   c. Grade threshold entry page
2. Display pages, with options to select specific columns
3. More display pages, showing all columns and then with options (as before), for the processing of students' results.*
		
Group the individual pages together with a uniform theme and flow.

#### Student record and data-entry ####

1. Personal data-entry and verification (check for duplication and NULL)
   a. Students' personal details
   b. Guardians personal details
   c. How SRM responds when either records are updated or deleted

2. Teacher data-entry and verification (check for duplication and NULL)
   a. Teacher work details
   b. Subjects on offer (including those not taken by any student)
   c. How SRM responds when either records are updated or deleted

3. Academic classes data-entry and verification (check for duplication and NULL)
   a. Assigning students and teachers to specific classes
   b. How SRM responds when either records are updated or deleted

#### Handling of assignment and threshold data entry ####
	
1. Assignment info data-entry and verification (check for duplication and NULL)
   a. Components of all types
   b. Couple assignment info with teachers
   c. How SRM responds when either records are updated or deleted
	
2. Student assignment info (raw scores) upload and verification. How SRM responds when either records are updated or deleted.

#### Handling of processing of students' scores ####

1. Percentage deduction of individual scores and then grade threshold mapping.
2. Averages of percentages by component type
3. Comparison of individual student's percentage to a class average
4. PUM mapping of individual scores
5. Overall, end-of-year scores based on weighted component scores
	
#### Error-checking and feedback ####

1. Entering raw scores > max_raw score
2. Entering unexpected data types: input 'A' instead of MySQL INT
3. Teachers student data of other teachers
4. Not entering any student results for a previous assignment

#### User login page ####

Apply the MySQL statements developed earlier and build a welcome page

#### Microsoft Excel XLSX export #####

Develop Spring MVC interface with JExcel or jxls (for example) to display, on the first worksheet:

+ Student name
+ Student email address
+ Assignment info, raw score, percentage and letter grade
+ Class average

On a second worksheet:

+ Student name
+ Assignment info
+ Difference between student average and class average (with colour code)
+ Standard deviation

On the third worksheet:

+ Student name
+ Cumulative average by component
+ Overall cumulative average and grade

Develop the worksheet further so that the user can select which elements to print and on which worksheet. See how the font size and number of characters influences the cell size...

#### Other database-independent settings to save on SRM-server ####

Save above settings to a separate table in SRM? Need to also store:

+ Academic term or semester start dates, including the start date of the following year (SRM will default to one year after the first start date if not entered)
+ Logging of SQL access

Most of the main objectives would be fulfilled at this stage. Future ideas include:

+ Display trends graphically
+ Parents/Guardians web portal
+ Automated student report generator

#### MySQL db notes ####

Due to MySQL foreign key constraints, the following tables should be populated in the order:

1. Students
2. (In no particular order) Guardians, Subjects and Teachers
3. (In no particular order) Guardians_addresses, Subject_Subjects, Form_groups, Subject_Teachers_groups and Student_reports
4. Academic_classes

When the pastoral and academic plans are entered, teachers can then begin entering assignments related data:

4. Assignments_info
5. (In no particular order) Assignments_teacher_info, Grade_thresholds, Letter_grade_chars and  Student_Assignments
6. Grading Groups

## Running Docker containers ##

Docker is not a requirement to run SRM with MySQL but is presented here for those who want to run MySQL from a
 container instead of from a local installation.

### Installing Docker ###

Installing Docker on Linux Mint 20 (Ulyana) is done by running the following commands either individually or as a shell script.

```shell script
sudo apt-get update
     
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
     
curl -fsSL https://download.docker.com/linux/ubu... | sudo apt-key add -
     
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(. /etc/os-release; echo "$UBUNTU_CODENAME") stable"
     
sudo apt-get update
     
sudo apt-get -y  install docker-ce
```

To persist data to a local directory, new directories for the MySQL container will need to be created. In my example
, I used `/home/james/Dev/mySQLContainer`. Docker containers to Docker images are very much like Java classes are to
 objects. Docker containers are run with the `run` command and then listed in `docker ps`. If the image is not stored
  on the local disk then it will be automatically downloaded from the Docker Hub.
  
If you already have MySQL community installed, then you will need to use a localhost port other than 3306. In this example, I used localhost 3307 to map to the MySQL container's 3306.
   
Both shared volume -v and port -p parameters follow the sequence: localVolume:containerVolume or localPort:containerPort.
```shell script
docker run --name mySQLContainer -v /home/james/Dev/mySQLContainer:/var/lib/mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3307:3306 -d mysql
```
      
This also sets the environment variable (-e) with null password. The parameter -d sets mysql to run as a daemon.
   
MySQL 8 requires password protecting. Run `docker exec -it <container_id> bash` (the container id is found by running `docker ps`) and after execute this `mysql -u root`. At the MySQL console, run:

```shell script
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';use mysql;UPDATE mysql.user SET host='%' WHERE user='root';
```
   
Type Ctrl+D and restart mysql with the following command: `mysqld restart` (credits to https://stackoverflow.com/a/53063112). One can then access MySQL through MySQL Workbench, port 3307 to access the container’s (in future, clients) MySQL daemon, independent of any local copy of MySQL.
   
__Running MySQL after setup__

Run the container with `docker exec -it <container_id> bash` and then enter `mysql -u root`.
   
### Initialising non-root users ###

Setup two databases `SRM_dev` and `SRM_prod`, one for development and one for production, each with their own user
 account (Database Manipulation Language access only). The SQL script is located [here](./srm_spring_data/src/main/scripts).

For demo purposes, the users `SRM_dev_user` and `SRM_prod_user` both have the password: `admin`.

Once established, set up shortcuts to MySQL workbench (remember the port is 3307 not 3306) or via the console with
 `mysql -u SRM_dev_user -p` and enter `admin` when asked. Same applies for the production version, `SRM_prod`.
 
### Building the schema ###
 
A copy of the MySQL schema is found [here](./srm_spring_data/src/main/scripts). If a new schema is needed then uncomment lines 15-22 in application-dev.yml and ensure that the 'dev' profile is selected (through the IDE or application.yml) and run SRM.

The printed script's lines may need terminating with a semi-colon, and then add a first line `use SRM_dev;` or `use SRM_prod;`. Place a copy of the script in MySQL workbench (under either or both dev and/or prod DBs, as desired) and execute. It is assumed the current dev or prod DB will be empty.

### Running with MySQL ###

Ensure that 'dev' and 'springDataJPA' are selected from the application.yml file (and the DB initialisation is commented out) and then run as usual.

### Running without MySQL ###

To initialise via h2 database, set the profile to 'map' instead of 'springDataJPA', and remove profiles 'dev'/'prod'
# Student Record Management SRM #
SRM (student record management) stores and processes academic data.

This project was initally developed with [NodeJS and MySQL](https://github.com/jfspps/SRM_Node) and then moved across here as a Spring 5 based project. The port is intended to showcase my learning of the Spring framework.

The SQL schema (/dbSchema), admin login (previously NodeJS scripts, /dbscripts) and SRS documents (/docs) have been transferred across from the NodeJS repo for reference.

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

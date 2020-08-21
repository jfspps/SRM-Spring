# SRM development stages #

The development of SRM will focus initially on the NodeJS Parents' Portal (details [here](https://github.com/jfspps/SRM_Node)).

## Models and CRUD services ##
1. Design the models, grouped under [academic](./srm_spring_data/src/main/java/com/srm/model/academic/) and [people](./srm_spring_data/src/main/java/com/srm/model/people/). All models aer serialisable and derived from [BaseEntity](./com/srm/model/).
2. Implement the [HashMap](./srm_spring_data/src/main/java/com/srm/services/map/) and [Spring Data JPA](./srm_spring_data/src/main/java/com/srm/services/springDataJPA/) services. Provide the interfaces (located in [people](./srm_spring_data/src/main/java/com/srm/services/peopleServices/) and [academic](./srm_spring_data/src/main/java/com/srm/services/academicServices/)) which define both services. All interfaces are derived from [CrudService](./com/srm/services/).

## Controllers and viewers ##
1. Implement [controllers](./srm_spring_web/src/main/java/com/srm/controllers/) for people and subject related models.
2. Build Thymeleaf [templates](./srm_spring_web/src/main/resources/templates/) with create, read and update capabilities.
3. Populate database with [bootstrap class](./srm_spring_web/src/main/java/com/srm/bootstrap/), initialising POJOs for people and subject related models.

## MySQL 8 data persistence ##
1. Update Profiles as 'dev' and 'prod' for development and production environments.
2. Initialise Docker MySQL container and connect to Spring SRM (non-root users and DB schema is [here](./srm_spring_data/src/main/scripts/))

# Future work #

### Delete in CRUD ###

+ Implement the deletion of records

### Spring Security ###
+ Implement user authentication and authorisation

### Parents' portal ###
+ Build the UI which enables parents to view their own personal data (NodeJS or Spring, tbc)

## Extending SRM ##

Continue to extend the functionality of SRM by adding the following as controllers and templates

### Student academic records and data-entry ###

1. Teacher data-entry and verification (check for duplication and NULL)
   a. Assignment details
   b. Subjects on offer (including those not taken by any student)

2. Academic classes data-entry and verification (check for duplication and NULL)
   - Assigning students and teachers to specific classes; building form and subject class lists

### Handling of assignment and threshold data entry ###
	
1. Assignment info data-entry and verification (check for duplication and NULL)
   a. Components of all types
   b. Couple assignment info with teachers
	
2. Student assignment info (raw scores) upload and verification.

### Handling of processing of students' scores ###

1. Percentage deduction of individual scores and then grade threshold mapping.
2. Averages of percentages by component type
3. Comparison of individual student's percentage to a class average
4. PUM mapping of individual scores
5. Overall, end-of-year scores based on weighted component scores
	
### Error-checking and feedback ###

1. Entering raw scores > max_raw score
2. Entering unexpected data types: input 'A' instead of MySQL INT
3. Teachers student data of other teachers
4. Not entering any student results for a previous assignment

### Microsoft Excel XLSX export ####

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

## Other database-independent settings to save on SRM-server ##

Save above settings to a separate table in SRM? Need to also store:

+ Academic term or semester start dates, including the start date of the following year (SRM will default to one year after the first start date if not entered)
+ Logging of SQL access

Most of the main objectives would be fulfilled at this stage. Future ideas include:

+ Display trends graphically
+ Parents/Guardians web portal
+ Automated student report generator

[![CircleCI](https://circleci.com/gh/jfspps/SRM-Spring.svg?style=svg)](https://circleci.com/gh/jfspps/SRM-Spring)

# Student Record Management SRM #
SRM (student record management) stores and processes academic data.

This project was initially developed with [NodeJS and MySQL](https://github.com/jfspps/SRM_Node) and then moved across here as a Spring 5 based project. The port is intended to showcase my learning of the Spring framework.

The SQL schema (/dbSchema), admin login (previously NodeJS scripts, /dbscripts) and SRS documents (/docs) have been transferred across from the NodeJS repo for reference.

__After building and running the app, enter `localhost:8080` from any browser to execute SRM.__

__Frameworks and tools:__ Java 11 and Spring 5 (the development outline is [here](/DevelopmentOutline.md).)

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

### Setting up the Docker container volume and port parameters ###

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
   
### Running the Docker container and setting up MySQL ###

MySQL 8 requires password protecting. Run `docker exec -it <container_id> bash` (the container id is found by running `docker ps`) and after execute this `mysql -u root`. At the MySQL console, run:

```shell script
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';use mysql;UPDATE mysql.user SET host='%' WHERE user='root';
```
   
Type Ctrl+D and restart mysql with the following command: `mysqld restart` (credits to https://stackoverflow.com/a/53063112). One can then access MySQL through MySQL Workbench, port 3307 to access the container’s (in future, clients) MySQL daemon, independent of any local copy of MySQL.
   
### Running MySQL after root setup ###

List all images with `docker ps -a` and then start the container with `docker start containerID`.

Run the bash shell from the container with `docker exec -it <container_id> bash` and then enter `mysql -u root`.
   
### Initialising non-root users ###

Setup two databases `SRM_dev` and `SRM_prod`, one for development and one for production, each with their own user
 account (Database Manipulation Language access only). The SQL script is [here](./srm_spring_data/src/main/scripts).

For demo purposes, the users `SRM_dev_user` and `SRM_prod_user` both have the password: `admin`.

Once established, either set up shortcuts to MySQL workbench (remember the port is 3307 not 3306) or instead logon via the console with
 `mysql -u SRM_dev_user -p` and enter `admin` when asked. Same applies for the production version, `SRM_prod`.
 
### Building the schema ###
 
A copy of the MySQL schema is found [here](./srm_spring_data/src/main/scripts). If a new schema is needed then uncomment lines 15-22 in application-dev.yml and ensure that the 'dev' profile is selected (through the IDE or application.yml) and run SRM.

The printed script's lines may need terminating with a semi-colon, and then add a first line `use SRM_dev;` or `use SRM_prod;`. Place a copy of the script in MySQL workbench (under either or both dev and/or prod DBs, as desired) and execute. It is assumed the current dev or prod DB will be empty.

### Running with MySQL ###

Ensure that 'dev' and 'springDataJPA' are selected from the application.yml file and then run as usual.

### Running without MySQL ###

To initialise via h2 database, set the profile to 'map' instead of 'springDataJPA', and remove profiles 'dev'/'prod'
# connect to mysql and run as root user, then...

#Create Databases
CREATE DATABASE SRM_dev;
CREATE DATABASE SRM_prod;

#Create database service accounts for local MySQL and Docker MySQL
#(the localhost assumes you are running from local copy of MySQL; Docker is run
#from outside the Container with a domain name that isn't localhost, hence the '%'')
CREATE USER 'SRM_dev_user'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'SRM_prod_user'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'SRM_dev_user'@'%' IDENTIFIED BY 'admin';
CREATE USER 'SRM_prod_user'@'%' IDENTIFIED BY 'admin';

#Database grants
GRANT SELECT ON SRM_dev.* to 'SRM_dev_user'@'localhost';
GRANT INSERT ON SRM_dev.* to 'SRM_dev_user'@'localhost';
GRANT DELETE ON SRM_dev.* to 'SRM_dev_user'@'localhost';
GRANT UPDATE ON SRM_dev.* to 'SRM_dev_user'@'localhost';
GRANT SELECT ON SRM_prod.* to 'SRM_prod_user'@'localhost';
GRANT INSERT ON SRM_prod.* to 'SRM_prod_user'@'localhost';
GRANT DELETE ON SRM_prod.* to 'SRM_prod_user'@'localhost';
GRANT UPDATE ON SRM_prod.* to 'SRM_prod_user'@'localhost';
GRANT SELECT ON SRM_dev.* to 'SRM_dev_user'@'%';
GRANT INSERT ON SRM_dev.* to 'SRM_dev_user'@'%';
GRANT DELETE ON SRM_dev.* to 'SRM_dev_user'@'%';
GRANT UPDATE ON SRM_dev.* to 'SRM_dev_user'@'%';
GRANT SELECT ON SRM_prod.* to 'SRM_prod_user'@'%';
GRANT INSERT ON SRM_prod.* to 'SRM_prod_user'@'%';
GRANT DELETE ON SRM_prod.* to 'SRM_prod_user'@'%';
GRANT UPDATE ON SRM_prod.* to 'SRM_prod_user'@'%';
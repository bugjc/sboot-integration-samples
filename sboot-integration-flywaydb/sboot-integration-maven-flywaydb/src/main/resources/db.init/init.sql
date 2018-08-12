

DROP DATABASE IF EXISTS `jc_flyway_test` ;
CREATE DATABASE	`jc_flyway_test` DEFAULT CHARSET utf8;

CREATE USER 'jc_flyway_test'@'localhost' IDENTIFIED BY 'jc_flyway_test';

GRANT ALL PRIVILEGES ON jc_flyway_test.* TO 'jc_flyway_test'@'localhost' WITH GRANT OPTION;

USE jc_flyway_test;

/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - jwt_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jwt_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jwt_test`;

/*Table structure for table `tbs_user` */

DROP TABLE IF EXISTS `tbs_user`;

CREATE TABLE `tbs_user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'userId',
  `firstName` varchar(50) DEFAULT NULL COMMENT '名称',
  `lastName` varchar(50) DEFAULT NULL COMMENT '姓氏',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbs_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

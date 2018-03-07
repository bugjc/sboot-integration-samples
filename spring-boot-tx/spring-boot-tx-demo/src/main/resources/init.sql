
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring-boot-tx1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spring-boot-tx1`;

/*Table structure for table `td_test1` */

DROP TABLE IF EXISTS `td_test1`;

CREATE TABLE `td_test1` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;



CREATE DATABASE /*!32312 IF NOT EXISTS*/`spring-boot-tx2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spring-boot-tx2`;

/*Table structure for table `td_test2` */

DROP TABLE IF EXISTS `td_test2`;

CREATE TABLE `td_test2` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

DROP DATABASE IF EXISTS `sys_admin` ;
CREATE DATABASE	`sys_admin` DEFAULT CHARSET utf8;

CREATE USER 'sys_admin'@'localhost' IDENTIFIED BY 'sys_admin123456';

GRANT ALL PRIVILEGES ON sys_admin.* TO 'sys_admin'@'localhost' WITH GRANT OPTION;

USE sys_admin;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_login`;
CREATE TABLE `sys_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `last_login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_login
-- ----------------------------
BEGIN;
INSERT INTO `sys_login` VALUES (448, 1, '2018-03-17 12:56:26');
INSERT INTO `sys_login` VALUES (449, 1, '2018-03-17 12:56:26');
INSERT INTO `sys_login` VALUES (450, 1, '2018-03-17 12:59:58');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `menu_name` varchar(50) DEFAULT NULL,
  `menu_url` varchar(50) DEFAULT '#',
  `menu_type` enum('2','1') DEFAULT '2' COMMENT '1 -- 系统菜单，2 -- 业务菜单',
  `menu_icon` varchar(50) DEFAULT '#',
  `sort_num` int(11) DEFAULT '1',
  `user_id` int(11) DEFAULT '1' COMMENT '创建这个菜单的用户id',
  `is_del` int(11) DEFAULT '0' COMMENT '1-- 删除状态，0 -- 正常',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '#', '1', 'fa fa-gears', 1, 1, 0, '2017-09-08 16:15:24', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (2, 1, '菜单管理', 'menu/list', '1', '#', 1, 1, 0, '2017-09-12 11:28:09', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'role/list', '1', NULL, 2, 1, 0, '2017-09-07 17:58:52', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (4, 1, '用户管理', 'user/list', '1', '', 3, 1, 0, '2017-09-12 09:44:48', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (5, 0, '业务菜单', '#', '2', 'fa fa-tasks', 2, 1, 1, '2018-01-30 12:02:53', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (6, 5, '随便添加的子菜单', 'page/t4', '2', '', 1, 1, 1, '2018-01-30 12:02:53', '2017-09-07 14:52:41');
INSERT INTO `sys_menu` VALUES (7, 5, 'test', 'behavior/list', '2', '', 1, 1, 1, '2018-01-30 12:02:53', '2018-01-24 10:43:20');
INSERT INTO `sys_menu` VALUES (8, 5, '营销管理', 'give/product/type/list', '2', '', 1, 1, 1, '2018-01-30 12:02:53', '2018-01-26 10:55:57');
INSERT INTO `sys_menu` VALUES (9, 0, '营销管理', '#', '2', 'fa fa-tasks', 3, 1, 1, '2018-03-17 12:59:38', '2018-01-26 10:57:17');
INSERT INTO `sys_menu` VALUES (10, 9, '赠品类型管理', 'give/product/type/list', '2', '', 2, 1, 1, '2018-03-17 12:59:38', '2018-01-26 10:59:09');
INSERT INTO `sys_menu` VALUES (11, 9, '赠品管理', 'give/product/list', '2', '', 3, 1, 1, '2018-03-17 12:59:38', '2018-01-26 20:14:27');
INSERT INTO `sys_menu` VALUES (12, 9, '规则管理', 'rule/list', '2', '', 4, 1, 1, '2018-03-17 12:59:38', '2018-01-29 16:42:15');
INSERT INTO `sys_menu` VALUES (13, 9, '行为管理', 'behavior/list', '2', '', 1, 1, 1, '2018-03-17 12:59:38', '2018-01-30 12:00:18');
INSERT INTO `sys_menu` VALUES (14, 0, '消费管理', '#', '2', 'fa fa-tasks', 1, 1, 1, '2018-03-17 12:59:41', '2018-02-01 16:26:44');
INSERT INTO `sys_menu` VALUES (15, 14, '产品类型管理', 'product/type/list', '2', '', 1, 1, 1, '2018-03-17 12:59:41', '2018-02-01 16:28:55');
INSERT INTO `sys_menu` VALUES (16, 14, '产品管理', 'product/list', '2', '', 2, 1, 1, '2018-03-17 12:59:41', '2018-02-01 16:29:35');
INSERT INTO `sys_menu` VALUES (17, 14, '订单管理', 'order/list', '2', '', 4, 1, 1, '2018-03-17 12:59:41', '2018-02-01 16:29:53');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `role_desc` varchar(255) DEFAULT NULL,
  `rights` varchar(255) DEFAULT '0' COMMENT '最大权限的值',
  `add_qx` varchar(255) DEFAULT '0',
  `del_qx` varchar(255) DEFAULT '0',
  `edit_qx` varchar(255) DEFAULT '0',
  `query_qx` varchar(255) DEFAULT '0',
  `user_id` varchar(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员权限', '261662', '261662', '261662', '261662', '261662', '1', '2018-02-02 19:35:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `nick_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `pic_path` varchar(200) DEFAULT '/images/logo.png',
  `status` enum('unlock','lock') DEFAULT 'unlock',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '7c4a8d09ca3762af61e59520943dc26494f8941b', '/upload/show/user/90481532.png', 'unlock', '2017-08-18 13:57:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (17, 1, 1, '2018-01-24 10:44:32');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

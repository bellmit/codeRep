DROP DATABASE IF EXISTS `freyja_0`;
CREATE DATABASE `freyja_0` ;
USE `freyja_0`;

#
# Source for table t_user_0
#

DROP TABLE IF EXISTS `t_user_0`;
CREATE TABLE `t_user_0` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `open_Id` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table t_user_0
#

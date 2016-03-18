DROP DATABASE IF EXISTS `freyja_1`;
CREATE DATABASE `freyja_1` ;
USE `freyja_1`;

#
# Source for table t_user_0
#

DROP TABLE IF EXISTS `t_user_0`;
CREATE TABLE `t_user_0` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `open_Id` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Dumping data for table t_user_0
#

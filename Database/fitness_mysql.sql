/*
Navicat MySQL Data Transfer

Source Server         : djzhao_mysql
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : fitness_mysql

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-05-10 11:50:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `newsId` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `replyUser` varchar(20) DEFAULT '',
  `commentTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('1', '1', '1', '给自己加油', '', '2017-05-06 12:43:25', '0');
INSERT INTO `comments` VALUES ('2', '2', '1', '嗯嗯，一起加油', 'dj', '2017-05-06 12:43:43', '0');
INSERT INTO `comments` VALUES ('3', '1', '2', 'nice', '', '2017-05-06 16:41:23', '0');
INSERT INTO `comments` VALUES ('4', '1', '2', 'good', 'dj', '2017-05-06 16:42:00', '0');
INSERT INTO `comments` VALUES ('5', '1', '1', '好的，一起来腹肌撕裂', '46', '2017-05-06 16:49:26', '0');
INSERT INTO `comments` VALUES ('6', '1', '1', '今天很开心', '', '2017-05-06 16:52:49', '0');
INSERT INTO `comments` VALUES ('7', '1', '1', '今天运动了吗', '46', '2017-05-06 16:53:09', '0');
INSERT INTO `comments` VALUES ('8', '1', '6', '你好啊', '', '2017-05-06 21:25:18', '0');
INSERT INTO `comments` VALUES ('9', '0', '6', '啥回事', 'dj', '2017-05-06 21:25:35', '0');
INSERT INTO `comments` VALUES ('10', '1', '6', '啥回事', 'dj', '2017-05-06 21:27:17', '0');
INSERT INTO `comments` VALUES ('11', '1', '5', '消灭0回复', '', '2017-05-07 20:33:07', '0');
INSERT INTO `comments` VALUES ('13', '1', '3', '你好啊，坚持就是胜利', '', '2017-05-10 10:13:45', '0');
INSERT INTO `comments` VALUES ('14', '1', '3', '对对对', 'dj', '2017-05-10 10:14:14', '0');

-- ----------------------------
-- Table structure for dailycheck
-- ----------------------------
DROP TABLE IF EXISTS `dailycheck`;
CREATE TABLE `dailycheck` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `checkDate` date NOT NULL,
  `checkTime` time DEFAULT NULL,
  `status` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dailycheck
-- ----------------------------
INSERT INTO `dailycheck` VALUES ('1', '1', '2017-05-01', '22:29:43', '0');
INSERT INTO `dailycheck` VALUES ('2', '1', '2017-05-02', '22:29:46', '0');
INSERT INTO `dailycheck` VALUES ('3', '1', '2017-05-03', '07:31:44', '0');
INSERT INTO `dailycheck` VALUES ('4', '1', '2017-05-04', '19:56:04', '0');
INSERT INTO `dailycheck` VALUES ('5', '1', '2017-05-06', '14:28:58', '0');

-- ----------------------------
-- Table structure for favors
-- ----------------------------
DROP TABLE IF EXISTS `favors`;
CREATE TABLE `favors` (
  `favorId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `newsId` int(11) NOT NULL,
  `favorTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`favorId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favors
-- ----------------------------
INSERT INTO `favors` VALUES ('1', '1', '1', '2017-05-05 16:44:47', '0');
INSERT INTO `favors` VALUES ('2', '2', '1', '2017-05-05 16:44:54', '0');
INSERT INTO `favors` VALUES ('3', '1', '5', '2017-05-06 13:30:07', '0');
INSERT INTO `favors` VALUES ('4', '1', '4', '2017-05-06 15:41:31', '0');
INSERT INTO `favors` VALUES ('5', '0', '6', '2017-05-06 21:25:57', '0');
INSERT INTO `favors` VALUES ('6', '1', '6', '2017-05-06 21:26:47', '0');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `releaseTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '1', '第一天健身', '健身之后感觉很舒服，超赞', 'ac99f6db-80ca-443d-a814-eda47e980650.png', '2017-05-10 08:06:35', '0');
INSERT INTO `news` VALUES ('2', '2', '123', '123', '6a9aa202-90c2-4c36-8224-199d8997cc2b#icon.jpg', '2017-05-10 07:55:17', '0');
INSERT INTO `news` VALUES ('3', '1', '123', '123', 'fb9c2f3f-5e11-4d1e-8476-54f0aa09064a#icon.jpg', '2017-05-10 07:55:05', '0');
INSERT INTO `news` VALUES ('4', '2', '请问', '阿斯顿', '592d1962-7d65-4757-a647-27ba4402401a#icon.jpg', '2017-05-10 07:55:08', '0');
INSERT INTO `news` VALUES ('5', '1', '问问', '二恶', null, '2017-05-10 07:55:15', '0');
INSERT INTO `news` VALUES ('6', '2', '立志一个月，练出马甲线', '在这里定一个小目标，一个月练出马甲线~！一起加油', 'ac99f6db-80ca-443d-a814-eda47e980650.png', '2017-05-10 07:54:00', '0');

-- ----------------------------
-- Table structure for training
-- ----------------------------
DROP TABLE IF EXISTS `training`;
CREATE TABLE `training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `trainTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `duration` int(2) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of training
-- ----------------------------
INSERT INTO `training` VALUES ('1', '1', '2017-05-07 09:01:46', '8', '0');
INSERT INTO `training` VALUES ('2', '1', '2017-05-07 09:01:48', '8', '0');
INSERT INTO `training` VALUES ('3', '1', '2017-05-07 08:43:19', '8', '0');
INSERT INTO `training` VALUES ('4', '1', '2017-05-07 08:56:40', '8', '0');
INSERT INTO `training` VALUES ('5', '1', '2017-05-07 09:01:27', '8', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `sex` varchar(2) NOT NULL,
  `height` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `registerTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'dj', '123456', '女', '170', '65.3', '2017-05-06 21:27:49', '0');
INSERT INTO `user` VALUES ('2', '46', 'mmm', '男', '5', '5', '2017-05-02 16:21:50', '0');

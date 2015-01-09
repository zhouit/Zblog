/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50534
Source Host           : localhost:3306
Source Database       : zblog

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2015-01-09 21:51:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` varchar(19) NOT NULL,
  `name` varchar(25) NOT NULL,
  `leftv` int(11) NOT NULL,
  `rightv` int(11) NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('Mf2DuehP8rWqS8EzyXb', 'Root', '1', '2', '0', '2014-12-18 19:37:58');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(19) NOT NULL,
  `postid` varchar(19) NOT NULL,
  `parent` varchar(19) DEFAULT NULL,
  `creator` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `url` varchar(80) NOT NULL,
  `agent` varchar(80) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `content` varchar(200) NOT NULL,
  `approved` tinyint(4) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_comment_post` (`postid`),
  CONSTRAINT `index_comment_post` FOREIGN KEY (`postid`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `link`
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` varchar(19) NOT NULL,
  `name` varchar(80) NOT NULL,
  `url` varchar(100) NOT NULL,
  `notes` varchar(150) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  `creator` varchar(20) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES ('6ChkuVe0kfLY8ZiDnqZ', 'JavaTalk', 'http://www.zhouhaocheng.cn', null, '1', 'admin', '2015-01-06 17:27:31');

-- ----------------------------
-- Table structure for `options`
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options` (
  `id` varchar(19) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of options
-- ----------------------------

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` varchar(19) NOT NULL,
  `title` varchar(100) NOT NULL,
  `excerpt` varchar(350) DEFAULT NULL,
  `content` mediumtext NOT NULL,
  `type` enum('post','page') NOT NULL,
  `parent` varchar(19) DEFAULT NULL,
  `categoryid` varchar(19) DEFAULT NULL,
  `pstatus` varchar(10) NOT NULL,
  `cstatus` varchar(10) NOT NULL,
  `ccount` int(11) NOT NULL,
  `rcount` int(11) NOT NULL,
  `creator` varchar(25) NOT NULL,
  `createTime` datetime NOT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_post_category` (`categoryid`),
  KEY `index_post_user` (`creator`),
  CONSTRAINT `index_post_category` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `index_post_user` FOREIGN KEY (`creator`) REFERENCES `user` (`nickName`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for `upload`
-- ----------------------------
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload` (
  `id` varchar(19) NOT NULL,
  `postid` varchar(19) DEFAULT NULL,
  `name` varchar(80) NOT NULL,
  `path` varchar(100) NOT NULL,
  `token` varchar(32) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_upload_post` (`postid`),
  CONSTRAINT `index_upload_post` FOREIGN KEY (`postid`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(19) NOT NULL,
  `nickName` varchar(25) NOT NULL,
  `realName` varchar(25) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(30) NOT NULL,
  `status` varchar(10) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `creator` varchar(15) DEFAULT NULL,
  `lastUpdate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`nickName`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('uHHi9gvg81UXn4PLlLE', 'admin', '东方上人', 'e10adc3949ba59abbe56e057f20f883e', 'manager@zblog.com', '', null, '2014-12-31 18:49:44', null, '2014-12-31 18:49:48');

-- ----------------------------
-- View structure for `view_category`
-- ----------------------------
DROP VIEW IF EXISTS `view_category`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_category` AS select `n`.`id` AS `id`,`n`.`name` AS `text`,count(`n`.`id`) AS `level`,`n`.`visible` AS `visible` from (`category` `n` join `category` `p`) where (`n`.`leftv` between `p`.`leftv` and `p`.`rightv`) group by `n`.`id` order by `n`.`leftv` ;

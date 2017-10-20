/*
Navicat MySQL Data Transfer

Source Server         : localhost336
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : ddap_iot

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-08-15 17:05:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ddap_account
-- ----------------------------
DROP TABLE IF EXISTS `ddap_account`;
CREATE TABLE `ddap_account` (
  `id` char(16) NOT NULL,
  `account` char(32) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `role` char(16) DEFAULT NULL,
  `phone` char(16) DEFAULT NULL,
  `email` char(32) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `u_key` char(64) DEFAULT NULL,
  `u_secret` char(64) DEFAULT NULL,
  `data_state` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddap_data
-- ----------------------------
DROP TABLE IF EXISTS `ddap_data`;
CREATE TABLE `ddap_data` (
  `device_id` char(34) NOT NULL,
  `data` varchar(128) DEFAULT NULL,
  `time_stamp` int(11) DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddap_device
-- ----------------------------
DROP TABLE IF EXISTS `ddap_device`;
CREATE TABLE `ddap_device` (
  `id` char(16) NOT NULL,
  `name` char(32) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `desc_attributes` varchar(500) DEFAULT NULL,
  `product_id` char(32) DEFAULT NULL,
  `device_status` char(1) DEFAULT NULL,
  `device_key` char(64) DEFAULT NULL,
  `device_secret` char(64) DEFAULT NULL,
  `data_state` char(1) DEFAULT NULL,
  `longitude` decimal(12,9) DEFAULT NULL,
  `latitude` decimal(12,9) DEFAULT NULL,
  `owner` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ddap_product
-- ----------------------------
DROP TABLE IF EXISTS `ddap_product`;
CREATE TABLE `ddap_product` (
  `id` char(16) NOT NULL,
  `name` char(32) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `desc_attributes` varchar(500) DEFAULT NULL,
  `protocol` char(16) DEFAULT NULL,
  `data_attributes` varchar(255) DEFAULT NULL,
  `product_key` char(64) DEFAULT NULL,
  `product_secret` char(64) DEFAULT NULL,
  `data_state` char(1) DEFAULT NULL,
  `owner` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Adminer 4.2.4 MySQL dump
-- ONLY FOR TEST THE ACTUAL LOGIN

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

CREATE DATABASE `testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `testdb`;

DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `gender` char(1) NOT NULL DEFAULT 'm',
  `birth_date` date NOT NULL,
  `reg_date` date NOT NULL,
  `direction` varchar(128) NOT NULL,
  `phone_num` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Person` (`id`, `name`, `gender`, `birth_date`, `reg_date`, `direction`, `phone_num`) VALUES
(1,	'nombre',	'm',	'2016-03-26',	'2016-03-26',	'direcion de prueba simple',	'04162723452');



DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` bigint(20) unsigned NOT NULL,
  `rol` int(11) NOT NULL,
  `pass` varchar(32) NOT NULL,
  KEY `id` (`id`),
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `User` (`id`, `rol`, `pass`) VALUES
(1,	1,	'1234');

-- 2016-04-02 18:57:29

-- nombre 12345     username and password for test

--17/04/2016
--agregar apellido en la tabla persona

ALTER TABLE `Person`
ADD `last_name` varchar(32) COLLATE 'utf8_general_ci' NOT NULL AFTER `name`;





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

ALTER TABLE `User`
CHANGE `id` `person_id` bigint(20) unsigned NOT NULL FIRST;


CREATE TABLE `Appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `patient_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `description` varchar(256) NOT NULL,
  `type` enum('FIRST_APPOINTMENT','ROUTINE_CHECKUP','SICK_VISIT','VACCINATIONS','TRAVEL_CLINIC') NOT NULL
);


ALTER TABLE `Appointment`
CHANGE `id` `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT FIRST,
CHANGE `patient_id` `patient_id` bigint(20) unsigned NOT NULL AFTER `id`,
CHANGE `user_id` `user_id` bigint(20) unsigned NOT NULL AFTER `patient_id`,
CHANGE `doctor_id` `doctor_id` bigint(20) unsigned NOT NULL AFTER `user_id`;


ALTER TABLE `Appointment`
ADD FOREIGN KEY (`doctor_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE


-- 21 /04 / 2016

ALTER TABLE `Appointment`
ADD `done` int NOT NULL DEFAULT '0',
ADD `paid` int NOT NULL DEFAULT '0' AFTER `done`;

ALTER TABLE `Appointment`
DROP `paid`;

--  22 / 04 / 2016   database backup

-- Adminer 4.2.4 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

CREATE DATABASE `testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `testdb`;

DROP TABLE IF EXISTS `Appointment`;
CREATE TABLE `Appointment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `doctor_id` bigint(20) unsigned NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `description` varchar(256) NOT NULL,
  `type` enum('FIRST_APPOINTMENT','ROUTINE_CHECKUP','SICK_VISIT','VACCINATIONS','TRAVEL_CLINIC') NOT NULL,
  `done` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `Appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_3` FOREIGN KEY (`doctor_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Appointment` (`id`, `patient_id`, `user_id`, `doctor_id`, `date`, `time`, `description`, `type`, `done`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'12:23:00',	'una descripcion simple',	'ROUTINE_CHECKUP',	0),
(2,	4,	1,	3,	'2016-04-22',	'02:30:00',	'otra cita',	'ROUTINE_CHECKUP',	0),
(5,	6,	1,	3,	'2016-04-20',	'14:34:13',	'descipcion simple',	'ROUTINE_CHECKUP',	0),
(9,	9,	1,	3,	'2016-04-22',	'10:00:10',	'cita',	'ROUTINE_CHECKUP',	0),
(10,	2,	1,	4,	'2016-04-21',	'20:00:10',	'otra cita',	'VACCINATIONS',	0),
(11,	6,	1,	3,	'2016-04-21',	'03:00:00',	'des cita',	'VACCINATIONS',	0);

DROP TABLE IF EXISTS `Consultation`;
CREATE TABLE `Consultation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `patient_id` bigint(20) unsigned NOT NULL,
  `appointment_id` bigint(20) unsigned NOT NULL,
  `paid` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `patient_id` (`patient_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `Consultation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_3` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Consultation` (`id`, `user_id`, `patient_id`, `appointment_id`, `paid`) VALUES
(13,	3,	4,	1,	0);

DROP TABLE IF EXISTS `Invoice`;
CREATE TABLE `Invoice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  `sub_total` float unsigned NOT NULL,
  `iva` float unsigned NOT NULL,
  `total` float unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Invoice_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Invoice_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(16) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `gender` char(1) NOT NULL DEFAULT 'm',
  `birth_date` date NOT NULL,
  `reg_date` date NOT NULL,
  `direction` varchar(128) NOT NULL,
  `phone_num` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Person` (`id`, `name`, `last_name`, `gender`, `birth_date`, `reg_date`, `direction`, `phone_num`) VALUES
(1,	'nom',	'ap',	'f',	'2016-01-13',	'2016-03-26',	'simple direccion',	'04142723452'),
(2,	'jesus',	'algoa',	'm',	'2010-03-19',	'2016-04-18',	'simple',	'051631723'),
(3,	'jose',	'gomez',	'm',	'2014-03-13',	'2016-04-18',	'simple direccion',	'04147659283'),
(4,	'maria',	'rodriguez',	'f',	'2014-03-12',	'2016-04-18',	'donde es',	'04127623523'),
(5,	'julio',	'paredes',	'm',	'2010-03-18',	'2016-04-18',	'direccion 3',	'0412876025'),
(6,	'pac6',	'ap6',	'f',	'2016-03-22',	'2016-04-20',	'dire6',	'1235127435'),
(9,	'paciente9',	'apellido',	'm',	'2016-03-26',	'2016-04-19',	'direccion9',	'123512315');

DROP TABLE IF EXISTS `Prescription`;
CREATE TABLE `Prescription` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Prescription_ibfk_2` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription` (`id`, `consultation_id`) VALUES
(2,	13);

DROP TABLE IF EXISTS `Prescription_item`;
CREATE TABLE `Prescription_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `prescription_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prescription_id` (`prescription_id`),
  CONSTRAINT `Prescription_item_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `Prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription_item` (`id`, `prescription_id`, `description`) VALUES
(1,	2,	'meta'),
(2,	2,	'otra medicina'),
(3,	2,	'recipe mas');

DROP TABLE IF EXISTS `Record`;
CREATE TABLE `Record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `visit_date` date NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Record_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Record` (`id`, `patient_id`, `consultation_id`, `user_id`, `visit_date`, `description`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'primera hisoria...'),
(2,	4,	2,	3,	'2016-04-22',	'segunda historia'),
(3,	4,	2,	3,	'2016-04-22',	'y asiii...'),
(4,	4,	3,	3,	'2016-04-22',	'debe ser depues'),
(5,	4,	3,	3,	'2016-04-22',	'y esta es otra historia'),
(6,	4,	4,	3,	'2016-04-22',	'seleccione la ultima'),
(7,	4,	4,	3,	'2016-04-22',	'y esta'),
(8,	4,	4,	3,	'2016-04-22',	'y el scroll??'),
(9,	4,	5,	3,	'2016-04-22',	'ahora?'),
(10,	4,	5,	3,	'2016-04-22',	'fino!!! otro hisotiral'),
(11,	4,	6,	3,	'2016-04-22',	'ultima historia'),
(12,	4,	7,	3,	'2016-04-22',	'ahora si_ultima'),
(13,	4,	8,	3,	'2016-04-22',	'chao historia de maria'),
(14,	9,	9,	3,	'2016-04-22',	'primera historia\n'),
(15,	4,	10,	3,	'2016-04-22',	'todavia funciona?'),
(16,	4,	13,	3,	'2016-04-22',	'otra mas'),
(17,	4,	13,	3,	'2016-04-22',	'');

DROP TABLE IF EXISTS `Test`;
CREATE TABLE `Test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Test_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test` (`id`, `consultation_id`) VALUES
(4,	13);

DROP TABLE IF EXISTS `Test_item`;
CREATE TABLE `Test_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `test_id` (`test_id`),
  CONSTRAINT `Test_item_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `Test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test_item` (`id`, `test_id`, `description`) VALUES
(5,	4,	'un examen'),
(6,	4,	'otro examen'),
(7,	4,	'otro mas'),
(8,	4,	'si sirve');

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `person_id` bigint(20) unsigned NOT NULL,
  `rol` int(11) NOT NULL,
  `pass` varchar(32) NOT NULL,
  KEY `id` (`person_id`),
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `User` (`person_id`, `rol`, `pass`) VALUES
(1,	1,	'1234'),
(3,	2,	'1234'),
(4,	2,	'1234');

-- 2016-04-23 00:31:30


-- ultimo respaldo de la base de datos   22/ 04 / 2016

-- Adminer 4.2.4 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

CREATE DATABASE `testdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `testdb`;

DROP TABLE IF EXISTS `Appointment`;
CREATE TABLE `Appointment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `doctor_id` bigint(20) unsigned NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `description` varchar(256) NOT NULL,
  `type` enum('FIRST_APPOINTMENT','ROUTINE_CHECKUP','SICK_VISIT','VACCINATIONS','TRAVEL_CLINIC') NOT NULL,
  `done` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `Appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_3` FOREIGN KEY (`doctor_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Appointment` (`id`, `patient_id`, `user_id`, `doctor_id`, `date`, `time`, `description`, `type`, `done`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'12:23:00',	'una descripcion simple',	'ROUTINE_CHECKUP',	0),
(2,	4,	1,	3,	'2016-04-22',	'02:30:00',	'otra cita',	'ROUTINE_CHECKUP',	2),
(5,	6,	1,	3,	'2016-04-20',	'14:34:13',	'descipcion simple',	'ROUTINE_CHECKUP',	0),
(9,	9,	1,	3,	'2016-04-22',	'10:00:10',	'cita',	'ROUTINE_CHECKUP',	0),
(10,	2,	1,	4,	'2016-04-21',	'20:00:10',	'otra cita',	'VACCINATIONS',	0),
(11,	6,	1,	3,	'2016-04-21',	'03:00:00',	'des cita',	'VACCINATIONS',	0);

DROP TABLE IF EXISTS `Consultation`;
CREATE TABLE `Consultation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `patient_id` bigint(20) unsigned NOT NULL,
  `appointment_id` bigint(20) unsigned NOT NULL,
  `paid` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `patient_id` (`patient_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `Consultation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_3` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Consultation` (`id`, `user_id`, `patient_id`, `appointment_id`, `paid`) VALUES
(13,	3,	4,	1,	0),
(14,	3,	4,	2,	1);

DROP TABLE IF EXISTS `Invoice`;
CREATE TABLE `Invoice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  `sub_total` float unsigned NOT NULL,
  `iva` float unsigned NOT NULL,
  `total` float unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Invoice_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Invoice_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Invoice` (`id`, `consultation_id`, `user_id`, `description`, `sub_total`, `iva`, `total`) VALUES
(2,	14,	1,	'primer pago !',	1000,	12,	1120);

DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(16) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `gender` char(1) NOT NULL DEFAULT 'm',
  `birth_date` date NOT NULL,
  `reg_date` date NOT NULL,
  `direction` varchar(128) NOT NULL,
  `phone_num` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Person` (`id`, `name`, `last_name`, `gender`, `birth_date`, `reg_date`, `direction`, `phone_num`) VALUES
(1,	'nom',	'ap',	'f',	'2016-01-13',	'2016-03-26',	'simple direccion',	'04142723452'),
(2,	'jesus',	'algoa',	'm',	'2010-03-19',	'2016-04-18',	'simple',	'051631723'),
(3,	'jose',	'gomez',	'm',	'2014-03-13',	'2016-04-18',	'simple direccion',	'04147659283'),
(4,	'maria',	'rodriguez',	'f',	'2014-03-12',	'2016-04-18',	'donde es',	'04127623523'),
(5,	'julio',	'paredes',	'm',	'2010-03-18',	'2016-04-18',	'direccion 3',	'0412876025'),
(6,	'pac6',	'ap6',	'f',	'2016-03-22',	'2016-04-20',	'dire6',	'1235127435'),
(9,	'paciente9',	'apellido',	'm',	'2016-03-26',	'2016-04-19',	'direccion9',	'123512315');

DROP TABLE IF EXISTS `Prescription`;
CREATE TABLE `Prescription` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Prescription_ibfk_2` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription` (`id`, `consultation_id`) VALUES
(2,	13);

DROP TABLE IF EXISTS `Prescription_item`;
CREATE TABLE `Prescription_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `prescription_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prescription_id` (`prescription_id`),
  CONSTRAINT `Prescription_item_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `Prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription_item` (`id`, `prescription_id`, `description`) VALUES
(1,	2,	'meta'),
(2,	2,	'otra medicina'),
(3,	2,	'recipe mas');

DROP TABLE IF EXISTS `Record`;
CREATE TABLE `Record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `visit_date` date NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Record_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Record` (`id`, `patient_id`, `consultation_id`, `user_id`, `visit_date`, `description`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'primera hisoria...'),
(2,	4,	2,	3,	'2016-04-22',	'segunda historia'),
(3,	4,	2,	3,	'2016-04-22',	'y asiii...'),
(4,	4,	3,	3,	'2016-04-22',	'debe ser depues'),
(5,	4,	3,	3,	'2016-04-22',	'y esta es otra historia'),
(6,	4,	4,	3,	'2016-04-22',	'seleccione la ultima'),
(7,	4,	4,	3,	'2016-04-22',	'y esta'),
(8,	4,	4,	3,	'2016-04-22',	'y el scroll??'),
(9,	4,	5,	3,	'2016-04-22',	'ahora?'),
(10,	4,	5,	3,	'2016-04-22',	'fino!!! otro hisotiral'),
(11,	4,	6,	3,	'2016-04-22',	'ultima historia'),
(12,	4,	7,	3,	'2016-04-22',	'ahora si_ultima'),
(13,	4,	8,	3,	'2016-04-22',	'chao historia de maria'),
(14,	9,	9,	3,	'2016-04-22',	'primera historia\n'),
(15,	4,	10,	3,	'2016-04-22',	'todavia funciona?'),
(16,	4,	13,	3,	'2016-04-22',	'otra mas'),
(17,	4,	13,	3,	'2016-04-22',	'');

DROP TABLE IF EXISTS `Test`;
CREATE TABLE `Test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Test_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test` (`id`, `consultation_id`) VALUES
(4,	13);

DROP TABLE IF EXISTS `Test_item`;
CREATE TABLE `Test_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `test_id` (`test_id`),
  CONSTRAINT `Test_item_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `Test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test_item` (`id`, `test_id`, `description`) VALUES
(5,	4,	'un examen'),
(6,	4,	'otro examen'),
(7,	4,	'otro mas'),
(8,	4,	'si sirve');

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `person_id` bigint(20) unsigned NOT NULL,
  `rol` int(11) NOT NULL,
  `pass` varchar(32) NOT NULL,
  KEY `id` (`person_id`),
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `User` (`person_id`, `rol`, `pass`) VALUES
(1,	1,	'1234'),
(3,	2,	'1234'),
(4,	2,	'1234');

-- 2016-04-23 02:10:27

-- Adminer 4.2.4 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `Appointment`;
CREATE TABLE `Appointment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `doctor_id` bigint(20) unsigned NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `description` varchar(256) NOT NULL,
  `type` enum('FIRST_APPOINTMENT','ROUTINE_CHECKUP','SICK_VISIT','VACCINATIONS','TRAVEL_CLINIC') NOT NULL,
  `done` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `Appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Appointment_ibfk_3` FOREIGN KEY (`doctor_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Appointment` (`id`, `patient_id`, `user_id`, `doctor_id`, `date`, `time`, `description`, `type`, `done`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'12:23:00',	'una descripcion simple',	'ROUTINE_CHECKUP',	0),
(2,	4,	1,	3,	'2016-04-22',	'02:30:00',	'otra cita',	'ROUTINE_CHECKUP',	2),
(5,	6,	1,	3,	'2016-04-20',	'14:34:13',	'descipcion simple',	'ROUTINE_CHECKUP',	0),
(9,	9,	1,	3,	'2016-04-22',	'10:00:10',	'cita',	'ROUTINE_CHECKUP',	0),
(10,	2,	1,	4,	'2016-04-21',	'20:00:10',	'otra cita',	'VACCINATIONS',	0),
(11,	6,	1,	3,	'2016-04-21',	'03:00:00',	'des cita',	'VACCINATIONS',	0);

DROP TABLE IF EXISTS `Consultation`;
CREATE TABLE `Consultation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `patient_id` bigint(20) unsigned NOT NULL,
  `appointment_id` bigint(20) unsigned NOT NULL,
  `paid` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `patient_id` (`patient_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `Consultation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consultation_ibfk_3` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Consultation` (`id`, `user_id`, `patient_id`, `appointment_id`, `paid`) VALUES
(13,	3,	4,	1,	0),
(14,	3,	4,	2,	1);

DROP TABLE IF EXISTS `Invoice`;
CREATE TABLE `Invoice` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  `sub_total` float unsigned NOT NULL,
  `iva` float unsigned NOT NULL,
  `total` float unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Invoice_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Invoice_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Invoice` (`id`, `consultation_id`, `user_id`, `description`, `sub_total`, `iva`, `total`) VALUES
(2,	14,	1,	'primer pago !',	1000,	12,	1120);

DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(16) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `gender` char(1) NOT NULL DEFAULT 'm',
  `birth_date` date NOT NULL,
  `reg_date` date NOT NULL,
  `direction` varchar(128) NOT NULL,
  `phone_num` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Person` (`id`, `name`, `last_name`, `gender`, `birth_date`, `reg_date`, `direction`, `phone_num`) VALUES
(1,	'nom',	'ap',	'F',	'2016-01-13',	'2016-03-26',	'simple direccion',	'04142723452'),
(2,	'jesus',	'algoa',	'M',	'2010-03-19',	'2016-04-18',	'simple',	'051631723'),
(3,	'jose',	'gomez',	'M',	'2014-03-13',	'2016-04-18',	'simple direccion',	'04147659283'),
(4,	'maria',	'rodriguez',	'F',	'2014-03-12',	'2016-04-18',	'donde es',	'04127623523'),
(5,	'julio',	'paredes',	'M',	'2010-03-18',	'2016-04-18',	'direccion 3',	'0412876025'),
(6,	'pac6',	'ap6',	'F',	'2016-03-22',	'2016-04-20',	'dire6',	'1235127435'),
(7,	'adm',	'gal',	'M',	'2004-06-17',	'2016-04-24',	'dir',	'1235'),
(9,	'paciente9',	'apellido',	'M',	'2016-03-26',	'2016-04-19',	'direccion9',	'123512315');

DROP TABLE IF EXISTS `Prescription`;
CREATE TABLE `Prescription` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Prescription_ibfk_2` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription` (`id`, `consultation_id`) VALUES
(2,	13);

DROP TABLE IF EXISTS `Prescription_item`;
CREATE TABLE `Prescription_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `prescription_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prescription_id` (`prescription_id`),
  CONSTRAINT `Prescription_item_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `Prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Prescription_item` (`id`, `prescription_id`, `description`) VALUES
(1,	2,	'meta'),
(2,	2,	'otra medicina'),
(3,	2,	'recipe mas');

DROP TABLE IF EXISTS `Record`;
CREATE TABLE `Record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) unsigned NOT NULL,
  `consultation_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `visit_date` date NOT NULL,
  `description` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_id` (`patient_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Record_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Record` (`id`, `patient_id`, `consultation_id`, `user_id`, `visit_date`, `description`) VALUES
(1,	4,	1,	3,	'2016-04-22',	'primera hisoria...'),
(2,	4,	2,	3,	'2016-04-22',	'segunda historia'),
(3,	4,	2,	3,	'2016-04-22',	'y asiii...'),
(4,	4,	3,	3,	'2016-04-22',	'debe ser depues'),
(5,	4,	3,	3,	'2016-04-22',	'y esta es otra historia'),
(6,	4,	4,	3,	'2016-04-22',	'seleccione la ultima'),
(7,	4,	4,	3,	'2016-04-22',	'y esta'),
(8,	4,	4,	3,	'2016-04-22',	'y el scroll??'),
(9,	4,	5,	3,	'2016-04-22',	'ahora?'),
(10,	4,	5,	3,	'2016-04-22',	'fino!!! otro hisotiral'),
(11,	4,	6,	3,	'2016-04-22',	'ultima historia'),
(12,	4,	7,	3,	'2016-04-22',	'ahora si_ultima'),
(13,	4,	8,	3,	'2016-04-22',	'chao historia de maria'),
(14,	9,	9,	3,	'2016-04-22',	'primera historia\n'),
(15,	4,	10,	3,	'2016-04-22',	'todavia funciona?'),
(16,	4,	13,	3,	'2016-04-22',	'otra mas'),
(17,	4,	13,	3,	'2016-04-22',	'');

DROP TABLE IF EXISTS `Test`;
CREATE TABLE `Test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `consultation_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `consultation_id` (`consultation_id`),
  CONSTRAINT `Test_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `Consultation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test` (`id`, `consultation_id`) VALUES
(4,	13);

DROP TABLE IF EXISTS `Test_item`;
CREATE TABLE `Test_item` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `test_id` bigint(20) unsigned NOT NULL,
  `description` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `test_id` (`test_id`),
  CONSTRAINT `Test_item_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `Test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Test_item` (`id`, `test_id`, `description`) VALUES
(5,	4,	'un examen'),
(6,	4,	'otro examen'),
(7,	4,	'otro mas'),
(8,	4,	'si sirve');

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `person_id` bigint(20) unsigned NOT NULL,
  `rol` int(11) NOT NULL,
  `pass` varchar(32) NOT NULL,
  KEY `id` (`person_id`),
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `User` (`person_id`, `rol`, `pass`) VALUES
(1,	1,	'1234'),
(3,	2,	'1234'),
(7,	3,	'1234'),
(4,	2,	'1234');

-- 2016-04-25 03:52:10



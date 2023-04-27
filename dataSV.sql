-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: courses
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `date_from` datetime DEFAULT NULL,
  `date_to` datetime DEFAULT NULL,
  `packages` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `trainer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1noqds2b17f5t1ovqi6f6uun2` (`trainer_id`),
  CONSTRAINT `FK1noqds2b17f5t1ovqi6f6uun2` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'2022-10-27 09:40:16.054','2022-10-27 09:40:16.054','IS202210273202',NULL,'2022-12-02 07:00:00',NULL,_binary '\0',NULL);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
INSERT INTO `combo` VALUES (1,'2022-10-30 12:45:28.197','2022-10-30 12:45:28.197','test','title');
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `combo_package`
--

DROP TABLE IF EXISTS `combo_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_package` (
  `combo_id` bigint NOT NULL,
  `package_id` bigint NOT NULL,
  `sale_price` double DEFAULT NULL,
  `_package_id` bigint NOT NULL,
  PRIMARY KEY (`combo_id`,`package_id`),
  KEY `FKbprhu1d8f4561haminr8d2376` (`package_id`),
  KEY `FK2xfowgo76xkna9ek2nc9qwmmp` (`_package_id`),
  CONSTRAINT `FK2xfowgo76xkna9ek2nc9qwmmp` FOREIGN KEY (`_package_id`) REFERENCES `package` (`id`),
  CONSTRAINT `FK7sbfywtiadcfpm8p5vcr03xh7` FOREIGN KEY (`combo_id`) REFERENCES `combo` (`id`),
  CONSTRAINT `FKbprhu1d8f4561haminr8d2376` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo_package`
--

LOCK TABLES `combo_package` WRITE;
/*!40000 ALTER TABLE `combo_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `excerpt` varchar(255) DEFAULT NULL,
  `is_combo` bit(1) DEFAULT NULL,
  `list_price` double DEFAULT NULL,
  `sale_price` double DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbc4v7pm3osjvg7enn4d1sv0a5` (`subject_id`),
  CONSTRAINT `FKbc4v7pm3osjvg7enn4d1sv0a5` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (1,'2022-10-30 12:13:09.603','2022-10-30 12:13:09.603','test','ngay','excerpt',_binary '\0',3500000,2000000,_binary '','title',1);
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `can_add` bit(1) DEFAULT NULL,
  `can_delete` bit(1) DEFAULT NULL,
  `can_edit` bit(1) DEFAULT NULL,
  `get_all_data` bit(1) DEFAULT NULL,
  `screen_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`screen_id`),
  KEY `FKdhd6msgurbmw70w034mld1ueb` (`screen_id`),
  CONSTRAINT `FKdhd6msgurbmw70w034mld1ueb` FOREIGN KEY (`screen_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `FKrg6w6lbn6bgo4djfhgjdqegth` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `body` text NOT NULL,
  `status` int NOT NULL,
  `thumnail_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  KEY `FKc19uv3y1k398ug1fwsys5wog5` (`category_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKc19uv3y1k398ug1fwsys5wog5` FOREIGN KEY (`category_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'2022-10-28 19:32:40.16','2022-10-28 19:36:00.048','Ước mơ của mọi người là vô hạn',0,'d27f2383-7520-4f08-b706-de26234066c4.jpg','Hành trình đi tìm ước mơ',3,NULL);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `setting_id` bigint NOT NULL AUTO_INCREMENT,
  `desciption` varchar(255) DEFAULT NULL,
  `display_order` varchar(255) DEFAULT NULL,
  `setting_title` varchar(255) DEFAULT NULL,
  `setting_value` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  PRIMARY KEY (`setting_id`),
  KEY `FKq8llkt5ntkjr9l9rfm1wnstwm` (`type_id`),
  CONSTRAINT `FKq8llkt5ntkjr9l9rfm1wnstwm` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES (1,'ROLE_GUEST','ROLE_GUEST','ROLE_GUEST','ROLE_GUEST',_binary '',1),(2,'ROLE_ADMIN','ROLE_ADMIN','ROLE_ADMIN','ROLE_ADMIN',_binary '',1),(3,'role of manager','role of manager','Manager','ROLE_MANAGER',_binary '',1),(4,'role of supporter','role of supporter','Supporter','ROLE_SUPPORTER',_binary '',1),(5,'role of marketer','role of marketer','Marketer','ROLE_MARKETER',_binary '',1),(6,'role of trainee','role of trainee','Trainee','ROLE_TRAINEE',_binary '',1),(7,'role of trainer','role of trainer','Trainer','ROLE_TRAINER',_binary '',1),(8,'role of expert','role of expert','Expert','ROLE_EXPERT',_binary '',1),(9,'category of web contact',NULL,'Subject','SUBJECT',_binary '',5),(10,'category of web contact',NULL,'Courses','COURSES',_binary '',5),(11,'category of web contact',NULL,'Account','ACCOUNT',_binary '',5),(12,NULL,NULL,'Lập trình web',NULL,_binary '',6),(13,NULL,NULL,'Quản trị mạng',NULL,_binary '',6),(14,NULL,NULL,'Lập trình android',NULL,_binary '',6),(15,NULL,NULL,'Lập trình python',NULL,_binary '',6),(16,NULL,NULL,'Đào tạo offline',NULL,_binary '',8),(17,NULL,NULL,'Tin khuyến mãi',NULL,_binary '',8),(18,NULL,NULL,'Học HTML căn bản',NULL,_binary '',8),(19,NULL,NULL,'Học CSS căn bản',NULL,_binary '',8);
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slide`
--

DROP TABLE IF EXISTS `slide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slide` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  `valid_to` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slide`
--

LOCK TABLES `slide` WRITE;
/*!40000 ALTER TABLE `slide` DISABLE KEYS */;
INSERT INTO `slide` VALUES (1,'2022-10-31 21:32:06.527','2022-10-31 21:32:06.527','http://localhost:8080/api/account/downloadFile/b277f019-e547-4dc2-b99f-4fb6376564f7.png',1,'2022-11-02 07:00:00');
/*!40000 ALTER TABLE `slide` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `status` bit(1) NOT NULL,
  `expert_id` bigint DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3q2ooejviwaxfhel0gs1cdgrw` (`expert_id`),
  KEY `FKnymjcv6wa4jysqsm7sw9trxiv` (`manager_id`),
  KEY `FK4tjtfmgujp6f7lbyy4trbouj0` (`category_id`),
  CONSTRAINT `FK3q2ooejviwaxfhel0gs1cdgrw` FOREIGN KEY (`expert_id`) REFERENCES `expert` (`id`),
  CONSTRAINT `FK4tjtfmgujp6f7lbyy4trbouj0` FOREIGN KEY (`category_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `FKnymjcv6wa4jysqsm7sw9trxiv` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'2022-10-30 12:08:58.048','2022-10-30 12:08:58.048','JAVA1',NULL,'Basic java1','check',1600000,_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'User Role'),(2,'System Screen'),(3,'Class Setting Type'),(4,'Subject Setting Type'),(5,'Web Contact Category'),(6,'Subject Category'),(7,'Training Branch'),(8,'Post');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `register_token` varchar(255) DEFAULT NULL,
  `reset_password_token` varchar(255) DEFAULT NULL,
  `time_register_token` datetime DEFAULT NULL,
  `type_account` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKe9lyuequ2wogf8lkp6a1pl70t` (`role_id`),
  CONSTRAINT `FKe9lyuequ2wogf8lkp6a1pl70t` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'2022-10-25 09:14:46.33','2022-10-29 23:24:00.257',_binary '','ea91eb74-2104-4ad9-a6c6-838753b17b28.jpg','hhung003@gmail.com','Nguyen Hung','$2a$10$rnRorVZRWxWeQFc/.hBWBeymrOCux5.VvTfCqOE.OQCOF5sNvB6oG',NULL,'DxARHq2Q0cBDHhW1H4X3zp87RnNnv9',NULL,'2022-10-25 09:14:46',NULL,'vncharles',2),(3,'2022-10-25 11:05:54.823','2022-10-25 11:07:48.601',_binary '',NULL,'manage1@gmail.com','Manager1','$2a$10$RHsbG.e7Z.7deSZo2YR4YeqMHe8l9Jr02/C26qausIH4MPpsf6ebC',NULL,'pvfzzoD8nMwJJu8XX29XLhi7XG2TRs',NULL,'2022-10-25 11:05:55',NULL,'manager1',1),(4,'2022-10-25 11:06:20.036','2022-10-25 11:07:49.633',_binary '',NULL,'manager2@gmail.com','Manager2','$2a$10$Q5rGZMeMITYsl.o6/pErru8zd/uzOf93fpk8ZSwHOnkZW49Ft1RZG',NULL,'SVXdgvDtmvxjOyWNCNr2VVSUTAL9nD',NULL,'2022-10-25 11:06:20',NULL,'manager2',1),(5,'2022-10-25 11:06:28.856','2022-10-25 11:07:50.2',_binary '',NULL,'manager3@gmail.com','Manager3','$2a$10$Pr/3.9GaWLfaUhs2S7HNyOoyrHrbyyRq7Z4lo0juInVvCGom0RnjO',NULL,'DSy7JHugxaTMDqWdFe91zCiWlrzhlZ',NULL,'2022-10-25 11:06:29',NULL,'manager3',1),(6,'2022-10-25 11:06:37.482','2022-10-25 11:07:50.734',_binary '',NULL,'manager4@gmail.com','Manager4','$2a$10$mClOTlNWTo8DXoJFKtZ.oO8T/d/gqq0CL6gQEJ.putWg4tKraKp0K',NULL,'eoFg7Kch53EVRo1RKTVMCJyKHyDzWc',NULL,'2022-10-25 11:06:37',NULL,'manager4',1),(7,'2022-10-25 11:06:52.17','2022-10-25 11:07:51.304',_binary '',NULL,'expert1@gmail.com','Expert1','$2a$10$pgLrPMPC3m5QYUJ81ECQbeFCZAOFVCJquv5g8n8RN92iy.hPwN.Gq',NULL,'QmxuJQILYsJKgXyPKXGJO0aAvdHS98',NULL,'2022-10-25 11:06:52',NULL,'expert1',1),(8,'2022-10-25 11:07:01.252','2022-10-25 11:07:51.806',_binary '',NULL,'expert2@gmail.com','Expert2','$2a$10$5hB.S1F2mmY1a19omdGGiebwWXanOJXfLceeL6OlyHSEwg2znEaU2',NULL,'teNmBZM4k1lhaengvFGKFX6oVRvUkI',NULL,'2022-10-25 11:07:01',NULL,'expert2',1),(9,'2022-10-25 11:07:08.598','2022-10-25 11:07:52.3',_binary '',NULL,'expert3@gmail.com','Expert3','$2a$10$8E5BJ0YAewy/wBwLB1U9G.R0izWif1dn6VbfmXejhaNFNo7w7EZAe',NULL,'Ckp8R84MIoJbSW6bgOiPBv076lAu9g',NULL,'2022-10-25 11:07:09',NULL,'expert3',1),(10,'2022-10-25 11:07:15.647','2022-10-25 11:07:53.302',_binary '',NULL,'expert4@gmail.com','Expert4','$2a$10$yNgdIIU/oJ90jEjr8ODiPOl4xwcbGq/Vx2nnnexavVbWwWpUAfRtu',NULL,'lRNTtKBb1pxJjveoaGeRNNeV7Q66RF',NULL,'2022-10-25 11:07:25',NULL,'expert4',1),(11,'2022-10-25 11:07:39.688','2022-10-25 11:07:54.522',_binary '',NULL,'user1@gmail.com','User1','$2a$10$jPecRPfzGaOd9LOVUK69J.AwqSZqQS2ueZZNb/5Nuj2nhd7FSPPoO',NULL,'c46Nj6LeDI4GgLV86tmgnABnK4BdQL',NULL,'2022-10-25 11:07:40',NULL,'user1',1),(12,'2022-10-28 11:30:15.377','2022-10-28 11:30:15.377',_binary '\0',NULL,'hhung003@gmail.com','Nguyen Hung','$2a$10$5hBxnvmL4ZlCVGOmc9qMYesGTS2aQmS1SPsaWOB52LIAwu/BD1MmS',NULL,'T9MG03HwG24F58IKN9mp1Dz7hJ7pnn',NULL,'2022-10-28 11:30:15',NULL,'vncharles1',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_contact`
--

DROP TABLE IF EXISTS `web_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` varchar(255) DEFAULT NULL,
  `updated_date` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5vib470hmeexgdgrm8hpi1sqn` (`category_id`),
  CONSTRAINT `FK5vib470hmeexgdgrm8hpi1sqn` FOREIGN KEY (`category_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_contact`
--

LOCK TABLES `web_contact` WRITE;
/*!40000 ALTER TABLE `web_contact` DISABLE KEYS */;
INSERT INTO `web_contact` VALUES (1,'2022-10-31 15:37:36.446','2022-10-31 15:37:36.446','hhung003@gmail.com','Trần Quốc Trọng','Test web contact','0357770753',_binary '\0',9);
/*!40000 ALTER TABLE `web_contact` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-01 13:30:09

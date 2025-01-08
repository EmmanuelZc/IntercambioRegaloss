-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: intercambiosdb
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `contactos_locales`
--

DROP TABLE IF EXISTS `contactos_locales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contactos_locales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactos_locales`
--

LOCK TABLES `contactos_locales` WRITE;
/*!40000 ALTER TABLE `contactos_locales` DISABLE KEYS */;
/*!40000 ALTER TABLE `contactos_locales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intercambio`
--

DROP TABLE IF EXISTS `intercambio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intercambio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_intercambio` varchar(255) NOT NULL,
  `clave_unica` varchar(45) NOT NULL,
  `id_user` int NOT NULL,
  `fecha_limite_registro` varchar(45) NOT NULL,
  `fecha_intercambio` varchar(45) NOT NULL,
  `hora_intercambio` time NOT NULL,
  `lugar_intercambio` varchar(255) NOT NULL,
  `monto` double NOT NULL,
  `comentarios` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_unica_UNIQUE` (`clave_unica`),
  KEY `id_organizador_idx` (`id_user`),
  CONSTRAINT `id_organizador` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intercambio`
--

LOCK TABLES `intercambio` WRITE;
/*!40000 ALTER TABLE `intercambio` DISABLE KEYS */;
INSERT INTO `intercambio` VALUES (1,'jsjsjskksb','8afae424-19ad-4ed0-81e3-5846b2341d50',5,'2025-01-07','2025-01-07','07:33:00','nsnsksksv',0,'nxnxnxnzb'),(2,'jsjsjskksb','1d2b7e49-8075-4d9e-a486-278259b1d81d',5,'2025-01-07','2025-01-07','07:33:00','nsnsksksv',300,'nxnxnxnzb'),(3,'jaajjajs','bfc155ae-4050-4d00-8365-131f3ddaf778',5,'2025-01-07','2025-01-07','08:39:00','djjsndndn',500,'nznsnsns'),(4,'jsjsjsks','30e15dce-4e99-40a5-b082-508df25e6b95',5,'2025-01-07','2025-01-07','12:24:00','bdndjdnnd',400,'jdjdjdj'),(5,'kkdkdkdkf','12b8da35-8050-4e5b-bef3-e6271f8a1e50',5,'2025-01-07','2025-01-07','12:38:00','nfnxnxnx',600,'ptm'),(6,'kkdkdkdkf','a6d8b4a5-a6ce-4334-b4f8-68b8c7495ae6',5,'2025-01-07','2025-01-07','12:38:00','nfnxnxnx',600,'ptm'),(7,'jdjdjkd','15ec79e1-55cc-4813-80ba-d909e5ebb8bb',5,'2025-01-07','2025-01-07','12:55:00','mfmfmfm',697,'ndndndnd'),(8,'jsjjsns','e2fda4d7-a6b0-43b0-81b5-e0def2bf9bb8',5,'2025-01-07','2025-01-07','13:34:00','bdjdjdjd',266,'jdjdndkd'),(9,'final ','4405108d-c51a-496b-8893-ce3ce99b1c59',5,'2025-01-07','2025-01-07','16:48:00','casa',799,'hbvgjj'),(10,'mmmmm','7df86b3b-fb74-4ece-9ec6-7668517ddd02',5,'2025-01-07','2025-01-07','16:52:00','bnjhj',700,'vhjjj'),(11,'bkkjj','67cc41ef-e4a6-4c77-bb6b-6fef1dc9a51b',5,'2025-01-07','2025-01-07','16:57:00','hhhv',589,'hhjk'),(12,'hjjjb','6040d431',5,'2025-01-07','2025-01-07','17:03:00','jhhvc',700,'vbcd'),(13,'hjjjb','9a37a7e8',5,'2025-01-07','2025-01-07','17:03:00','jhhvc',700,'vbcd'),(14,'hjjjb','061db00a',5,'2025-01-07','2025-01-07','17:03:00','jhhvc',700,'vbcd');
/*!40000 ALTER TABLE `intercambio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intercambios`
--

DROP TABLE IF EXISTS `intercambios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intercambios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `fechaLimiteRegistro` date NOT NULL,
  `fechaIntercambio` date NOT NULL,
  `horaIntercambio` time NOT NULL,
  `lugar` varchar(255) NOT NULL,
  `montoMaximo` float NOT NULL,
  `comentarios` text,
  `temas` json DEFAULT NULL,
  `participantes` json DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intercambios`
--

LOCK TABLES `intercambios` WRITE;
/*!40000 ALTER TABLE `intercambios` DISABLE KEYS */;
/*!40000 ALTER TABLE `intercambios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participantes`
--

DROP TABLE IF EXISTS `participantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participantes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_intercambio` int NOT NULL,
  ` nombre` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `confirmado` int DEFAULT '0',
  `asignado_a` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_intercambio_idx` (`id_intercambio`),
  KEY `asignado_a_idx` (`asignado_a`),
  CONSTRAINT `asignado_a` FOREIGN KEY (`asignado_a`) REFERENCES `participantes` (`id`),
  CONSTRAINT `id_intercambio` FOREIGN KEY (`id_intercambio`) REFERENCES `intercambio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participantes`
--

LOCK TABLES `participantes` WRITE;
/*!40000 ALTER TABLE `participantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `participantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temas`
--

DROP TABLE IF EXISTS `temas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `temas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_intercambio` int DEFAULT NULL,
  `tema` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_intercambio_idx` (`id_intercambio`),
  KEY `id_intercambioo_idx` (`id_intercambio`),
  CONSTRAINT `id_intercambioo` FOREIGN KEY (`id_intercambio`) REFERENCES `intercambio` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temas`
--

LOCK TABLES `temas` WRITE;
/*!40000 ALTER TABLE `temas` DISABLE KEYS */;
INSERT INTO `temas` VALUES (1,8,'sueteres'),(2,8,'libros'),(3,8,'muñecos'),(4,9,'bbbbb'),(5,10,'kkkkk'),(6,11,'jkkk'),(7,13,'vyvhjj');
/*!40000 ALTER TABLE `temas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `correo` varchar(45) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'hhjjjjjjj','jjjjjjjjj','ezurita056@gmail.com','$2b$10$6qpW34BIwq81m5y6zSHaTun6wcj0My.KiIfYY0sXRKnnBGOB1W6Ey'),(4,'wjksiwks','bsjsjsks','ezurita056.2@gmail.com','$2b$10$gnp/LTgB72BRaNTcQM6rruMjWkQPceFvjdsJbxdqwLk.vdMvtoiem'),(5,'bnnnn','sssss','ezurita0564@gmail.com','$2b$10$2FV6fUfRU8.22Lz0jGmr7eNw2tChVOMmvyyAOHsvmAWfU5i0enzBi');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-07 19:30:54

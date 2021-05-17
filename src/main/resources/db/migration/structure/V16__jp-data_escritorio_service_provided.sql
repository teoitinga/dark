-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 192.168.15.29    Database: jp-data
-- ------------------------------------------------------
-- Server version	8.0.21

--
-- Table structure for table `visita_produtores`
--

CREATE TABLE `service_provided_escritorio` (
  `escritorio_codigo` int(11) NOT NULL,
  `service_provided_codigo` varchar(255) NOT NULL,
  KEY `FK_escritorio_codigo` (`escritorio_codigo`),
  KEY `FK_service_provided_codigo` (`service_provided_codigo`),
  CONSTRAINT `FK_escritorio_codigo` FOREIGN KEY (`escritorio_codigo`) REFERENCES `escritorio` (`codigo`),
  CONSTRAINT `service_provided_codigo` FOREIGN KEY (`service_provided_codigo`) REFERENCES `service_provided` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 192.168.15.29    Database: jp-data
-- ------------------------------------------------------
-- Server version	8.0.21

--
-- Table structure for table `visita_produtores`
--

CREATE TABLE `persona_escritorio` (
  `escritorio_codigo` int(11) NOT NULL,
  `persona_cpf` varchar(255) NOT NULL,
  KEY `FK_escritorio_codigo` (`escritorio_codigo`),
  KEY `FK_persona_cpf` (`persona_cpf`),
  CONSTRAINT `FK_escritorio_codigo_ps` FOREIGN KEY (`escritorio_codigo`) REFERENCES `escritorio` (`codigo`),
  CONSTRAINT `FK_persona_cpf` FOREIGN KEY (`persona_cpf`) REFERENCES `persona` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


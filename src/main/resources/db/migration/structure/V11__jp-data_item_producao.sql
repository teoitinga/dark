-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 192.168.15.29    Database: jp-data
-- ------------------------------------------------------
-- Server version	8.0.21

CREATE TABLE `item_producao` (
  `codigo` varchar(255) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `fator_conv_para_anual` decimal(19,2) DEFAULT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  `textomda` varchar(255) DEFAULT NULL,
  `origem_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKe0dkmg5m83mwm1jhh3arq3o1p` (`origem_codigo`),
  CONSTRAINT `FKe0dkmg5m83mwm1jhh3arq3o1p` FOREIGN KEY (`origem_codigo`) REFERENCES `origem_renda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


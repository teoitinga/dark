--
-- Table structure for table `item_producao`
--
CREATE TABLE `item_producao` (
  `codigo` varchar(255) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `fator_conv_para_anual` decimal(19,2) DEFAULT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  `textomda` varchar(255) DEFAULT NULL,
  `origem_renda_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK9vqoqk9limih7mtfnebvoay9j` (`origem_renda_codigo`),
  CONSTRAINT `FK9vqoqk9limih7mtfnebvoay9j` FOREIGN KEY (`origem_renda_codigo`) REFERENCES `origem_renda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `item_producao`
--
CREATE TABLE `escritorio` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(155) DEFAULT NULL,
  `referency` varchar(155) DEFAULT NULL,
  `endereco` varchar(155) DEFAULT NULL,
  `municipio` varchar(155) DEFAULT NULL,
  `fone` varchar(155) DEFAULT NULL,
  `zap` varchar(155) DEFAULT NULL,
  `email` varchar(155) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

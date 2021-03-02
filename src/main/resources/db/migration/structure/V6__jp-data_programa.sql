--
-- Table structure for table `programa`
--
CREATE TABLE `programa` (
  `codigo` varchar(255) NOT NULL,
  `data_fim` date DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


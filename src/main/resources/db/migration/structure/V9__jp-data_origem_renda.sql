--
-- Table structure for table `origem_renda`
--
CREATE TABLE `origem_renda` (
  `codigo` varchar(255) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `referencia` varchar(255) DEFAULT NULL,
  `is_agro` VARCHAR(3) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


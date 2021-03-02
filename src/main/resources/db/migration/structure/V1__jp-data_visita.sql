--
-- Table structure for table `visita`
--

CREATE TABLE `visita` (
  `codigo` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `data_da_visita` date DEFAULT NULL,
  `local_do_atendimento` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `orientacao` varchar(255) DEFAULT NULL,
  `recomendacao` varchar(255) DEFAULT NULL,
  `situacao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

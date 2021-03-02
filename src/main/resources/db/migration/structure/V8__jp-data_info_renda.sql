--
-- Table structure for table `info_renda`
--
CREATE TABLE `info_renda` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `area_explorada` decimal(19,2) DEFAULT NULL,
  `area_imovel_principal` decimal(19,2) DEFAULT NULL,
  `membros_familia` int(11) DEFAULT NULL,
  `qtd_propriedades` int(11) DEFAULT NULL,
  `visita_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKpvtpf1pu604l6y1cjn2f8esk4` (`visita_codigo`),
  CONSTRAINT `FKpvtpf1pu604l6y1cjn2f8esk4` FOREIGN KEY (`visita_codigo`) REFERENCES `visita` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


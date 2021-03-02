CREATE TABLE `beneficiario` (
  `id` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `observacoes` varchar(255) DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `beneficiario_cpf` varchar(255) NOT NULL,
  `programa_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6xiukgul5buky6exehmrha8u4` (`beneficiario_cpf`),
  KEY `FKd5c30accy3g4wo95myy76odq` (`programa_codigo`),
  CONSTRAINT `FK6xiukgul5buky6exehmrha8u4` FOREIGN KEY (`beneficiario_cpf`) REFERENCES `persona` (`cpf`),
  CONSTRAINT `FKd5c30accy3g4wo95myy76odq` FOREIGN KEY (`programa_codigo`) REFERENCES `programa` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

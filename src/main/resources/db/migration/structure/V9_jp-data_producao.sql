--
-- Table structure for table `producao`
--
CREATE TABLE `producao` (
  `codigo` int(11) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `valor_unitario` decimal(19,2) DEFAULT NULL,
  `quantidade` decimal(19,2) DEFAULT NULL,
  `descricao` varchar(50) DEFAULT NULL,
  `data_producao` date DEFAULT NULL,
  `item_producao_codigo` varchar(255) DEFAULT NULL,
  `info_renda_codigo` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_item_producao_codigo` (`item_producao_codigo`),
  KEY `fk_info_renda_codigo` (`info_renda_codigo`),
  CONSTRAINT `fk_item_producao_codigo` FOREIGN KEY (`item_producao_codigo`) REFERENCES `item_producao` (`codigo`),
  CONSTRAINT `fk_info_renda_codigo` FOREIGN KEY (`info_renda_codigo`) REFERENCES `info_renda` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

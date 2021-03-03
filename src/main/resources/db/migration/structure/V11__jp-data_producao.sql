--
-- Table structure for table `producao`
--
CREATE TABLE `producao` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `data_da_producao` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `quantidade` decimal(19,2) DEFAULT NULL,
  `valor_unitario` decimal(19,2) DEFAULT NULL,
  `info_renda_codigo` int(11) DEFAULT NULL,
  `item_producao_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKhddd2vnclqks9e2yoihg2ch83` (`info_renda_codigo`),
  KEY `FKs5iyefr1pq8ythipqvuaemq3o` (`item_producao_codigo`),
  CONSTRAINT `FKhddd2vnclqks9e2yoihg2ch83` FOREIGN KEY (`info_renda_codigo`) REFERENCES `info_renda` (`codigo`),
  CONSTRAINT `FKs5iyefr1pq8ythipqvuaemq3o` FOREIGN KEY (`item_producao_codigo`) REFERENCES `item_producao` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `prices_item`
--
CREATE TABLE `prices_item` (
  `id` varchar(255) NOT NULL,
  `detalhes` varchar(255) DEFAULT NULL,
  `especificação` varchar(255) DEFAULT NULL,
  `unidade` varchar(255) DEFAULT NULL,
  `unidade_descricao` varchar(255) DEFAULT NULL,
  `item_de_producao_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd1cyr423ywm8kubi6w5lpxcc4` (`item_de_producao_codigo`),
  CONSTRAINT `FKd1cyr423ywm8kubi6w5lpxcc4` FOREIGN KEY (`item_de_producao_codigo`) REFERENCES `item_producao` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `info_renda`
--
CREATE TABLE `info_renda` (
  `codigo` int(11) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `data_producao` date DEFAULT NULL,
  `quantidade` decimal(19,2) DEFAULT NULL,
  `valor_unitario` decimal(19,2) DEFAULT NULL,
  `item_producao_codigo` varchar(255) DEFAULT NULL,
  `visita_codigo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKhuax88f4b4kde0qco8sh80kq6` (`item_producao_codigo`),
  KEY `FKpvtpf1pu604l6y1cjn2f8esk4` (`visita_codigo`),
  CONSTRAINT `FKhuax88f4b4kde0qco8sh80kq6` FOREIGN KEY (`item_producao_codigo`) REFERENCES `item_producao` (`codigo`),
  CONSTRAINT `FKpvtpf1pu604l6y1cjn2f8esk4` FOREIGN KEY (`visita_codigo`) REFERENCES `visita` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

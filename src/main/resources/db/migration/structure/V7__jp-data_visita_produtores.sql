--
-- Table structure for table `visita_produtores`
--
CREATE TABLE `visita_produtores` (
  `visita_codigo` varchar(255) NOT NULL,
  `produtores_cpf` varchar(255) NOT NULL,
  KEY `FKsx7t5ngytqfkqqm9coxmbba8i` (`produtores_cpf`),
  KEY `FKcnxvwok5q4x48mv8sp1p2gh84` (`visita_codigo`),
  CONSTRAINT `FKcnxvwok5q4x48mv8sp1p2gh84` FOREIGN KEY (`visita_codigo`) REFERENCES `visita` (`codigo`),
  CONSTRAINT `FKsx7t5ngytqfkqqm9coxmbba8i` FOREIGN KEY (`produtores_cpf`) REFERENCES `persona` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

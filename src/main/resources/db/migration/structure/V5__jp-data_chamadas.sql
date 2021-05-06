--
-- Table structure for table `chamadas`
--
CREATE TABLE `chamadas` (
  `codigo` varchar(255) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `ocorrencia` varchar(255) DEFAULT NULL,
  `previsao_de_conclusao` date DEFAULT NULL,
  `servico` varchar(255) DEFAULT NULL,
  `servico_quitado_em` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `responsavel_cpf` varchar(255) DEFAULT NULL,
  `service_provided_id` varchar(255) NOT NULL,
  `call_id` varchar(255) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKggdf6sswpg7g8r9sk0005v7ra` (`responsavel_cpf`),
  KEY `FKq8cqct4qcuccd5p9giuqo0t2x` (`service_provided_id`),
  KEY `FKe77ickej08igw78tjve90fhe4` (`call_id`),
  CONSTRAINT `FKe77ickej08igw78tjve90fhe4` FOREIGN KEY (`call_id`) REFERENCES `visita` (`codigo`),
  CONSTRAINT `FKggdf6sswpg7g8r9sk0005v7ra` FOREIGN KEY (`responsavel_cpf`) REFERENCES `persona` (`cpf`),
  CONSTRAINT `FKq8cqct4qcuccd5p9giuqo0t2x` FOREIGN KEY (`service_provided_id`) REFERENCES `service_provided` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

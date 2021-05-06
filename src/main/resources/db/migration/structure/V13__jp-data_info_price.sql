--
-- Table structure for table `info_price`
--
CREATE TABLE `info_price` (
  `id` int(11) NOT NULL,
  `created` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `modified_by` varchar(255) NOT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `qtd_por_unid` int(11) DEFAULT '1',
  `valor` decimal(6,2) NOT NULL,
  `especificacao_id` varchar(255) DEFAULT NULL,
  `produtor_informante_cpf` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hogfx7c91ic2ihjde7agtr19` (`especificacao_id`),
  KEY `FKmptev9pckpjlw3bvqqpr0ilm6` (`produtor_informante_cpf`),
  CONSTRAINT `FK9hogfx7c91ic2ihjde7agtr19` FOREIGN KEY (`especificacao_id`) REFERENCES `prices_item` (`id`),
  CONSTRAINT `FKmptev9pckpjlw3bvqqpr0ilm6` FOREIGN KEY (`produtor_informante_cpf`) REFERENCES `persona` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

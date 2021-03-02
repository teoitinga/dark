CREATE TABLE `persona` (
  `cpf` varchar(255) NOT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `isenabbled` tinyint(1) DEFAULT '1',
  `endereco` varchar(255) DEFAULT NULL,
  `nascimento` date DEFAULT NULL,
  `nome` varchar(155) NOT NULL,
  `permissao` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cpf`),
  UNIQUE KEY `UK_bs5ppfl8asydenx5pfq2m9wh7` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `service_provided`
--
CREATE TABLE `service_provided` (
  `codigo` varchar(255) NOT NULL,
  `default_value` decimal(19,2) DEFAULT NULL,
  `descricao` varchar(155) NOT NULL,
  `referency` varchar(155) NOT NULL,
  `time_remaining` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

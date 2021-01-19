--
-- Table structure for table `info_renda`
--
ALTER TABLE `jp-data`.`visita`
CHANGE COLUMN `orientacao` `orientacao` LONGTEXT CHARACTER SET latin1 COLLATE latin1_general_ci NULL DEFAULT NULL,
CHANGE COLUMN `recomendacao` `recomendacao` LONGTEXT CHARACTER SET latin1 COLLATE latin1_general_ci NULL DEFAULT NULL,
CHANGE COLUMN `situacao` `situacao` LONGTEXT CHARACTER SET latin1 COLLATE latin1_general_ci NULL DEFAULT NULL
;


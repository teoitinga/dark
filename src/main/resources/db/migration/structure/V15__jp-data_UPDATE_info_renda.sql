--
-- Table structure for table `info_renda`
--
ALTER TABLE `jp-data`.`info_renda`
ADD COLUMN `area_imovel_principal` DECIMAL(19,2) NULL AFTER `visita_codigo`,
ADD COLUMN `qtd_propriedades` DECIMAL(19,2) NULL AFTER `area_imovel_principal`,
ADD COLUMN `membros_familia` DECIMAL(19,2) NULL AFTER `qtd_propriedades`,
ADD COLUMN `area_explorada` DECIMAL(19,2) NULL AFTER `membros_familia`
;


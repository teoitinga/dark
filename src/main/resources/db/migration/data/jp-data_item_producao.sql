CREATE DATABASE  IF NOT EXISTS `jp-data` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `jp-data`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 192.168.15.29    Database: jp-data
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `item_producao`
--

LOCK TABLES `item_producao` WRITE;
/*!40000 ALTER TABLE `item_producao` DISABLE KEYS */;
INSERT INTO `item_producao` VALUES ('ALGME','Aluguel de Máquinas e Equipamentos',1.00,'Aluguel de Máquinas e Equipamentos','Aluguel de Máquinas e Equipamentos','OUTRND'),('APSP','Aposentadoria do serviço Público',1.00,'Aposentadoria do serviço Público','Aposentadoria do serviço Público','OUTRND'),('ARRD','Arrendamento',1.00,'Arrendamento','Arrendamento','OUTRND'),('ARRZ','Arroz',210.00,'Arroz','Arroz','AGRO'),('AVI','Avicultura Integrada - Carne',210.00,'Avicultura Integrada - Carne','Avicultura Integrada - Carne','PEC'),('AVnI','Avicultura não Integrada - Carne',210.00,'Avicultura NÃO Integrada - Carne','Avicultura Integrada - Carne','PEC'),('BOIGRD','Boi Gordo > 16@',210.00,'Queijos','Bovinos - Carne','PEC'),('BOIMGR','Boi magro 11-14 @',210.00,'Queijos','Bovinos - Carne','PEC'),('BZMGR','Boi magro 6-8@',210.00,'Queijos','Bovinos - Carne','PEC'),('CONST','mão de obra diarista',1.00,'mão de obra diarista','mão de obra diarista','OUTRND'),('EMPRCNPJ','Renda de outras empresas',1.00,'Renda de outras empresas','Renda de outras empresas','OUTRND'),('EMPRR','Empregos no Meio Rural',1.00,'Empregos no Meio Rural','Empregos no Meio Rural','OUTRND'),('EMPURB','Empregos no Meio Urbano',1.00,'Empregos no Meio Urbano','Empregos no Meio Urbano','OUTRND'),('FARMAND','Farinha de Mandioca',210.00,'Farinha de Mandioca','Farinha de Mandioca','IND'),('FJ','Feijão',210.00,'Feijão','Feijão','AGRO'),('FRT','Frete/taxi',1.00,'Frete/taxi','Frete/taxi','OUTRND'),('FRTCAM','Frete de caminhão',1.00,'Frete de caminhão','Frete de caminhão','OUTRND'),('FRTTRSP','Transpote coletivo',1.00,'Transpote coletivo','Transpote coletivo','OUTRND'),('GLDC','Geleias e doces',210.00,'Geleias e doces','Geléias e Doces','IND'),('LEITE','Leite tipo C',210.00,'Leite tipo C','Bovinos - Leite','PEC'),('MC','Meação',1.00,'Meação','Meação','OUTRND'),('MELADO','Melaço de cana',210.00,'Melaço de cana','Derivados da Cana-de-açucar','IND'),('MLH','Milho',210.00,'Milho','Milho','AGRO'),('OLRGR','Produtos Olericolas em Geral',210.00,'Produtos Olericolas em Geral','Produtos Olericolas em Geral','AGRO'),('OTRND','Outras Atividades não Agropecuárias do Estabelecimento',1.00,'Outras Atividades não Agropecuárias do Estabelecimento','Outras Atividades não Agropecuárias do Estabelecimento','OUTRND'),('OVOS','Ovos',210.00,'Ovos','Ovos','PEC'),('PGRSC','Programas Sociais',1.00,'Programas Sociais','Programas Sociais','OUTRND'),('QJ','Queijos',210.00,'Queijos','Queijos','IND'),('RAP','Rapadura',180.00,'Derivados da Cana-de-açucar','Derivados da Cana-de-açucar','IND'),('RR','Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais',1.00,'Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais','Rendas não Rurais, Excluídos a Aposentadoria Rural e Outros Benefícios Sociais','OUTRND'),('SNI','Suinocultura Integrada - Carne',210.00,'Suinocultura Integrada - Carne','Suinocultura Integrada - Carne','PEC'),('SNnI','Suinocultura não Integrada - Carne',210.00,'Suinocultura NÃO Integrada - Carne','Suinocultura não Integrada - Carne','PEC'),('TMT','Tomate',210.00,'Tomate','Tomate','AGRO'),('VCDSC','Descarte de vacas',210.00,'Queijos','Bovinos - Carne','PEC'),('VNDMO','Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos',1.00,'Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos','Venda da Mão-de-Obra da Unidade Familiar para Outros Estabelecimentos','OUTRND');
/*!40000 ALTER TABLE `item_producao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-02 15:33:40

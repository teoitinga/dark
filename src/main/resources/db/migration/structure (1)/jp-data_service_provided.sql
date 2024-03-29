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
-- Table structure for table `service_provided`
--

DROP TABLE IF EXISTS `service_provided`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service_provided` (
  `codigo` varchar(255) NOT NULL,
  `default_value` decimal(19,2) DEFAULT NULL,
  `descricao` varchar(155) NOT NULL,
  `referency` varchar(155) NOT NULL,
  `time_remaining` int DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_provided`
--

LOCK TABLES `service_provided` WRITE;
/*!40000 ALTER TABLE `service_provided` DISABLE KEYS */;
INSERT INTO `service_provided` VALUES ('AGREC',0.00,'Assistência técnica em preparo e utilização de caldas.','agroecologia caldas',1),('AGREME',0.00,'Orientações Microorganismos eficientes','agroecologia Microorganismos eficientes',1),('AGRTEVAP',0.00,'Orientações tanque de evapotranspiração - TEVAP','Agroecologia TEVAP',1),('ANSOLO',0.00,'Encaminhamento de amostras de solo para análise','Encaminhar amostra de solo para análise',20),('ANSOLOINT',0.00,'Interpretação de análise de solo','Interpretação de análise de solo',1),('ASSJENT',0.00,'Elaboração de projeto para entidade/Associação','Assessoria - Elaboração de projeto para entidade/Associação',5),('ASSPSTC',0.00,'Assitência a prestação de contas a projeto elaborado','Assessoria - prestação de contas a projeto elaborado',5),('ATERBOV',0.00,'Orientações em bovinocultura','Orientações Bovinocultura',1),('ATERBOVIRRG',300.00,'Elaboração de projeto/orçamento para irrigação de pastagem/Forrageiras','Projeto de irrigação de pastagem/Forrageiras',10),('ATERBOVIRRGAC',300.00,'Acompanhamento em implantação de sistemas de irrigação de pastagem','Acompanhamento em implantação de sistemas de irrigação de pastagem',15),('ATERBOVPAST',0.00,'Assitencia técnica em adubação/interpretação de análise de solo','Assist. Pastagem adubação interpretação de analise de solos',1),('ATERBOVROT',300.00,'Dimensionamento de área para pastejo rotacionado','Projeto de Piquetes, pastejo rotacionado',15),('ATERBOVROTAC',300.00,'Acompanhamento em divisão de pastagem em piquetes','Acompanhamento em divisão de piquetes',7),('ATERCREDGER',0.00,'Orientações gerais sobre acesso a crédito rural','Crédito rural - orientações gerais',1),('ATERCULT',0.00,'Assistência técnica em culturas não relacionadas no planejamento','Assist. culturas',1),('ATERCULTFEIJAO',0.00,'Assistência técnica em cultura do feijão','Assist. cultura do feijão',1),('ATERCULTHORTA',0.00,'Assitencia técnica em produção de hortaliças','Assist. cultura de hortaliças',1),('ATERCULTMILHO',0.00,'Assistência técnica em cultura do milho','Assist. cultura do milho',1),('ATERMACAR',200.00,'Emissão de Cadastro ambiental rural','Elaboração de CAR',7),('ATERMACAR2V',50.00,'Segunda via de Cadastro ambiental rural','2 via de CAR',5),('ATERMACARRET',200.00,'Retificação de Cadastro ambiental rural','Retificação de CAR',7),('ATERMAFSSPT',0.00,'Assistência técnica prestando orientações gerais e, em meio ambiente','Assist. culturas',1),('ATERMAPRTNSC',0.00,'Orientações sobre oroteção de nascentes','Assist. proteção de nascentes',1),('ATERMARESIDSOLID',0.00,'Orientações sobre tratamento de resíduos sólidos','fossa séptica',1),('COBAN',150.00,'Acolhimento de propostas COBAN','Acolhimento de propostas COBAN',14),('CRBB',150.00,'BB PRONAF projeto de crédito rural','BB PRONAF projeto de crédito rural',5),('CRBBANCRD',0.00,'Análise prévia a cadastro no Banco do Brasil para acesso a crédito rural','BB Crédito rural Pronaf- Análise prévia de cliente',1),('CRBBCS',150.00,'BB CUSTEIO projeto de crédito rural','BB CUSTEIO projeto de crédito rural',5),('CRBBINVAG',150.00,'BB INVESTAGRO projeto de crédito rural','BB INVESTAGRO projeto de crédito rural',5),('CRBBINVAGCS',150.00,'BB CUSTEIO projeto de crédito rural','BB CUSTEIO projeto de crédito rural',5),('CRBBLOV',0.00,'Emissão de laudo de opinião de valor a produtor rural','Laudo de Opinião de Valor',2),('CRBBLPD',150.00,'BB laudo de prorrogação de dívidas','Laudo prorrogação de dívidas Banco do Brasil',5),('CRBBMDA',150.00,'BB MODERAGRO projeto de crédito rural','BB MODERAGRO projeto de crédito rural',5),('CRBBMDACS',150.00,'BB CUSTEIO projeto de crédito rural','BB CUSTEIO projeto de crédito rural',5),('CRBBPRN',150.00,'BB PRONAMP projeto de crédito rural','BB PRONAMP projeto de crédito rural',5),('CRBBPRNCS',150.00,'BB PRONAMP CUSTEIO projeto de crédito rural','BB PRONAMP CUSTEIO projeto de crédito rural',5),('CRSC',150.00,'SICOOB projeto de crédito rural','SICOOB projeto de crédito rural',5),('DAP',0.00,'Emissão de declaração de aptidão ao PRONAF a agricultor familiar','Emissão de DAP',2),('DAP2V',0.00,'Emissão de 2ª via de declaração de aptidão','DAP 2 via',2),('DAPCIRR',0.00,'Consulta a irregularidades na emissão de DAP','DAP - consulta irregularidade',1),('DAPDBL',0.00,'Levantamento e processo para desbloqueio de DAP','DAP - Desbloqueio',90),('DAPLEV',0.00,'Levantamento social para Emissão/Renovação de DAP','Levantamento social para Emissão/Renovação de DAP',2),('GMPBB',50.00,'Geomapa Banco do Brasil','Geomapa Banco do Brasil',2),('GMPSC',50.00,'Geomapa Sicoob','Geomapa Sicoob',2),('LAS',200.00,'Licenciamento ambiental','Licenciamento ambiental - CTF Ibama',7),('LMBB',150.00,'Elaboração de laudo de Limite de credito para o Banco do Brasil','BB Limite de credito',5),('LMSC',150.00,'Elaboração de laudo Laudo de vistoria de imóvel para o SICCOB','SICOOB Laudo de vistoria de imóvel',5),('MAATERBRR',0.00,'Orientações importância e dimensionamento de barraginha','Orientações importância e dimensionamento de barraginha',1),('MAATERCXFRC',0.00,'Orientações importância, dimensionamento e contrução de caixas de ferrocimento','Orientações importância, dimensionamento e contrução de caixas de ferrocimento',1),('MACUINSIG',200.00,'Elaboração/processso de Cadastro de uso insignificante','Outorga - Cadastro de uso insignificante',5),('MACUINSIG2V',0.00,'Emissão de 2ª via de cadastro de uso insignificante','2ª via de Cadastro de uso insignificante',2),('MALADISP',200.00,'Elaboração de Processo de dispensa de licenciamento ambiental','Licenciamento ambiental exploração agropecuária',14),('MALAIAPPLG',600.00,'Processo de licenciamento para limpeza de lagoas','Processo de licenciamento para limpeza de lagoas',30),('MAOUT',3200.00,'Processo de outorga águas superficiais','Outorga águas superficiais',720),('MARCMTAMB',600.00,'Processo de recurso em passivos ao meio ambiente','Recurso - multas meio ambiente',7),('MAROMS',200.00,'Processo de registro e porte de motoserra','Processo de registro e porte de motoserra',15),('PRGDKTHT2019',0.00,'Doação de Kit horta - Convênio 891349/2019 no ano de 2021','Kit Horta - Emenda Parlamentar 2019  Convênio 891349/2019 no ano de 2021',1),('PRGDKTHT2019V1',0.00,'Assistência técnica a beneficiário  Convênio 891349/2019 no ano de 2021','1ª Visita Kit Horta - Emenda Parlamentar',30),('PRGDSMF2020',0.00,'Doação de sementes de feijão recebidas por Emenda parlamentar no ano de 2020 - Convênio N° 891349/2019 MC/EMATER-MG','Sementes de Feijão - Emenda Parlamentar  Convênio 891349/2019 no ano de 2021',1),('PRGDSMF2020V1',0.00,'1ª Visita Técnica a beneficiário sementes de feijão - Convênio N° 891349/2019 MC/EMATER-MG','1ª Visita Sementes de feijão',30),('PRGDSMF2020V2',0.00,'2ª Visita Técnica a beneficiário sementes de feijão - Convênio N° 891349/2019 MC/EMATER-MG','2ª Visita Sementes de Feijão',60),('PRGDSMF2020V3',0.00,'3ª Visita Técnica a beneficiário sementes de feijão - Convênio N° 891349/2019 MC/EMATER-MG','3ª Visita Sementes de feijão',90),('PRGDSMM2020',0.00,'Doação de sementes de milho recebidas por Emenda parlamentar no ano de 2020 - Convênio N° 891349/2019 MC/EMATER-MG','Sementes de Milho - Emenda Parlamentar  Convênio 891349/2019 no ano de 2021',1),('PRGDSMM2020V1',0.00,'1ª Visita Técnica a beneficiário sementes de milho - Convênio N° 891349/2019 MC/EMATER-MG','1ª Visita Sementes de milho',30),('PRGDSMM2020V2',0.00,'1ª Visita Técnica a beneficiário sementes de milho - Convênio N° 891349/2019 MC/EMATER-MG','2ª Visita Sementes de milho',60),('PRGDSMM2020V3',0.00,'1ª Visita Técnica a beneficiário sementes de milho - Convênio N° 891349/2019 MC/EMATER-MG','3ª Visita Sementes de milho',90),('PRGMDSMH',0.00,'Doação de Sementes de hortaliças pelo programa municipal','Programa Municipal - Sementes de hortaliças',1);
/*!40000 ALTER TABLE `service_provided` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-02 15:16:49

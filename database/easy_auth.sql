-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: easy_auth
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登入时间',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（1为正常）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'2507544221','$2a$10$Cgo.4SQjY1Y.rdMO759igOVAOD0Mbv1ogtKn1WJ4bYed6lx1UhGG2','2507544221@qq.com','2024-02-26 13:02:34','2024-02-26 13:02:34',1),(2,'lzc666','$2a$10$Cgo.4SQjY1Y.rdMO759igOVAOD0Mbv1ogtKn1WJ4bYed6lx1UhGG2',NULL,'2024-02-26 13:02:34','2024-02-26 13:02:34',1),(3,'wbq888','$2a$10$Cgo.4SQjY1Y.rdMO759igOVAOD0Mbv1ogtKn1WJ4bYed6lx1UhGG2',NULL,'2024-02-26 13:02:34','2024-02-26 13:02:34',1),(4,'test56789','$2a$10$wt7s54iDet4QOfBw5Jmuxe/nkmUS0q47da.rhINp2nhiIqLYLoRDS','myemail@test.com','2024-02-29 09:35:17','2024-02-29 09:35:17',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role`
--

DROP TABLE IF EXISTS `employee_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL COMMENT '员工id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role`
--

LOCK TABLES `employee_role` WRITE;
/*!40000 ALTER TABLE `employee_role` DISABLE KEYS */;
INSERT INTO `employee_role` VALUES (1,1,1),(2,2,2),(3,3,3),(14,4,2),(15,4,3);
/*!40000 ALTER TABLE `employee_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `http_method` varchar(32) DEFAULT NULL COMMENT '请求方式',
  `path` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `resource_http_method_path_uindex` (`http_method`,`path`) COMMENT '组合唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,'用户列表','GET','/admin/user/list','根据用户名获取用户','2024-02-27 15:11:55','2024-03-04 20:45:09'),(2,'添加用户','POST','/admin/user',NULL,'2024-02-27 15:11:55','2024-02-27 15:11:55'),(3,'编辑用户','PUT','/admin/user',NULL,'2024-02-27 15:11:55','2024-02-27 15:11:55'),(4,'切换用户状态','PUT','/admin/user/status',NULL,'2024-02-27 15:11:55','2024-02-29 08:49:06'),(5,'资源列表','GET','/admin/resource/list','资源分页查询','2024-02-28 09:03:04','2024-02-28 09:03:04'),(6,'删除资源','DELETE','/admin/resource','根据ID删除','2024-02-28 09:03:04','2024-02-28 09:03:16'),(7,'修改资源','PUT','/admin/resource',NULL,'2024-02-28 09:05:57','2024-02-28 09:05:57'),(8,'添加资源','POST','/admin/resource',NULL,'2024-02-28 09:07:37','2024-02-28 09:07:37'),(9,'资源条件查询','GET','/admin/resource/conditionSearch','条件分页查询资源','2024-02-28 09:50:15','2024-03-04 20:50:47'),(10,'添加员工','POST','/admin/employee',NULL,'2024-02-29 14:41:40','2024-02-29 14:41:40'),(11,'编辑员工','PUT','/admin/employee',NULL,'2024-02-29 14:41:40','2024-02-29 14:41:40'),(12,'员工列表','GET','/admin/employee/list',NULL,'2024-02-29 14:41:40','2024-02-29 14:41:40'),(13,'员工条件查询','GET','/admin/employee/conditionSearch','动态条件查询员工','2024-02-29 14:41:40','2024-02-29 14:41:40'),(14,'用户分页条件查询','GET','/admin/user/conditionSearch','动态条件查询用户','2024-03-04 20:45:09','2024-03-04 20:45:09'),(15,'角色权限列表','GET','/admin/rolePermission/list',NULL,'2024-03-04 20:50:47','2024-03-04 20:50:47'),(16,'角色权限添加','POST','/admin/rolePermission/add',NULL,'2024-03-04 20:50:47','2024-03-04 20:50:47'),(17,'角色权限删除','PUT','/admin/rolePermission/delete',NULL,'2024-03-04 20:50:47','2024-03-04 20:50:47'),(18,'用户表单登入','POST','/user/auth/formLogin',NULL,'2024-03-05 09:23:06','2024-03-05 09:23:06');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态，1->启用，0->禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'超级管理员','拥有所有权限',1),(2,'客服','操作一般用户',1),(3,'后台编辑','操作资源',1),(4,'普通用户','基础使用',1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `role_id` int NOT NULL COMMENT '角色id',
  `resource_id` int NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(2,1),(2,2),(2,3),(2,4),(3,5),(3,6),(3,7),(3,8),(3,9);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登入时间',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `status` tinyint DEFAULT '1' COMMENT '状态（1为正常）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$4rZs3LxMCR9gvyBYisksqevvGqxdgLKQtRgVsmK4TiUPxHIZR5vXK','2507544221@qq.com','2024-02-24 17:21:48','2024-02-24 17:21:48',1),(2,'2507544221','$2a$10$YwCGD3MpCWj1wN2ymo0Dk.9nnbsg.es3SmoC3g66nEdzsl1HraxSu',NULL,'2024-02-26 13:54:03','2024-02-26 13:54:03',1),(3,'xyt666','$2a$10$Cgo.4SQjY1Y.rdMO759igOVAOD0Mbv1ogtKn1WJ4bYed6lx1UhGG2',NULL,'2024-02-26 13:54:03','2024-02-26 13:54:03',1),(7,'emailtest666','$2a$10$2VXqsbAeBDYZH684VqVJVO3OGn2L2qQNpQPb0SA6Gx1p1/ns2jiJe','2932089752@qq.com','2024-03-07 10:02:04','2024-03-07 10:02:04',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,4),(2,2,4),(3,3,4),(4,7,4);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-08 13:22:34

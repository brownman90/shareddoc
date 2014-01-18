-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.33a-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 share 的数据库结构
CREATE DATABASE IF NOT EXISTS `share` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `share`;


-- 导出  表 share.doc 结构
CREATE TABLE IF NOT EXISTS `doc` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `committer` varchar(50) DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- 正在导出表  share.doc 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `doc` DISABLE KEYS */;
INSERT INTO `doc` (`id`, `name`, `type`, `size`, `committer`, `time`, `path`) VALUES
	(1, 'test', 'doc', 10000, 'czc', '2014-01-13', '/test'),
	(2, 'ert', 'doc', 10000, 'czc', '2014-01-13', '/test'),
	(3, 'favicon', 'ico', 6518, 'czc', '2014-1-18', NULL);
/*!40000 ALTER TABLE `doc` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

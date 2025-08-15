-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 15, 2025 at 12:25 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pahana-edu`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
CREATE TABLE IF NOT EXISTS `bills` (
  `bill_id` int NOT NULL AUTO_INCREMENT,
  `account_number` int NOT NULL,
  `billing_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `units_consumed` int NOT NULL DEFAULT '0',
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `account_number` (`account_number`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`bill_id`, `account_number`, `billing_date`, `units_consumed`, `amount`) VALUES
(9, 2, '2025-08-11 16:17:11', 0, 1364.00),
(5, 3, '2025-08-10 16:53:18', 0, 764.00),
(6, 3, '2025-08-10 16:53:35', 0, 600.00),
(7, 1, '2025-08-10 17:00:02', 0, 764.00),
(8, 3, '2025-08-10 17:00:46', 0, 764.00);

-- --------------------------------------------------------

--
-- Table structure for table `bill_items`
--

DROP TABLE IF EXISTS `bill_items`;
CREATE TABLE IF NOT EXISTS `bill_items` (
  `bill_item_id` int NOT NULL AUTO_INCREMENT,
  `bill_id` int NOT NULL,
  `item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`bill_item_id`),
  KEY `bill_id` (`bill_id`),
  KEY `item_id` (`item_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bill_items`
--

INSERT INTO `bill_items` (`bill_item_id`, `bill_id`, `item_id`, `quantity`, `unit_price`) VALUES
(10, 9, 1, 1, 764.00),
(2, 3, 1, 1, 764.00),
(9, 9, 2, 3, 200.00),
(4, 5, 1, 1, 764.00),
(5, 6, 2, 1, 200.00),
(6, 6, 2, 2, 200.00),
(7, 7, 1, 1, 764.00),
(8, 8, 1, 1, 764.00);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `account_number` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `units_consumed` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_number`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`account_number`, `name`, `address`, `telephone`, `units_consumed`, `created_at`) VALUES
(1, 'punura', '149/4c', '119', 0, '2025-07-19 06:08:03'),
(2, 'chandupa', '726', '1990', 0, '2025-07-19 06:33:26'),
(3, 'customer1', '234', '242', 0, '2025-07-19 06:34:33'),
(4, 'customer1', 'vve', '234', 0, '2025-07-19 06:35:45'),
(5, 'punura', '149/4c', '119', 0, '2025-07-19 09:19:35'),
(6, 'punura', '149/4c', '1990', 0, '2025-07-19 09:19:46'),
(7, 'new', 'test 2', '123456789', 0, '2025-08-10 10:53:00');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `item_description` text,
  `unit_price` decimal(10,2) NOT NULL,
  `stock_quantity` int DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_name`, `item_description`, `unit_price`, `stock_quantity`) VALUES
(1, 'Beast Quest', 'Adventure', 764.00, 87624),
(2, 'Harry Potter', 'book 2', 200.00, 10);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin@123');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

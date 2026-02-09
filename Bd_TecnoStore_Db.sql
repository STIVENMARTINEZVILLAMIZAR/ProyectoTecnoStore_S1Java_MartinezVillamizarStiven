SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `tecnostore_db`;
USE `tecnostore_db`;

DROP TABLE IF EXISTS `detalle_ventas`;
DROP TABLE IF EXISTS `ventas`;
DROP TABLE IF EXISTS `clientes`;
DROP TABLE IF EXISTS `celulares`;
DROP TABLE IF EXISTS `marca`;
DROP TABLE IF EXISTS `gama`;

CREATE TABLE `marca` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `nombre` VARCHAR(50) NOT NULL UNIQUE,
  `descripcion` VARCHAR(200),
  `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `gama` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `nombre` VARCHAR(50) NOT NULL UNIQUE,
  `descripcion` VARCHAR(200),
  `factor_precio` DECIMAL(3, 2) DEFAULT 1.0,
  `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `celulares` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `marca` VARCHAR(50) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  `precio` DECIMAL(10, 2) NOT NULL,
  `stock` INT NOT NULL,
  `sistema_operativo` VARCHAR(50) NOT NULL,
  `gama` VARCHAR(20) NOT NULL,
  `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `clientes` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `nombre` VARCHAR(100) NOT NULL,
  `identificacion` VARCHAR(20) UNIQUE NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(15) NOT NULL,
  `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ventas` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `id_cliente` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `total` DECIMAL(10, 2) NOT NULL,
  `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`id_cliente`) REFERENCES `clientes`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `detalle_ventas` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `id_venta` INT NOT NULL,
  `id_celular` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `subtotal` DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (`id_venta`) REFERENCES `ventas`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`id_celular`) REFERENCES `celulares`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `marca` (nombre, descripcion) VALUES
('Apple', 'Fabricante de dispositivos iOS'),
('Samsung', 'Fabricante de dispositivos Android'),
('Xiaomi', 'Fabricante chino de tecnología'),
('Motorola', 'Fabricante estadounidense'),
('OnePlus', 'Fabricante de smartphones premium');

INSERT INTO `gama` (nombre, descripcion, factor_precio) VALUES
('Baja', 'Gama baja - Entrada', 0.95),
('Media', 'Gama media - Estándar', 1.0),
('Alta', 'Gama alta - Premium', 1.05);

INSERT INTO `celulares` (marca, modelo, precio, stock, sistema_operativo, gama) VALUES
('Apple', 'iPhone 15', 1299.99, 15, 'iOS', 'Alta'),
('Samsung', 'Galaxy A54', 449.99, 25, 'Android', 'Media'),
('Xiaomi', 'Redmi Note 12', 299.99, 30, 'Android', 'Baja'),
('Apple', 'iPhone 14', 999.99, 10, 'iOS', 'Alta'),
('Samsung', 'Galaxy S23', 899.99, 12, 'Android', 'Alta');

INSERT INTO `clientes` (nombre, identificacion, correo, telefono) VALUES
('Juan Pérez', '1234567890', 'juan@email.com', '3001234567'),
('María García', '0987654321', 'maria@email.com', '3109876543'),
('Carlos López', '1122334455', 'carlos@email.com', '3201122334');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
CREATE DATABASE IF NOT EXISTS db_ecommerce_caruajulcajorge
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE db_ecommerce_caruajulcajorge;

DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS productos;

CREATE TABLE productos (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre         VARCHAR(100)  NOT NULL,
  categoria      VARCHAR(50)   NOT NULL,
  precio_compra  DECIMAL(10,2) NOT NULL,
  precio_venta   DECIMAL(10,2) NOT NULL,
  stock          INT           NOT NULL DEFAULT 0,
  marca          VARCHAR(50),
  codigo_barras  VARCHAR(20)   UNIQUE,
  fecha_ingreso  DATE          NOT NULL
) ENGINE=InnoDB;

CREATE TABLE usuarios (
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  username  VARCHAR(50)  NOT NULL UNIQUE,
  password  VARCHAR(100) NOT NULL,
  nombre    VARCHAR(100) NOT NULL,
  rol       VARCHAR(20)  NOT NULL DEFAULT 'ROLE_ADMIN'
) ENGINE=InnoDB;

INSERT INTO productos (nombre, categoria, precio_compra, precio_venta, stock, marca, codigo_barras, fecha_ingreso) VALUES
('Laptop IdeaPad 3 16GB',   'Laptops',     2100.00, 2599.00, 10, 'Lenovo',   '7750001000011', '2026-11-01'),
('Smartphone Galaxy A55',   'Smartphones', 1150.00, 1499.00, 25, 'Samsung',  '7750001000028', '2026-11-03'),
('Audifonos WH-CH720N',     'Audio',        280.00,  399.00, 30, 'Sony',     '7750001000035', '2026-11-05'),
('Monitor 24 IPS 100Hz',    'Monitores',    390.00,  529.00, 12, 'LG',       '7750001000042', '2026-11-07'),
('Teclado Mecanico Kumara', 'Accesorios',   120.00,  169.00, 40, 'Redragon', '7750001000059', '2026-11-09');

-- Usuario: jorge / contraseña: jorge123
INSERT INTO usuarios (username, password, nombre, rol) VALUES
('jorge', '$2a$10$aqgP.jK9ID5Cl27YqvR1tOaxdVRmHovp0HoXcGa/iD0/QmJ4YnyLe', 'Jorge Caruajulca', 'ROLE_ADMIN');
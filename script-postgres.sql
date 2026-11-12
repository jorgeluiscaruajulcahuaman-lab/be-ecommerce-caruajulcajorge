-- =========================================
-- script-postgres.sql  (PostgreSQL - Render)
-- =========================================
CREATE TABLE IF NOT EXISTS productos (
  id             BIGSERIAL PRIMARY KEY,
  nombre         VARCHAR(100)  NOT NULL,
  categoria      VARCHAR(50)   NOT NULL,
  precio_compra  NUMERIC(10,2) NOT NULL,
  precio_venta   NUMERIC(10,2) NOT NULL,
  stock          INTEGER       NOT NULL DEFAULT 0,
  marca          VARCHAR(50),
  codigo_barras  VARCHAR(20)   UNIQUE,
  fecha_ingreso  DATE          NOT NULL
);

CREATE TABLE IF NOT EXISTS usuarios (
  id        BIGSERIAL PRIMARY KEY,
  username  VARCHAR(50)  NOT NULL UNIQUE,
  password  VARCHAR(100) NOT NULL,
  nombre    VARCHAR(100) NOT NULL,
  rol       VARCHAR(20)  NOT NULL DEFAULT 'ROLE_ADMIN'
);

INSERT INTO productos (nombre, categoria, precio_compra, precio_venta, stock, marca, codigo_barras, fecha_ingreso) VALUES
('Laptop IdeaPad 3 16GB',   'Laptops',     2100.00, 2599.00, 10, 'Lenovo',   '7750001000011', '2026-11-01'),
('Smartphone Galaxy A55',   'Smartphones', 1150.00, 1499.00, 25, 'Samsung',  '7750001000028', '2026-11-03'),
('Audifonos WH-CH720N',     'Audio',        280.00,  399.00, 30, 'Sony',     '7750001000035', '2026-11-05')
ON CONFLICT (codigo_barras) DO NOTHING;

-- Usuario: jorge / contraseña: jorge123
INSERT INTO usuarios (username, password, nombre, rol) VALUES
('jorge', '$2a$10$aqgP.jK9ID5Cl27YqvR1tOaxdVRmHovp0HoXcGa/iD0/QmJ4YnyLe', 'Jorge Caruajulca', 'ROLE_ADMIN')
ON CONFLICT (username) DO NOTHING;

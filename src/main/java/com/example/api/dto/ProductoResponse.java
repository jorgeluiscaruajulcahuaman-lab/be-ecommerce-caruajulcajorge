package com.example.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

// Lo que la API DEVUELVE
public record ProductoResponse(
    Long id,
    String nombre,
    String categoria,
    BigDecimal precioCompra,
    BigDecimal precioVenta,
    BigDecimal ganancia,
    Integer stock,
    String marca,
    String codigoBarras,
    LocalDate fechaIngreso
) {}

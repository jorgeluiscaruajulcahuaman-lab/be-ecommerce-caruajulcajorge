package com.example.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

public record ProductoRequest(
    @NotBlank(message = "El nombre es obligatorio") @Size(max = 100) String nombre,
    @NotBlank(message = "La categoría es obligatoria") @Size(max = 50) String categoria,
    @NotNull(message = "El precio de compra es obligatorio") @DecimalMin("0.00") BigDecimal precioCompra,
    @NotNull(message = "El precio de venta es obligatorio") @DecimalMin("0.00") BigDecimal precioVenta,
    @NotNull(message = "El stock es obligatorio") @Min(0) Integer stock,
    @Size(max = 50) String marca,
    @Size(max = 20) String codigoBarras,
    LocalDate fechaIngreso
) {}
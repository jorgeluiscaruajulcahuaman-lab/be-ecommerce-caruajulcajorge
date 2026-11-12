package com.example.api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.api.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    
    List<Producto> buscarPorCategoria(@Param("categoria") String categoria);
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);
    List<Producto> buscarStockBajo(@Param("minimo") Integer minimo);

    
    @Query("SELECT p FROM Producto p WHERE p.precioVenta BETWEEN :min AND :max ORDER BY p.precioVenta")
    List<Producto> buscarPorRangoPrecio(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    Optional<Producto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);
}

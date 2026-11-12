package com.example.api.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.api.dto.ProductoRequest;
import com.example.api.dto.ProductoResponse;
import com.example.api.service.ProductoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ProductoResponse obtener(@PathVariable("id") Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable("id") Long id, @Valid @RequestBody ProductoRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProductoResponse> porCategoria(@PathVariable("categoria") String categoria) {
        return service.porCategoria(categoria);
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> porNombre(@RequestParam("nombre") String nombre) {
        return service.porNombre(nombre);
    }

    @GetMapping("/stock-bajo")
    public List<ProductoResponse> stockBajo(@RequestParam(name = "minimo", defaultValue = "10") Integer minimo) {
        return service.stockBajo(minimo);
    }

    @GetMapping("/rango-precio")
    public List<ProductoResponse> rangoPrecio(@RequestParam("min") BigDecimal min,
                                              @RequestParam("max") BigDecimal max) {
        return service.rangoPrecio(min, max);
    }
}

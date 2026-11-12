package com.example.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.api.dto.ProductoRequest;
import com.example.api.dto.ProductoResponse;
import com.example.api.entity.Producto;
import com.example.api.exception.RecursoNoEncontradoException;
import com.example.api.exception.ReglaNegocioException;
import com.example.api.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtener(Long id) {
        return toResponse(buscar(id));
    }

    public ProductoResponse crear(ProductoRequest req) {
        validarPrecios(req);
        if (req.codigoBarras() != null && repo.existsByCodigoBarras(req.codigoBarras())) {
            throw new ReglaNegocioException("Ya existe un producto con el código " + req.codigoBarras());
        }
        Producto p = new Producto();
        copiar(req, p);
        return toResponse(repo.save(p));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest req) {
        validarPrecios(req);
        Producto p = buscar(id);
        if (req.codigoBarras() != null
                && !req.codigoBarras().equals(p.getCodigoBarras())
                && repo.existsByCodigoBarras(req.codigoBarras())) {
            throw new ReglaNegocioException("Ya existe un producto con el código " + req.codigoBarras());
        }
        copiar(req, p);
        return toResponse(repo.save(p));
    }

    public void eliminar(Long id) {
        repo.delete(buscar(id));
    }

    // ---- Búsquedas (usan las Named Queries y el JPQL del repositorio) ----
    @Transactional(readOnly = true)
    public List<ProductoResponse> porCategoria(String categoria) {
        return repo.buscarPorCategoria(categoria).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> porNombre(String nombre) {
        return repo.buscarPorNombre(nombre).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> stockBajo(Integer minimo) {
        return repo.buscarStockBajo(minimo).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> rangoPrecio(BigDecimal min, BigDecimal max) {
        return repo.buscarPorRangoPrecio(min, max).stream().map(this::toResponse).toList();
    }

    
    private Producto buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
    }

    
    private void validarPrecios(ProductoRequest req) {
        if (req.precioVenta().compareTo(req.precioCompra()) < 0) {
            throw new ReglaNegocioException("El precio de venta no puede ser menor al precio de compra");
        }
    }

    private void copiar(ProductoRequest r, Producto p) {
        p.setNombre(r.nombre());
        p.setCategoria(r.categoria());
        p.setPrecioCompra(r.precioCompra());
        p.setPrecioVenta(r.precioVenta());
        p.setStock(r.stock());
        p.setMarca(r.marca());
        p.setCodigoBarras(r.codigoBarras());
        p.setFechaIngreso(r.fechaIngreso() != null ? r.fechaIngreso() : LocalDate.now());
    }

    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(
                p.getId(), p.getNombre(), p.getCategoria(),
                p.getPrecioCompra(), p.getPrecioVenta(),
                p.getPrecioVenta().subtract(p.getPrecioCompra()),
                p.getStock(), p.getMarca(), p.getCodigoBarras(), p.getFechaIngreso());
    }
}

package com.cafeteria.crud.producto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public ResponseEntity<Object> eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);

            return ResponseEntity.ok().body("Producto eliminado con éxito");
        } else {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }
    }

    public ResponseEntity<Object> newProducto(Producto producto) {

         if (producto.getFecha() == null) {
        producto.setFecha(LocalDate.now()); 
    }
        HashMap<String, Object> response = new HashMap<>();
        try {
            // Validar si el producto ya existe
            Optional<Producto> existingProducto = productoRepository.findProductoByNombre(producto.getNombre());
            if (existingProducto.isPresent() && producto.getId() == null) {
                response.put("error", true);
                response.put("message", "El producto ya existe con el nombre: " + producto.getNombre());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            // Guardar el producto
            Producto savedProducto = productoRepository.save(producto);
            response.put("error", false);
            response.put("message", producto.getId() == null ? "Producto registrado con éxito" : "Producto actualizado con éxito");
            response.put("data", savedProducto);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataIntegrityViolationException e) {
            // Manejo de errores por restricción de unicidad
            response.put("error", true);
            response.put("message", "No se pudo guardar el producto. El nombre ya está registrado.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // Manejo genérico de errores
            response.put("error", true);
            response.put("message", "Ocurrió un error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

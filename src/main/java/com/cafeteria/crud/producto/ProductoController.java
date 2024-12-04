package com.cafeteria.crud.producto;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/productos")
@CrossOrigin(origins = "http://127.0.0.7:5500")
public class ProductoController {

    	private final ProductoService productoService;
          @Autowired
          private ProductoRepository productoRepository;

        @Autowired
        public ProductoController(ProductoService productoService) {
            this.productoService = productoService;
        }


        @GetMapping
         public List<Producto> getProductos() {
            return productoService.getProductos();
         }

         @PostMapping
         public ResponseEntity <Object> registrarProducto(@RequestBody Producto producto){
                return this.productoService.newProducto(producto);
         }

         @PutMapping
         public ResponseEntity<Object> actualizarProducto(@RequestBody Producto producto){
                return this.productoService.newProducto(producto);
         }

         @DeleteMapping("/{id}")
            public ResponseEntity<Object> eliminarProducto(@PathVariable Long id) {
            return this.productoService.eliminarProducto(id);
}


@GetMapping("/{id}")
public ResponseEntity<Object> obtenerProductoPorId(@PathVariable Long id) {
    // Busca el producto por ID
    Optional<Producto> producto = productoRepository.findById(id);

    if (producto.isPresent()) {
        return ResponseEntity.ok(producto.get()); // Retorna el producto si existe
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }
}

@PutMapping("/{id}")
public ResponseEntity<Object> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
    Optional<Producto> productoExistente = productoRepository.findById(id);

    if (productoExistente.isPresent()) {
        Producto producto = productoExistente.get();
        // Actualiza los valores
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setCantidad(productoActualizado.getCantidad());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setFecha(productoActualizado.getFecha());

        // Guarda el producto actualizado
        productoRepository.save(producto);
        return ResponseEntity.ok(producto);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }
}

         }
         
    


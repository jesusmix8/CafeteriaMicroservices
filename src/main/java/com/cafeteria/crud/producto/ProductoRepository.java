package com.cafeteria.crud.producto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository  <Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    Optional<Producto> findProductoByNombre(@Param("nombre") String nombre);
}

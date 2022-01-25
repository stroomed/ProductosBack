package com.ricardo.Productos.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricardo.Productos.Entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	Optional<Producto> findByNombreContaining(String nombre);

	boolean existsByNombre(String nombre);
}

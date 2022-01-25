package com.ricardo.Productos.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.Productos.Entity.Producto;
import com.ricardo.Productos.Repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {

	@Autowired
	ProductoRepository prodRep;

	public List<Producto> listar() {
		return prodRep.findAll();
	}

	public Optional<Producto> getOne(Integer id) {
		return prodRep.findById(id);
	}

	public Optional<Producto> getByNombre(String nombre) {
		return prodRep.findByNombreContaining(nombre);
	}

	public void save(Producto producto) {
		prodRep.save(producto);
	}
	
	public void delete(Integer id) {
		prodRep.deleteById(id);
	}
	
	public boolean existsById(Integer id) {
		return prodRep.existsById(id);
	}
	
	public boolean existsByNombre(String nombre) {
		return prodRep.existsByNombre(nombre);
	}
}

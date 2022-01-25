package com.ricardo.Productos.Controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ricardo.Productos.Entity.Producto;
import com.ricardo.Productos.ProductoDTO.*;
import com.ricardo.Productos.Service.ProductoService;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

	@Autowired
	ProductoService prodServ;

	@GetMapping("/listar")
	public ResponseEntity<List<Producto>> lista() {
		List<Producto> lista = prodServ.listar();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/detalles/{id}")
	public ResponseEntity<Producto> getById(@PathVariable("id") Integer id) {
		if (!prodServ.existsById(id)) {
			return new ResponseEntity(new Mensaje("No está registrado"), HttpStatus.NOT_FOUND);
		} else {
			Producto producto = prodServ.getOne(id).get();
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}

	}

	@GetMapping("/detalles/{nombre}")
	public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre) {
		if (!prodServ.existsByNombre(nombre)) {
			return new ResponseEntity(new Mensaje("Producto no registrado"), HttpStatus.NOT_FOUND);
		} else {
			Producto producto = prodServ.getByNombre(nombre).get();
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO proDto) {
		if (StringUtils.isBlank(proDto.getNombre())) {
			return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		} else {
			if (proDto.getPrecio() < 0) {
				return new ResponseEntity(new Mensaje("El no puede ser menor a 0"), HttpStatus.BAD_REQUEST);
			} else {
				if (prodServ.existsByNombre(proDto.getNombre())) {
					return new ResponseEntity(new Mensaje("El producto ya está registrado"), HttpStatus.BAD_REQUEST);
				} else {
					Producto producto = new Producto(proDto.getNombre(), proDto.getPrecio());
					prodServ.save(producto);
					return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
				}
			}
		}
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editarProducto(@PathVariable("id") Integer id, @RequestBody ProductoDTO proDto){
		if (!prodServ.existsById(id)) {
			return new ResponseEntity(new Mensaje("No está registrado"), HttpStatus.NOT_FOUND);
		} else {
			if(prodServ.existsByNombre(proDto.getNombre()) && prodServ.getByNombre(proDto.getNombre()).get().getId() != id) {
				return new ResponseEntity(new Mensaje("El producto ya existe"), HttpStatus.NOT_FOUND);
			} else {
				if(StringUtils.isBlank(proDto.getNombre())) {
					return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.NOT_FOUND);
				} else {
					if(proDto.getPrecio() == null || proDto.getPrecio() < 0) {
						return new ResponseEntity(new Mensaje("El precio debe ser mayor a 0"), HttpStatus.NOT_FOUND);
					} else {
						Producto prod = prodServ.getOne(id).get();
						prod.setNombre(proDto.getNombre());
						prod.setPrecio(proDto.getPrecio());
						prodServ.save(prod);
						return new ResponseEntity(new Mensaje(prod.getNombre() + "ha sido registrado con el precio $" + prod.getPrecio()), HttpStatus.OK);
					}
				}
			}
		}
	}
	
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> borrarProducto(@PathVariable("id") Integer id) {
		if(!prodServ.existsById(id)) {
			return new ResponseEntity(new Mensaje("No está registrado"), HttpStatus.NOT_FOUND);
		} else {
			prodServ.delete(id);
			return new ResponseEntity(new Mensaje("Producto eliminado"), HttpStatus.OK);
		}
	}
}

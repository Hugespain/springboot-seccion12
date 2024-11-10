package com.andres.curso.springboot.app.springbootcrud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andres.curso.springboot.app.springbootcrud.entities.Product;
import com.andres.curso.springboot.app.springbootcrud.services.ProductService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController  //Con esta anotación de  indico que es un controlador rest cuyos métodos devolverán datos en JSON
@RequestMapping("api/products") //Esta es una anotación de mapeo que establece una ruta base
public class ProductController{

@Autowired
private ProductService service; //Estoy inyectando el servicio como un atributo que será usado por el controlador

//Método del servicio mapeado a ruta base que use el método del repositorio findAll();
@GetMapping //Me mantengo en la ruta base
public List<Product> listAllProducts(){
    return service.findAll();
}

//Método del servicio para encontrar por id y mapeado a ruta especifica + ResponseEntity
@GetMapping("/{id}")
public ResponseEntity<?> view(@PathVariable Long id) {
    Optional<Product> productOptional= service.findById(id);//Como el servicio .findById devuelve un optional debo usar un optional 
    if(productOptional.isPresent()){
        return ResponseEntity.ok(productOptional.get());//Devuelvo codigo 200 + product en el body
    }
    return ResponseEntity.notFound().build();//Devuelvo 404 y el build simplemente añade la respuesta
}


//Método para añadir nuevo producto
//Como es crear uno nuevo es la anotación post
@PostMapping
public ResponseEntity<Product> create(@RequestBody Product product){ //Voy a devolver una respuesta http completa incluyendo código de estado y cuerpo;donde va el objeto que se devuelve
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));//Entidad Guardada: Devuelve la entidad con todos los cambios aplicados, incluyendo el ID si es nuevo.
    //Tipo de Retorno: La misma clase de la entidad que se guarda.
}


//Método que updatea
//Como updatea tiene que buscar ver si lo encuentra, si lo encuentra código de estado 200ok + guardarproducto si no pues codigo de error
@PutMapping("/{id}")
public ResponseEntity<Product> update (@PathVariable Long id, @RequestBody Product product){
    Optional<Product> productOptional = service.update(id, product);
    if (productOptional.isPresent()) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(productOptional.get());
    }
   return ResponseEntity.notFound().build();

}
}








// @RestController
// @RequestMapping("/api/products")
// public class ProductController {
    
//     @Autowired
//     private ProductService service;

//     @GetMapping
//     public List<Product> list() {
//         return service.findAll();
//     }
    
//     @GetMapping("/{id}")
//     public ResponseEntity<?> view(@PathVariable Long id) {
//         Optional<Product> productOptional = service.findById(id);
//         if (productOptional.isPresent()) {
//             return ResponseEntity.ok(productOptional.orElseThrow());
//         }
//         return ResponseEntity.notFound().build();
//     }
    
//     @PostMapping
//     public ResponseEntity<Product> create(@RequestBody Product product) {
//         return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
//         Optional<Product> productOptional = service.update(id, product);
//         if (productOptional.isPresent()) {
//             return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
//         }
//         return ResponseEntity.notFound().build();
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> delete(@PathVariable Long id) {
//         Optional<Product> productOptional = service.delete(id);
//         if (productOptional.isPresent()) {
//             return ResponseEntity.ok(productOptional.orElseThrow());
//         }
//         return ResponseEntity.notFound().build();
//     }
// }

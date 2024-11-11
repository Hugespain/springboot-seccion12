package com.andres.curso.springboot.app.springbootcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;



@RestController  //Con esta anotación de  indico que es un controlador rest cuyos métodos devolverán datos en JSON
@RequestMapping("/api/products") //Esta es una anotación de mapeo que establece una ruta base
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
public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){ //es necesario para que Spring convierta automáticamente el cuerpo de la solicitud (por ejemplo, un JSON) en un objeto Java(Product en este caso)
    if (result.hasFieldErrors()) {
        return validation(result);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));//Entidad Guardada: Devuelve la entidad con todos los cambios aplicados, incluyendo el ID si es nuevo.
    //Tipo de Retorno: La misma clase de la entidad que se guarda.
}


//Método que updatea
//Como updatea tiene que buscar ver si lo encuentra, si lo encuentra código de estado 200ok + guardarproducto si no pues codigo de error
@PutMapping("/{id}")
public ResponseEntity<?> update ( @Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){
    if (result.hasFieldErrors()) {
        return validation(result);
            }
            Optional<Product> productOptional = service.update(id, product);
            if (productOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.get());
            }
           return ResponseEntity.notFound().build();
        
        }
        
        
        
        @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete (@PathVariable Long id) { //Si no usamos aquí RequestBody es porque para eliminar un producto, solo necesitamos el id del producto a eliminar, que es parte de la URL, no del cuerpo de la solicitud.
    Optional<Product> productOptional=service.delete(id);//Si el producto es encontrado y eliminado, se devuelve en el Optional; si no existe, devuelve Optional.empty()
    if (productOptional.isPresent()) {
        return ResponseEntity.ok(productOptional.get());//Este get puede sustituirse por un orElseThrow ya que nos permitiría obtener el valor presente en un Optional, o lanzar una excepción si el Optional está vacío
    }
    return ResponseEntity.notFound().build(); //Tengo que usar el build para construir una respuesta sin cuerpo, que es lo esperado cuando no encuentra el objeto, ya que en la respuesta http donde se almacena el objeto es en el cuerpo
}



//-------MÉTODO PARA DEVOLVER BINDINGRESULT EN JSON-------
//1.Método que devuelve un responseentity y recibe un BindingResult
//2. Un map llamado errors que almacena par clave valor de tipo string, ambos.
//3.Recorrer el BindingResult y por cada uno hacemos lo siguiente
//4.Añadir al hashmap como key el campo y como segundo valor el mensaje el cual construimos con el campo de nuevo, y con el deffault message
//5.Devolver un responseentity con encadenamiento de métodos (ambos devuleven una instancia de responseentity)
    //BodyBuilder: Es una clase interna de ResponseEntity que facilita la construcción de respuestas HTTP.
    //Respuestas Sin Cuerpo: Usa el método build().
    //Respuestas Con Cuerpo: Usa el método body(T body)

private ResponseEntity<?> validation (BindingResult result){
Map<String,String> errors = new HashMap<>();
result.getFieldErrors().forEach(err->{
    errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
});
return ResponseEntity.badRequest().body(errors);
}


}






package com.andres.curso.springboot.app.springbootcrud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// @Entity
// @Table(name="products")
// public class Product {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     private String name;
//     private Integer price;
//     private String description;

//     public Long getId() {
//         return id;
//     }
//     public void setId(Long id) {
//         this.id = id;
//     }
//     public String getName() {
//         return name;
//     }
//     public void setName(String name) {
//         this.name = name;
//     }
//     public Integer getPrice() {
//         return price;
//     }
//     public void setPrice(Integer price) {
//         this.price = price;
//     }
//     public String getDescription() {
//         return description;
//     }
//     public void setDescription(String description) {
//         this.description = description;
//     }

    
// }

@Entity
@Table(name="products")
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="{NotEmpty.product.name}") //Igualmente 3 espacios en blanco los aceptaría, por eso quizás es mejor usar NotBlank
    @Size(min=3,max=20)
    private String name;

    @Min(value=500, message="{Min.product.price}")
    @NotNull(message = "{NotNull.product.price}")
    private Integer price;

    @NotBlank
    private String description;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    

}

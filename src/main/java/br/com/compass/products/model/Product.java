package br.com.compass.products.model;

import br.com.compass.products.respository.ProductRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}

package br.com.compass.products.controller;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<ProductDTO> products() {
        List<Product> products = repository.findAll();
        return ProductDTO.changeToProductDTO(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> storeProduct(@RequestBody ProductForm form, UriComponentsBuilder uriBuilder) {
        Product product = form.changeToProduct();
        repository.save(product);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDTO(product));
    }

}
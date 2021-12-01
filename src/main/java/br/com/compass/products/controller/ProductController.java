package br.com.compass.products.controller;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ProductDTO showProduct(@PathVariable Long id) {
        Product product = repository.getById(id);
        return new ProductDTO(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> storeProduct(@RequestBody ProductForm form, UriComponentsBuilder uriBuilder) {
        Product product = form.changeToProduct();
        repository.save(product);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDTO(product));
    }

    @PutMapping("/{id}") @Transactional
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product form) {
        Product product = form.updateProduct(id, repository);
        return ResponseEntity.ok(new ProductDTO(product));
    }

}
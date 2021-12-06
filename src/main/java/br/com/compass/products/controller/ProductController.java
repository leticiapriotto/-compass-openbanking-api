package br.com.compass.products.controller;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public Page<ProductDTO> products(@PageableDefault(sort = "name", page = 0, size = 5) Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return ProductDTO.changeToProductDTO(products);
    }

    @GetMapping("/search")
    public Page<ProductDTO> productsSearch(@PageableDefault(sort = "price", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
                                           @RequestParam(required = false) Double maxPrice,
                                           @RequestParam(required = false) Double minPrice,
                                           @RequestParam(required = false) String q) {

        Page<Product> products = repository.findBySearch(pageable, minPrice, maxPrice, q);
        return ProductDTO.changeToProductDTO(products);
    }

    @GetMapping("/{id}")
    public ProductDTO showProduct(@PathVariable Long id) {
        Product product = repository.getById(id);
        return new ProductDTO(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> storeProduct(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
        Product product = form.changeToProduct();
        repository.save(product);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDTO(product));
    }

    @PutMapping("/{id}") @Transactional
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductForm form) {
        Product product = form.updateProduct(id, repository);
        return ResponseEntity.ok(new ProductDTO(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
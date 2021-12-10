package br.com.compass.products.controller;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.service.ProductServiceImp;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImp service;

    @GetMapping
    public Page<ProductDTO> getAllProducts(@PageableDefault(sort = "name", page = 0, size = 5) Pageable pageable) {
        return service.getAllProducts(pageable);
    }

    @GetMapping("/search")
    public Page<ProductDTO> productsSearch(@PageableDefault(sort = "price", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
                                           @RequestParam(required = false) Double maxPrice,
                                           @RequestParam(required = false) Double minPrice,
                                           @RequestParam(required = false) String q) {

        return service.productsSearch(pageable, maxPrice, minPrice, q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> storeProduct(@RequestBody @Valid ProductForm form, UriComponentsBuilder uriBuilder) {
        return service.storeProduct(form, uriBuilder);
    }

    @PutMapping("/{id}") @Transactional
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductForm form) {
       return service.updateProduct(id, form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }

}
package br.com.compass.products.service;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository repository;

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return ProductDTO.changeToProductDTO(products);
    }

    public Page<ProductDTO> productsSearch(Pageable pageable, Double maxPrice, Double minPrice, String q) {

        Page<Product> products = repository.findBySearch(pageable, minPrice, maxPrice, q);
        return ProductDTO.changeToProductDTO(products);
    }

    public ProductDTO showProduct(Long id) {
        Product product = repository.getById(id);
        return new ProductDTO(product);
    }

    public ResponseEntity<ProductDTO> storeProduct(ProductForm form, UriComponentsBuilder uriBuilder) {
        Product product = form.changeToProduct();
        repository.save(product);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDTO(product));
    }

    public ResponseEntity<ProductDTO> updateProduct(Long id, ProductForm form) {
        Product product = form.updateProduct(id, repository);
        return ResponseEntity.ok(new ProductDTO(product));
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

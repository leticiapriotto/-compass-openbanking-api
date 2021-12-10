package br.com.compass.products.service;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return new PageImpl<>(products.stream().map(product -> mapper.map(product, ProductDTO.class)).collect(Collectors.toList()));
    }

    public Page<ProductDTO> productsSearch(Pageable pageable, Double maxPrice, Double minPrice, String q) {
        Page<Product> products = repository.findBySearch(pageable, minPrice, maxPrice, q);
        return new PageImpl<>(products.stream().map(product -> mapper.map(product, ProductDTO.class)).collect(Collectors.toList()));
    }

    public ResponseEntity<ProductDTO> getProductById(Long id) {
        Product product = repository.getById(id);
        return ResponseEntity.ok().body(mapper.map(product, ProductDTO.class));
    }

    public ResponseEntity<ProductDTO> storeProduct(ProductForm form, UriComponentsBuilder uriBuilder) {
        Product product = mapper.map(form, Product.class);
        repository.save(product);

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.map(product, ProductDTO.class));
    }

    public ResponseEntity<ProductDTO> updateProduct(Long id, ProductForm form) {
        Product product = form.updateProduct(id, repository);
        return ResponseEntity.ok().body(mapper.map(product, ProductDTO.class));
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

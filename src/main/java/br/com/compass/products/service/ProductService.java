package br.com.compass.products.service;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.controller.form.ProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ProductService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Page<ProductDTO> productsSearch(Pageable pageable, Double maxPrice, Double minPrice, String q);

    ProductDTO showProduct(Long id);

    ResponseEntity<ProductDTO> storeProduct(ProductForm form, UriComponentsBuilder uriBuilder);

    ResponseEntity<ProductDTO> updateProduct(Long id, ProductForm form);

    ResponseEntity<?> deleteProduct(Long id);
}

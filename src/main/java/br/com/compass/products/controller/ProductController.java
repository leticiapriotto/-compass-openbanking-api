package br.com.compass.products.controller;

import br.com.compass.products.controller.dto.ProductDTO;
import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
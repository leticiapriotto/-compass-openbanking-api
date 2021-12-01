package br.com.compass.products.controller.dto;

import br.com.compass.products.model.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public static List<ProductDTO> changeToProductDTO(List<Product> products) {
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }
}

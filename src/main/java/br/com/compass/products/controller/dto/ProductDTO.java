package br.com.compass.products.controller.dto;

import br.com.compass.products.model.Product;
import lombok.Data;
import org.springframework.data.domain.Page;

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

    public static Page<ProductDTO> changeToProductDTO(Page<Product> products) {
        return products.map(ProductDTO::new);
    }
}

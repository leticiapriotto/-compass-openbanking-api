package br.com.compass.products.controller.form;

import br.com.compass.products.model.Product;
import lombok.Data;

@Data
public class ProductForm {

    private String name;
    private String description;
    private Double price;

    public Product changeToProduct() {
        return new Product(name, description, price);
    }
}

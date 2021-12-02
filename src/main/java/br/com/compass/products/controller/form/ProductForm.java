package br.com.compass.products.controller.form;

import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import lombok.Data;

@Data
public class ProductForm {

    private String name;
    private String description;
    private Double price;

    public Product changeToProduct() {
        return new Product(name, description, price);
    }

    public Product updateProduct(Long id, ProductRepository repository) {
        Product product = repository.getById(id);

        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);

        return product;
    }
}

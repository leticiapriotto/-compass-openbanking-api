package br.com.compass.products.controller.form;

import br.com.compass.products.model.Product;
import br.com.compass.products.respository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

    @NotNull @NotEmpty @Length(min = 5, max = 50)
    private String name;

    @NotNull @NotEmpty @Length(min = 10)
    private String description;

    @NotNull @DecimalMin("0.0")
    private Double price;

    public Product updateProduct(Long id, ProductRepository repository) {
        Product product = repository.getById(id);

        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);

        return product;
    }
}

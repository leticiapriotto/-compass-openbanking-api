package br.com.compass.products.controller.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;

}

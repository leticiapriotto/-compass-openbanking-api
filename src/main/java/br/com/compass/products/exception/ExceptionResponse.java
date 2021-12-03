package br.com.compass.products.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private Integer statusCode;
    private String message;

}

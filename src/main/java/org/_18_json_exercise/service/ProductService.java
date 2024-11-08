package org._18_json_exercise.service;

import org._18_json_exercise.data.entities.Product;
import org._18_json_exercise.service.dtos.ProductInRangeDto;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProduct() throws FileNotFoundException;


    List<ProductInRangeDto> getAllProductsInRange(BigDecimal from, BigDecimal to);


    void printAllProductsInRange(BigDecimal from, BigDecimal to);
}

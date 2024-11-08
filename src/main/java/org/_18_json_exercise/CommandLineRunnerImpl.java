package org._18_json_exercise;

import org._18_json_exercise.service.CategoryService;
import org._18_json_exercise.service.ProductService;
import org._18_json_exercise.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {


    private final CategoryService categoryService;

    private final UserService userService;

    private final ProductService productService;

    public CommandLineRunnerImpl(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.userService.seedUsers();
        this.productService.seedProduct();
//        this.productService.printAllProductsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        this.userService.getAllUserAndSoldItems();
    }
}

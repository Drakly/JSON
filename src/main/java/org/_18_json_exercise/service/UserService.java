package org._18_json_exercise.service;

import org._18_json_exercise.data.entities.Product;
import org._18_json_exercise.service.dtos.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDto> getAllUserAndSoldItems();
}

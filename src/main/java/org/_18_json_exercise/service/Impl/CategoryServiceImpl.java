package org._18_json_exercise.service.Impl;

import com.google.gson.Gson;
import org._18_json_exercise.data.entities.Category;
import org._18_json_exercise.data.repository.CategoryRepository;
import org._18_json_exercise.service.CategoryService;
import org._18_json_exercise.service.dtos.CategorySeedDto;
import org._18_json_exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String FILE_PATH = "src/main/resources/json/categories.json";



    private final CategoryRepository categoryRepository;
    private final Gson gson;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));

            CategorySeedDto[] categorySeedDtos = this.gson.fromJson(jsonContent, CategorySeedDto[].class);
            for (CategorySeedDto categorySeedDto : categorySeedDtos) {
                if (!this.validationUtil.isValid(categorySeedDto)) {
                    this.validationUtil.getViolations(categorySeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }
}

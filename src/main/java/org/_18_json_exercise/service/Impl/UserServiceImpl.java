package org._18_json_exercise.service.Impl;

import com.google.gson.Gson;
import org._18_json_exercise.data.entities.User;
import org._18_json_exercise.data.repository.UserRepository;
import org._18_json_exercise.service.UserService;
import org._18_json_exercise.service.dtos.ProductSoldDto;
import org._18_json_exercise.service.dtos.UserSeedDto;
import org._18_json_exercise.service.dtos.UserSoldProductsDto;
import org._18_json_exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH = "src/main/resources/json/users.json";


    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            UserSeedDto[] userSeedDtos = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);
            for (UserSeedDto userSeedDto : userSeedDtos) {
                if (!this.validationUtil.isValid(userSeedDto)){
                    this.validationUtil.getViolations(userSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                this.userRepository.saveAndFlush(this.modelMapper.map(userSeedDto, User.class));

            }
        }
    }

    @Override
    public List<UserSoldProductsDto> getAllUserAndSoldItems() {

        return this.userRepository.findAll()
                .stream()
                .filter(u ->
                        u.getSold().stream().anyMatch(p -> p.getBuyer() == null))
                .map(u -> {
                    UserSoldProductsDto userDto = this.modelMapper.map(u, UserSoldProductsDto.class);
                    List<ProductSoldDto> soldProductsDto = u.getSold()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductSoldDto.class))
                            .collect(Collectors.toList());
                    userDto.setSoldProducts(soldProductsDto);
                    return userDto;
                })
                .sorted(Comparator.comparing(UserSoldProductsDto::getLastName).thenComparing(UserSoldProductsDto::getFirstName))
                .toList();
    }
}

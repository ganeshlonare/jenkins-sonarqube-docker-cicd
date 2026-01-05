package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final String CACHE_NAME= "userNames";

    public List<UserDto> getAllUser() {
        List<UserEntity> userEntities= userRepository.findAll();
        return userEntities
                .stream()
                .map(userEntity -> modelMapper.map(userEntity,UserDto.class))
                .collect(Collectors.toList());
    }

    @CachePut(cacheNames = CACHE_NAME , key = "#result.id")
    public UserDto saveUser(UserDto userDto) {
        UserEntity userEntity= userRepository.save(modelMapper.map(userDto, UserEntity.class));
        return modelMapper.map(userEntity,UserDto.class);
    }

    @Cacheable(cacheNames = CACHE_NAME , key = "#id")
    public UserDto getUserById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElse(null), UserDto.class);
    }
}

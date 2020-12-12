package com.pinartekes.api.service;

import com.pinartekes.api.dto.UserDto;
import com.pinartekes.api.dto.UserRegisterDto;
import com.pinartekes.api.util.TPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);
    UserDto getById(Long id);
    List<UserDto> getUsers();
    Long deleteByUsername(String username);
    Boolean delete(Long id);
    UserDto update(Long id, UserDto userDto);
    TPage<UserDto> getAllPageable(Pageable pageable);

    UserDto findOne(String username);
    Boolean register(UserRegisterDto userRegisterDto);

    List<UserDto> getTopTenUsers();
}

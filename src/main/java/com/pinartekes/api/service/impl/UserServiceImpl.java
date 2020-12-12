package com.pinartekes.api.service.impl;

import com.pinartekes.api.dao.UserRepository;
import com.pinartekes.api.dao.entity.User;
import com.pinartekes.api.dto.UserDto;
import com.pinartekes.api.dto.UserRegisterDto;
import com.pinartekes.api.service.UserService;
import com.pinartekes.api.util.TPage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDto save(UserDto projectDto) {
        User u = modelMapper.map(projectDto, User.class);
        u = userRepository.save(u);
        projectDto.setId(u.getId());
        return projectDto;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.getOne(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> data = userRepository.findAll();
        return Arrays.asList(modelMapper.map(data, UserDto[].class));
    }

    @Override
    public TPage<UserDto> getAllPageable(Pageable pageable) {
        Page<User> data = userRepository.findAll(pageable);
        TPage<UserDto> response = new TPage<UserDto>();
        response.setStat(data, Arrays.asList(modelMapper.map(data.getContent(), UserDto[].class)));
        return response;
    }

    @Override
    public UserDto findOne(String username) {
        User u = userRepository.findByUsername(username);
        return modelMapper.map(u, UserDto.class);
    }

    @Override
    public Boolean register(UserRegisterDto userRegisterDto) {
        try {
            User user = new User();
            user.setPassword(bcryptEncoder.encode(userRegisterDto.getPassword()));
            user.setUsername(userRegisterDto.getUsername());
            user.setName(userRegisterDto.getName());
            user.setSurname(userRegisterDto.getSurname());
            userRepository.save(user);
            return Boolean.TRUE;
        } catch (Exception e) {
            //log.error("REGISTRATION=>", e);
            return Boolean.FALSE;
        }
    }

    @Override
    public List<UserDto> getTopTenUsers() {
        List<User> data = userRepository.findTop10ByOrderByNameAsc();
        return Arrays.asList(modelMapper.map(data, UserDto[].class));
    }

    @Override
    public Long deleteByUsername(String username) {
        return userRepository.deleteUserByUsername(username);
    }

    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User userDb  =userRepository.getOne(id);
        if (userDb == null) {
            throw new IllegalArgumentException("User does not exist ID: " + id);
        }
        userDb.setUsername(userDto.getUsername());
        userDb.setName(userDto.getName());
        userDb.setSurname(userDto.getSurname());
        userDb.setPassword(userDto.getPassword());
        userRepository.save(userDb);
        return modelMapper.map(userDb, UserDto.class);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toString());
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }
}

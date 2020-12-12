package com.pinartekes.api.controller;

import com.pinartekes.api.dto.*;
import com.pinartekes.api.security.TokenProvider;
import com.pinartekes.api.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        AuthToken authToken = new AuthToken();

        UserDto userDetail= userService.findOne(loginUser.getUsername());

        AuthUserDto authUser= new AuthUserDto();

        authUser.setId(userDetail.getId());
        authUser.setUsername(userDetail.getUsername());
        authUser.setName(userDetail.getName());
        authUser.setUsername(userDetail.getUsername());
        authToken.setToken(token);
        authToken.setUser(authUser);

        return ResponseEntity.ok(authToken);
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@RequestBody UserRegisterDto userRegisterDto) throws AuthenticationException {

        try {
            Boolean response = userService.register(userRegisterDto);
            if(true)
            {

                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



}

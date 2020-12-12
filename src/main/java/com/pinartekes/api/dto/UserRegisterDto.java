package com.pinartekes.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterDto {


    private Long id;
    private String username;
    private String name;
    private String surname;
    private String password;



}

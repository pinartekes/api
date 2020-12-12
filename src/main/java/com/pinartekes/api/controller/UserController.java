package com.pinartekes.api.controller;

import com.pinartekes.api.dto.UserDto;
import com.pinartekes.api.service.impl.UserServiceImpl;
import com.pinartekes.api.util.TPage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> users = userServiceImpl.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/topten")
    public ResponseEntity<List<UserDto>> getTopTen(){
        List<UserDto> users = userServiceImpl.getTopTenUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/pagination")
    public ResponseEntity<TPage<UserDto>> getAllByPagination(Pageable pageable) {
        TPage<UserDto> data = userServiceImpl.getAllPageable(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable(value = "id", required = true) Long id){
        try{
            UserDto userDto = userServiceImpl.getById(id);
            return ResponseEntity.ok(userDto);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404 not found.
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createProject(@Valid @RequestBody UserDto projectDto) {
        try {
            UserDto newProject= userServiceImpl.save(projectDto);
            return ResponseEntity.created(new URI("/"+newProject.getId())).body(newProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(value = "id", required = true) Long id,
                                              @Valid @RequestBody UserDto user){
        try{
            userServiceImpl.update(id,user);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/byname/{username}")
    public Long delete(@PathVariable(value = "username", required = true) String username) {
        try {
            return  userServiceImpl.deleteByUsername(username);
        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id", required = true) Long id) {
        try {
            if (id != null) {
                userServiceImpl.delete(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}



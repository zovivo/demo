package com.example.demo.controller;

import com.example.demo.exception.CustomException;
import com.example.demo.model.UserModel;
import com.example.demo.model.enu.Code;
import com.example.demo.model.response.ResponseData;
import com.example.demo.model.search.UserSearch;
import com.example.demo.service.UserService;
import com.example.demo.util.ResponsePreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResponsePreProcessor responsePreProcessor;

    @PostMapping(value = {"/search"})
    public ResponseEntity<ResponseData> search(@RequestBody UserSearch search, HttpServletRequest request) {
        if (!search.isPaged()) {
            List<UserModel> userModels = userService.find(search);
            return responsePreProcessor.buildResponseEntity(HttpStatus.OK, Code.SUCCESS, userModels);
        }
        Page<UserModel> userModels = userService.search(search);
        return responsePreProcessor.buildResponseEntity(HttpStatus.OK, Code.SUCCESS, userModels);
    }

    @PostMapping(value = {"", "/", "/sign-up","/signup"})
    public ResponseEntity<ResponseData> signup(@RequestBody @Validated UserModel userModel) throws CustomException {
        userModel = userService.signup(userModel);
        return responsePreProcessor.buildResponseEntity(HttpStatus.OK, Code.SUCCESS, userModel);
    }

    @PutMapping(value = { "/{id}/update"})
    public ResponseEntity<ResponseData> update(@RequestBody UserModel userModel) throws CustomException {
//        userModel = userService.edit(userModel);
        userModel = userService.update(userModel);
        return responsePreProcessor.buildResponseEntity(HttpStatus.OK, Code.SUCCESS, userModel);
    }
}

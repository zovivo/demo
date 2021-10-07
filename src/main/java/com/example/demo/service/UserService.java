package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.model.UserModel;
import com.example.demo.model.search.UserSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService extends BaseService<User, Long> {

    public List<UserModel> find(UserSearch search);

    public Page<UserModel> search(UserSearch search);

    public UserModel create(UserModel userModel);

    public UserModel findOne(Long id);

    public UserModel signup(UserModel userModel) throws CustomException;

    public UserModel update(UserModel userModel) throws CustomException;

    public UserModel edit(UserModel userModel) throws CustomException;
}

package com.example.demo.repository.imp;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.QueryTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository(value = "userRepository")
public class UserRepositoryImp extends BaseRepositoryImp<User, Long> implements UserRepository {

    public UserRepositoryImp() {
        super(User.class);
    }

    public List<User> findAll(QueryTemplate queryTemplate) {
        return find(queryTemplate);
    }
}

package com.example.demo.service.imp;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.model.UserModel;
import com.example.demo.model.enu.Code;
import com.example.demo.model.search.UserSearch;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.imp.UserRepositoryImp;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.util.CommonUtils;
import com.example.demo.util.QueryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(value = "userService")
public class UserServiceImp extends BaseServiceImp<UserRepository, User, Long> implements UserService {

    @Autowired
    private UserRepositoryImp userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private QueryTemplate buildQuery(UserSearch search) {
        QueryTemplate queryTemplate = getBaseQuery(search);
        String query = queryTemplate.getQuery();
        HashMap<String, Object> params = queryTemplate.getParameterMap();
        if (search.getEmail() != null && !search.getEmail().isEmpty()) {
            query += " and e.email like :email ";
            params.put("email", "%" + search.getId() + "%");
        }
        if (search.getFullName() != null && !search.getFullName().isEmpty()) {
            query += " and e.fullName like :fullName ";
            params.put("fullName", "%" + search.getFullName() + "%");
        }
        if (search.getUserName() != null && !search.getUserName().isEmpty()) {
            query += " and e.userName like :userName ";
            params.put("userName", "%" + search.getUserName() + "%");
        }
        if (search.getRoleId() != null && search.getRoleId() > 0) {
            query += " and e.role.id = :roleId ";
            params.put("roleId", search.getRoleId());
        }
        queryTemplate.setQuery(query);
        queryTemplate.setParameterMap(params);
        return queryTemplate;
    }

    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public List<UserModel> find(UserSearch search) {
        List<UserModel> userModels = new ArrayList<>();
        QueryTemplate queryTemplate = buildQuery(search);
        List<User> users = find(queryTemplate);
        for (User user : users) {
            userModels.add(User.convertToModel(user, !search.isAdmin()));
        }
        return userModels;
    }

    @Override
    public Page<UserModel> search(UserSearch search) {
        CommonUtils.setDefaultPageIfNull(search);
        QueryTemplate queryTemplate = buildQuery(search);
        Page<User> users = search(queryTemplate);
        return users.map(user -> {
            UserModel model = User.convertToModel(user, !search.isAdmin());
            return model;
        });
    }

    @Override
    @Transactional(readOnly = false)
    public UserModel create(UserModel userModel) {
        Role role = roleService.find(userModel.getRole().getId());
        User user = UserModel.convertToEntity(userModel);
        user.setRole(role);
        userModel = User.convertToModel(user, true);
        return userModel;
    }

    @Override
    public UserModel findOne(Long id) {
        User user = find(id);
        UserModel userModel = User.convertToModel(user, true);
        return userModel;
    }

    @Override
    @Transactional(rollbackFor = {CustomException.class})
    public UserModel signup(UserModel userModel) throws CustomException {
        checkExistedId(userModel, Code.USER_ID_EXISTED);
//        Role role = roleService.findUserRole();
        User user = UserModel.convertToEntity(userModel);
//        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user = create(user);
        userModel = User.convertToModel(user, true);
//        roleService.create(userModel.getRole());
//        if (true)
//            throw new NullPointerException();
        return userModel;
    }

    @Override
    @Transactional(rollbackFor = NullPointerException.class)
    public UserModel update(UserModel userModel) throws CustomException {
        checkNotExistedId(userModel, Code.USER_ID_NOT_EXISTED);
        User user = userRepository.find(userModel.getId());
        user.update(userModel);
        user = update(user);
        userModel = User.convertToModel(user, true);
//        roleService.create(userModel.getRole());
//        if (true)
//            throw new NullPointerException();
        return userModel;
    }

    @Override
//    @Transactional()
    public UserModel edit(UserModel userModel) throws CustomException {
        userModel = create(userModel);
        return userModel;
    }

}

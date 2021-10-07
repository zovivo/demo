package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.exception.CustomException;
import com.example.demo.model.RoleModel;

public interface RoleService extends BaseService<Role, Long> {

    public Role findUserRole();

    public RoleModel create(RoleModel roleModel) throws CustomException;

}

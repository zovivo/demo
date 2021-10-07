package com.example.demo.service.imp;

import com.example.demo.entity.Role;
import com.example.demo.exception.CustomException;
import com.example.demo.model.RoleModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "roleService")
public class RoleServiceImp extends BaseServiceImp<RoleRepository, Role, Long> implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return this.roleRepository;
    }

    @Override
    public Role findUserRole() {
        return getRepository().findUserRole();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = NullPointerException.class)
    public RoleModel create(RoleModel roleModel) throws CustomException {
        Role role = new Role(roleModel.getName());
        create(role);
        roleModel.setId(role.getId());
        if (true)
            throw new NullPointerException();
        return roleModel;
    }
}

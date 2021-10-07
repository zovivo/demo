package com.example.demo.repository.imp;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.util.QueryTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "roleRepository")
public class RoleRepositoryImp extends BaseRepositoryImp<Role,Long> implements RoleRepository {

    public RoleRepositoryImp() {
        super(Role.class);
    }

    @Override
    public Role findUserRole() {
        String query = "From Role e where e.name = 'user'";
        QueryTemplate queryTemplate = new QueryTemplate();
        queryTemplate.setQuery(query);
        Role role = findOne(queryTemplate);
        return role;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createRole(Role role){
        getEntityManager().persist(role);
        if (true)
            throw new RuntimeException();
        return;
    }
}

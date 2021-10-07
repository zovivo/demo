package com.example.demo.repository;

import com.example.demo.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

    public Role findUserRole();

    public void createRole(Role role);

}

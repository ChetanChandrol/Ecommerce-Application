package com.example.UserService.Service;

import com.example.UserService.Repository.RoleRepository;
import com.example.UserService.models.Role;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class RoleService {
    public RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name)
    {
        Role role = new Role();
        role.setRoleName(name);
       return roleRepository.save(role);
    }


    public List<Role> getAllRoles()
    {
        return roleRepository.findAll();
    }


}

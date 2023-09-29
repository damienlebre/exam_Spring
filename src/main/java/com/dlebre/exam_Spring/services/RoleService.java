package com.dlebre.exam_Spring.services;

import com.dlebre.exam_Spring.models.Role;
import com.dlebre.exam_Spring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String roleName){

        return this.roleRepository.findByRoleName(roleName);
    }

    public void saveRole(Role roleName){

        this.roleRepository.save(roleName);
    }


}

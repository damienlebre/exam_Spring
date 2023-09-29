package com.dlebre.exam_Spring.repository;

import com.dlebre.exam_Spring.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);


}

package com.dlebre.exam_Spring.repository;

import com.dlebre.exam_Spring.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}

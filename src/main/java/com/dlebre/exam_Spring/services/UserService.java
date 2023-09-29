package com.dlebre.exam_Spring.services;

import com.dlebre.exam_Spring.models.User;
import com.dlebre.exam_Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username){

        return this.userRepository.findByUsername(username);
    }

    public void registerUser (User user){

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }
}

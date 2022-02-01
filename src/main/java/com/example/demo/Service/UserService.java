package com.example.demo.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User loadUserByUsername (String email) throws ChangeSetPersister.NotFoundException {
        return userRepository.findByEmail(email).orElse(null);
    }


}

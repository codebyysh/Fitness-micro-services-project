package com.fitness.userservice.services;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository ;


    public UserResponse register(RegisterRequest request) {

        if ( userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User Already Exists") ;
        }

        // User naam ki table mtlb ki database me jaane ke liye data ready kiya
        // cooked for the database saving process 🙂😂
        User user = new User() ;
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());

        User savedUser = userRepository.save(user) ;
        UserResponse userResponse = new UserResponse() ;
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setId(savedUser.getId());
        userResponse.setFirst_name(savedUser.getFirst_name());
        userResponse.setLast_name(savedUser.getLast_name());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse ;
    }
}

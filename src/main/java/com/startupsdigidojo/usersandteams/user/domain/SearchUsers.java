package com.startupsdigidojo.usersandteams.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchUsers {
    private UserRepository userRepository;

    @Autowired
    public SearchUsers(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByMailAddress(String mailAddress){
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("No user found with the mail address: "+mailAddress);
        }
        return maybeUser.get();
    }

    public User findById(long id){
        Optional<User> maybeUser = userRepository.findById(id);

        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("No user found with id: "+id);
        }
        return maybeUser.get();
    }

}

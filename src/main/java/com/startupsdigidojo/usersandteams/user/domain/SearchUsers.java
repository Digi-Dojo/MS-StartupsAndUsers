package com.startupsdigidojo.usersandteams.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchUsers {
    private final UserRepository userRepository;

    @Autowired
    public SearchUsers(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * @param mailAddress the mail address of the user we want to find
     * @return the user with the provided mail address
     * @throws IllegalArgumentException if no user with the provided mail address is found
     */
    public User findByMailAddress(String mailAddress){
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("No user found with the mail address: "+mailAddress);
        }
        return maybeUser.get();
    }

    /**
     * @param id id of the user we want to find
     * @return the user with the provided id
     * @throws IllegalArgumentException if no user with the provided id is found
     */
    public User findById(long id){
        Optional<User> maybeUser = userRepository.findById(id);

        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("No user found with id: "+id);
        }
        return maybeUser.get();
    }

}

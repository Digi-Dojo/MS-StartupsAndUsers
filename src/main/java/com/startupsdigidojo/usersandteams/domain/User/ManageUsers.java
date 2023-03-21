package com.startupsdigidojo.usersandteams.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageUsers {

    private UserRepository userRepository;

    @Autowired
    public ManageUsers(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String name, String mailAddress, String password){
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if(maybeUser.isPresent()){
            throw new IllegalArgumentException("A user already exists with this mail address");
        }

        return userRepository.save(new User(name, mailAddress, password));
    }

    public boolean deleteUser(String name, String mailAddress, String password){
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if(!maybeUser.isPresent()){
            throw new IllegalArgumentException("User doesn't exist");
        }

        userRepository.delete(new User(name,mailAddress,password));

        return true;
    }

    public User update(String name, String mailAddress, String password){
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if(!maybeUser.isPresent()){
            throw new IllegalArgumentException("The user doesn't exist");
        }

        return userRepository.save(new User(name, mailAddress, password));
    }

    public User updateMailAddress(String oldMailAddress, String newMailAddress) {
        Optional<User> maybeUser = userRepository.findByMailAddress(newMailAddress);

        if (maybeUser.isPresent()) {
            throw new IllegalArgumentException("User with mail address " + newMailAddress + "already exists");
        }

        maybeUser = userRepository.findByMailAddress(oldMailAddress);
        if(maybeUser.isEmpty()) {
            throw new IllegalArgumentException("User with mail address " + oldMailAddress + "does not exist");
        }

        User user = maybeUser.get();
        return userRepository.save(new User(user.getName(), newMailAddress, user.getPassword()));
    }
}

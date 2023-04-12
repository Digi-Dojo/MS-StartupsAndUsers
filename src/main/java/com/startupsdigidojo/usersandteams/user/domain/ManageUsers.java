package com.startupsdigidojo.usersandteams.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageUsers {

    private final UserRepository userRepository;

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

        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("User doesn't exist");
        }

        userRepository.delete(new User(name,mailAddress,password));

        return true;
    }

    public User updatePassword(User user, String newPassword){
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User updateUserMail(String oldMail, String newMail){
        Optional<User> maybeUser = userRepository.findByMailAddress(newMail);

        if (maybeUser.isPresent()) {
            throw new IllegalArgumentException("User with mail address " + newMail + " already exists");
        }

        maybeUser = userRepository.findByMailAddress(oldMail);
        if(maybeUser.isEmpty()){
            throw new IllegalArgumentException("User with mail address " + oldMail + " does not exist");
        }
        User user = maybeUser.get();
        user.setMailAddress(newMail);
        return userRepository.save(user);
    }
}

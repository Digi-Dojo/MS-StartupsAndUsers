package com.startupsdigidojo.usersandteams.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageUsers {

    private final UserRepository userRepository;

    private final UserBroadcaster userBroadcaster;

    @Autowired
    public ManageUsers(UserRepository userRepository, UserBroadcaster userBroadcaster) {
        this.userRepository = userRepository;
        this.userBroadcaster = userBroadcaster;
    }

    /**
     * @param name        name of the user
     * @param mailAddress mail address of the user
     * @param password    password of the user
     * @return the newly created user
     * @throws IllegalArgumentException if a user with the provided mail address already exists
     */
    public User createUser(String name, String mailAddress, String password) {
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if (maybeUser.isPresent()) {
            throw new IllegalArgumentException("A user already exists with this mail address");
        }

        User user = userRepository.save(new User(name, mailAddress, password));

        userBroadcaster.emitNewUser(user);

        return user;
    }

    /**
     * @param mailAddress mail address of the user we want to delete
     * @return true if the operation is successful
     * @throws IllegalArgumentException if no user with the provided mail address is found
     */
    public boolean deleteUser(String mailAddress) {
        Optional<User> maybeUser = userRepository.findByMailAddress(mailAddress);

        if (maybeUser.isEmpty()) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        userRepository.delete(maybeUser.get());

        return true;
    }

    /**
     * @param user        the user, whose password we want to change
     * @param newPassword the new password that will replace the current one
     * @return the newly updated user
     */
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    /**
     * @param oldMail the current mail of the user
     * @param newMail the new mail that will replace the current one
     * @return the newly updated user
     * @throws IllegalArgumentException if a user with the new mail already exists, or if no user with the old email is found
     */
    public User updateUserMail(String oldMail, String newMail) {
        Optional<User> maybeUser = userRepository.findByMailAddress(newMail);

        if (maybeUser.isPresent()) {
            throw new IllegalArgumentException("User with mail address " + newMail + " already exists");
        }

        maybeUser = userRepository.findByMailAddress(oldMail);
        if (maybeUser.isEmpty()) {
            throw new IllegalArgumentException("User with mail address " + oldMail + " does not exist");
        }
        User user = maybeUser.get();
        user.setMailAddress(newMail);
        return userRepository.save(user);
    }
}

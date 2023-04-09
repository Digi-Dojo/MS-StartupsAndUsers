package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.ManageUsers;
import com.startupsdigidojo.usersandteams.user.domain.SearchUsers;
import com.startupsdigidojo.usersandteams.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {
    private ManageUsers manageUsers;
    private SearchUsers searchUsers;

    @Autowired
    public UserController(ManageUsers manageUsers, SearchUsers searchUsers) {
        this.manageUsers = manageUsers;
        this.searchUsers = searchUsers;
    }

    @GetMapping("/{mailAddress}")
    public User findById(@PathVariable("mailAddress") String mailAddresses) {
        return searchUsers.findByMailAddress(mailAddresses);
    }

    @PostMapping( "/create")
    public User createNewUser(@RequestBody UserDTO dto) {
        return manageUsers.createUser(dto.getName(), dto.getMailAddress(), dto.getPassword());
    }

    @PostMapping( "/update")
    public User updateUser(@RequestBody UserDTO dto) {
        return manageUsers.update(dto.getName(), dto.getMailAddress(), dto.getPassword());
    }

    @DeleteMapping( "/delete")
    public boolean deleteUser(@RequestBody UserDTO dto) {
        return manageUsers.deleteUser(dto.getName(), dto.getMailAddress(), dto.getPassword());
    }

}
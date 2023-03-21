package com.startupsdigidojo.usersandteams.application.User;

import com.startupsdigidojo.usersandteams.domain.User.ManageUsers;
import com.startupsdigidojo.usersandteams.domain.User.SearchUsers;
import com.startupsdigidojo.usersandteams.domain.User.User;
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

    @PostMapping(path = "/create")
    public User createNewUser(@RequestBody UserDTO dto) {
        return manageUsers.createUser(dto.getName(), dto.getMailAddres(), dto.getPassword());
    }

    @PostMapping(path = "/update")
    public User updateUser(@RequestBody UserDTO dto) {
        return manageUsers.update(dto.getName(), dto.getMailAddres(), dto.getPassword());
    }

    @DeleteMapping(path = "/delete")
    public boolean deleteUser(@RequestBody UserDTO dto) {
        return manageUsers.deleteUser(dto.getName(), dto.getMailAddres(), dto.getPassword());
    }

}
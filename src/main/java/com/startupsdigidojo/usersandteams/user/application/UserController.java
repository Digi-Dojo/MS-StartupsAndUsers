package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.LoginService;
import com.startupsdigidojo.usersandteams.user.domain.ManageUsers;
import com.startupsdigidojo.usersandteams.user.domain.SearchUsers;
import com.startupsdigidojo.usersandteams.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {
    private final ManageUsers manageUsers;
    private final SearchUsers searchUsers;

    private final LoginService loginService;

    @Autowired
    public UserController(ManageUsers manageUsers, SearchUsers searchUsers, LoginService loginService) {
        this.manageUsers = manageUsers;
        this.searchUsers = searchUsers;
        this.loginService = loginService;
    }

    @GetMapping("/{mailAddress}")
    public User findById(@PathVariable("mailAddress") String mailAddresses) {
        return searchUsers.findByMailAddress(mailAddresses);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserDTO dto) {
        User dbUser = searchUsers.findByMailAddress(dto.getMailAddress());
        if (dbUser != null && loginService.verifyPassword(dto.getPassword(), dbUser.getPassword())) {
            return ResponseEntity.ok(dbUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping( "/create")
    public User createNewUser(@RequestBody UserDTO dto) {
        return manageUsers.createUser(dto.getName(), dto.getMailAddress(), loginService.hashPassword(dto.getPassword()));
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
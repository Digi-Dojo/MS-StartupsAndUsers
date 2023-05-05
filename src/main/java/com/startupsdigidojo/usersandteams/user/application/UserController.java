package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.LoginService;
import com.startupsdigidojo.usersandteams.user.domain.ManageUsers;
import com.startupsdigidojo.usersandteams.user.domain.SearchUsers;
import com.startupsdigidojo.usersandteams.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://mfront-startupsandusers.onrender.com")
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
    public User loginUser(@RequestBody UserDTO dto) {
        User dbUser = searchUsers.findByMailAddress(dto.getMailAddress());
        return loginService.verifyPassword(dbUser, dto.getPassword());
    }

    @PostMapping("/updatePassword")
    public User updatePassword(@RequestBody UpdatePasswordDTO dto) {
        User dbUser = searchUsers.findByMailAddress(dto.getMailAddress());
        User verifiedUser = loginService.verifyPassword(dbUser, dto.getOldPassword());
        return manageUsers.updatePassword(verifiedUser, loginService.hashPassword(dto.newPassword, dto.mailAddress));
    }

    @PostMapping("/create")
    public User createNewUser(@RequestBody UserDTO dto) {
        return manageUsers.createUser(dto.getName(), dto.getMailAddress(), loginService.hashPassword(dto.getPassword(), dto.getMailAddress()));
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestBody DeleteUserDTO dto) {
        return manageUsers.deleteUser(dto.getMailAddress());
    }

    @PostMapping("/updateMail")
    public User updateUserMail(@RequestBody UpdateUserMailDTO dto) {
        return manageUsers.updateUserMail(dto.getOldMail(), dto.getNewMail());
    }

}
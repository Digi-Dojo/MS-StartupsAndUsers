package com.startupsdigidojo.usersandteams.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ManageUsersTest {

    private ManageUsers underTest;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBroadcaster userBroadcaster;

    @BeforeEach
    void setUp() {
        underTest = new ManageUsers(userRepository, userBroadcaster);
    }

    @Test
    public void itCreatesAUser() {

        User user = new User("testUser", "TestUser@testmail.com", "testPassword");
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(user.getName(), user.getMailAddress(), user.getPassword()));

        User effect = underTest.createUser(user.getName(), user.getMailAddress(), user.getPassword());
        effect.setId(randomPositiveLong());

        assertThat(effect).isInstanceOf(User.class);
        assertThat(effect.getName()).isEqualTo(user.getName());
        assertThat(effect.getMailAddress()).isEqualTo(user.getMailAddress());
        assertThat(effect.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void createUserThrowsExceptionForExistingUser() {

        User user = new User("testUser", "testUser@testmail.com", "testPassword");
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.of(new User("testUser", "testUser@testmail.com", "testPassword")));

        assertThatThrownBy(() -> underTest.createUser(user.getName(), user.getMailAddress(), user.getPassword()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A user already exists with this mail address");
        ;
    }

    @Test
    public void UpdatePassword() {
        String userName = "testUser";
        String userMail = "TestUser@testmail.com";
        String userOldPassword = "testPassword";
        String userNewPassword = "NewPassword";
        User u = new User(userName, userMail, userOldPassword);
        u.setPassword(userNewPassword);
        when(userRepository.save(u)).thenReturn(u);

        User effect = underTest.updatePassword(u, userNewPassword);
        assertThat(effect.getPassword()).isEqualTo(userNewPassword);
    }

    @Test
    public void UpdateThrowsExceptionForNonExistingUser() {

        String userName = "testUser";
        String userMail = "TestUser@testmail.com";
        String userOldPassword = "testPassword";
        String userNewPassword = "NewPassword";

        User u = new User(userName, userMail, userOldPassword);
        when(userRepository.save(u)).thenThrow(new IllegalArgumentException());
        assertThatThrownBy(() -> underTest.updatePassword(u, userNewPassword))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteUserThrowsExceptionForNonExistingUser() {
        String userMail = "TestUser@testmail.com";

        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.deleteUser(userMail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User doesn't exist");
    }

    @Test
    public void UpdatesMailAddressThrowsExceptionForAlreadyExistingNewMailAddress() {
        User user = new User("testUser", "testUser@testmail.com", "testPassword");
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.of(new User("testUser", "testUser@testmail.com", "testPassword")));

        assertThatThrownBy(() -> underTest.updateUserMail(user.getMailAddress(), user.getMailAddress()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with mail address testUser@testmail.com already exists");
    }

    @Test
    public void UpdatesMailAddressThrowsExceptionForNonExistingOldMailAddress() {
        User user = new User("testUser", "testUser@testmail.com", "testPassword");
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateUserMail("NonExistingMail", user.getMailAddress()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User with mail address NonExistingMail does not exist");
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

}

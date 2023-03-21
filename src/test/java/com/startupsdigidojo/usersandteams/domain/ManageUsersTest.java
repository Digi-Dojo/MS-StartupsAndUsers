package com.startupsdigidojo.usersandteams.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ManageUsersTest {

    private ManageUsers underTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new ManageUsers(userRepository);
    }

    @Test
    public void itCreatesAUser() {

        User user = new User("testUser", "TestUser@testmail.com", "testPassword");
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(user.getName(),user.getMailAddress(),user.getPassword()));

        User effect = underTest.createUser(user.getName(),user.getMailAddress(),user.getPassword());
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
        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.of(new User("testUser", "testUser@testmail.com", "testPassword" )));

        assertThatThrownBy(() -> underTest.createUser(user.getName(), user.getMailAddress(), user.getPassword())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void UpdateUpdatesTheName() {

        String userOldName = "OldUser";
        String userNewName = "NewUser";
        String userMail = "TestUser@testmail.com";
        String userPassword = "testPassword";
        User user = new User(userOldName, userMail, userPassword);
        when(userRepository.findByMailAddress(userMail)).thenReturn(Optional.of(new User(userOldName, userMail, userPassword)));

        User effect = underTest.update(userNewName, userMail, userPassword);

        assertThat(effect.getName()).isEqualTo(userNewName);
    }

    @Test
    public void UpdateUpdatesTheMailAddress() {

        String userName = "testUser";
        String userOldMail = "OldMail@testmail.com";
        String userNewMail = "NewMail@testmail.com";
        String userPassword = "testPassword";
        User user = new User(userName, userOldMail, userPassword);
        Long Id = user.getId();

        when(userRepository.findById(Id)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(userName, userOldMail, userPassword));

        User effect = underTest.update(userName, userNewMail, userPassword);

        assertThat(effect.getMailAddress()).isEqualTo(userNewMail);
    }

    @Test
    public void UpdateUpdatesThePassword() {

        String userName = "testUser";
        String userMail = "TestUser@testmail.com";
        String userOldPassword = "testPassword";
        String userNewPassword = "NewPassword";
        User user = new User(userName, userMail, userOldPassword);
        Long Id = user.getId();

        when(userRepository.findById(Id)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(userName, userMail, userOldPassword));

        User effect = underTest.update(userName, userMail, userNewPassword);

        assertThat(effect.getPassword()).isEqualTo(userNewPassword);
    }

    @Test
    public void UpdateThrowsExceptionForNonExistingUser() {

        String userName = "testUser";
        String userMail = "TestUser@testmail.com";
        String userPassword = "testPassword";

        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.update(userName, userMail, userPassword)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteUserThrowsExceptionForNonExistingUser() {

        String userName = "testUser";
        String userMail = "TestUser@testmail.com";
        String userPassword = "testPassword";

        when(userRepository.findByMailAddress(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.deleteUser(userName, userMail, userPassword)).isInstanceOf(IllegalArgumentException.class);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

}

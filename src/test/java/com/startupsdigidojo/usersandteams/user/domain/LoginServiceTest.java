package com.startupsdigidojo.usersandteams.user.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private static LoginService loginService;

    @BeforeAll
    public static void setUp() {
        loginService = new LoginService();
    }

    @Test
    public void hashPasswordIsDeterministicTest() {
        String password = "password";
        String mailAddress = "mailAddress";
        String hash1 = loginService.hashPassword(password, mailAddress);
        String hash2 = loginService.hashPassword(password, mailAddress);
        assertEquals(hash1, hash2);
    }

    @ParameterizedTest
    @MethodSource("generateInput")
    public void avoidCollisionsTest(String password) {
        String hash = loginService.hashPassword("a", "@");
        assertNotEquals(hash, loginService.hashPassword(password, "@"));
    }

    @ParameterizedTest
    @MethodSource("generateInput")
    public void avoidCollisionsSamePasswordTest(String mailAddress) {
        String hash = loginService.hashPassword("@", "a");
        assertNotEquals(hash, loginService.hashPassword("@", mailAddress));
    }

    private static Stream<Arguments> generateInput() {
        return Stream.of(
                Arguments.of("b"),
                Arguments.of("c"),
                Arguments.of("d"),
                Arguments.of("e"),
                Arguments.of("f"),
                Arguments.of("g"),
                Arguments.of("h"),
                Arguments.of("i"),
                Arguments.of("j"),
                Arguments.of("k"),
                Arguments.of("l"),
                Arguments.of("m"),
                Arguments.of("n"),
                Arguments.of("o"),
                Arguments.of("p"),
                Arguments.of("q"),
                Arguments.of("r"),
                Arguments.of("s"),
                Arguments.of("t"),
                Arguments.of("u"),
                Arguments.of("v"),
                Arguments.of("w"),
                Arguments.of("x"),
                Arguments.of("y"),
                Arguments.of("z")
        );
    }

    @Test
    public void verifyPasswordTest() {
        User aUser = new User("Matteo", "malarcher@unibz.it", loginService.hashPassword("Matteo Password", "malarcher@unibz.it"));
        User test = loginService.verifyPassword(aUser, "Matteo Password");
        assertThat(test)
                .isInstanceOf(User.class)
                .isNotNull();
        assertThat(test.getMailAddress())
                .isEqualTo("malarcher@unibz.it");
    }

    @Test
    public void verifyPasswordThrowsExceptionTest() {
        User aUser = new User("Matteo", "malarcher@unibz.it", loginService.hashPassword("Matteo Password", "malarcher@unibz.it"));
        assertThatThrownBy(() -> loginService.verifyPassword(aUser, "Mariolino Password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong password for this user");
    }
}

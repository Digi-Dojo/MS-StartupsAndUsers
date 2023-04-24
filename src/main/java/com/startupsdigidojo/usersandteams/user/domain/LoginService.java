package com.startupsdigidojo.usersandteams.user.domain;

import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class LoginService {

    /**
     * @param password the password we want to hash
     * @return the string containing the hashed password
     * @throws NoSuchAlgorithmException if the algorithm for the hashing method isn't found
     */
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    /**
     * @param user the user, for whom we need to check the password
     * @param enteredPassword the password entered by the person trying to log in
     * @return the user
     * @throws IllegalArgumentException if the entered password isn't the one that belongs to the user
     */
    public User verifyPassword(User user, String enteredPassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        if(!hashedEnteredPassword.equals(user.getPassword())){
            throw new IllegalArgumentException("Wrong password for this user");
        }
        return user;
    }
}

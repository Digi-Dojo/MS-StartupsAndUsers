package com.startupsdigidojo.usersandteams.user.domain;

import jakarta.persistence.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Entity
public class User {

    public User() {
    }

    public User(String name, String mailAddress, String password){
        this.name = name;
        this.mailAddress = mailAddress;
        SecureRandom rand = new SecureRandom();
        salt = new byte[16];
        rand.nextBytes(salt);
        this.password = hashPassword(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String mailAddress;

    private byte[] salt;

    //Todo: maybe hash it or something first to improve security

    private String password;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname) {
        this.name = firstname;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String hashPassword(String password){
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 50000, 128);
        SecretKeyFactory factory;
        byte[] hash;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return new String(hash, StandardCharsets.UTF_8);
    }

}

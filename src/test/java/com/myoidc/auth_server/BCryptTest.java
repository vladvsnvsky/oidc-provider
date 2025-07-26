package com.myoidc.auth_server;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        long start = System.currentTimeMillis();
        String hash = encoder.encode("yourPassword");
        long end = System.currentTimeMillis();
        System.out.println("Encoding took: " + (end - start) + "ms");
    }
}

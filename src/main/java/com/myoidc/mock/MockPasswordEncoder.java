package com.myoidc.mock;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MockPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        // Just prefixing to simulate encoding
        return "mock_" + rawPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Matches if raw password prefixed with "mock_" equals the stored one
        return encodedPassword.equals("mock_" + rawPassword);
    }
}

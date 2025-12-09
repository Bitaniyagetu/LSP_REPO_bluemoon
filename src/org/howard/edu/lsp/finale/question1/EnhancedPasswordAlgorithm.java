package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;
/**
 * Password algorithm that generates passwords containing
 * uppercase letters, lowercase letters, and digits using
 * a cryptographically strong SecureRandom generator.
 */


public class EnhancedPasswordAlgorithm implements PasswordAlgorithm{
    public String generate(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(r.nextInt(chars.length())));
        return sb.toString();
    }
}

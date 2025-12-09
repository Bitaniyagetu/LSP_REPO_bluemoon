package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;
/**
 * Password algorithm that generates passwords using letters only
 * (A–Z and a–z), powered by SecureRandom.
 */


public class LettersPasswordAlgorithm implements PasswordAlgorithm{
    public String generate(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(letters.charAt(r.nextInt(letters.length())));
        return sb.toString();
    }
}

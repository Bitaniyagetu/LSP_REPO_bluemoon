package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Password algorithm that generates digit-only passwords (0–9)
 * using java.util.Random.
 */


public class BasicPasswordAlgorithm implements PasswordAlgorithm {
    public String generate(int length) {
        String digits = "0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(digits.charAt(r.nextInt(digits.length())));
        return sb.toString();
    }
}


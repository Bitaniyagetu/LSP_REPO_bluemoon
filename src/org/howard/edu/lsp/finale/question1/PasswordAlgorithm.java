
package org.howard.edu.lsp.finale.question1;
/**
 * Strategy interface for all password-generation algorithms.
 * Each algorithm must generate a password of the requested length.
 */

public interface PasswordAlgorithm {
    /**
     * Generates a password using the algorithm's rules.
     *
     * @param length number of characters in the password
     * @return generated password
     */
    String generate(int length);
}

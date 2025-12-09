package org.howard.edu.lsp.finale.question1;

/*
 * DESIGN PATTERN DOCUMENTATION
 * -----------------------------
 * Pattern(s) Used:
 * 1. Singleton Pattern  
 *    - Ensures only one instance of PasswordGeneratorService exists.
 *    - Provides a global access point via getInstance().
 *
 * 2. Strategy Pattern  
 *    - Each password-generation approach (basic / enhanced / letters)
 *      is implemented as a separate class that implements PasswordAlgorithm.
 *    - The algorithm is swappable at runtime using setAlgorithm().
 *    - Allows new algorithms to be added without modifying client code.
 *
 * Why These Patterns?
 * -------------------
 * - The Singleton pattern satisfies the requirement for a single shared
 *   access point to the service.
 *
 * - The Strategy pattern satisfies the requirement for multiple password
 *   generation behaviors that are:
 *        * interchangeable,
 *        * extendable,
 *        * selected at runtime.
 *
 * Together, these patterns satisfy ALL system architect expectations.
 */

public class PasswordGeneratorService {
    private static PasswordGeneratorService instance;
    private PasswordAlgorithm algorithm;

    private PasswordGeneratorService() {}
    
    /**
     * Returns the single shared instance of this service.
     *
     * @return the PasswordGeneratorService instance
     */


    public static PasswordGeneratorService getInstance() {
        if (instance == null) {
            instance = new PasswordGeneratorService();
        }
        return instance;
    }
    /**
     * Selects the password-generation algorithm to use.
     *
     * @param name algorithm name ("basic", "enhanced", "letters")
     * @throws IllegalArgumentException if the name does not match a supported algorithm
     */


    public void setAlgorithm(String name) {
        switch (name.toLowerCase()) {
            case "basic":
                algorithm = new BasicPasswordAlgorithm();
                break;
            case "enhanced":
                algorithm = new EnhancedPasswordAlgorithm();
                break;
            case "letters":
                algorithm = new LettersPasswordAlgorithm();
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm");
        }
    }
    /**
     * Generates a password using the currently selected algorithm.
     *
     * @param length desired password length
     * @return generated password
     * @throws IllegalStateException if no algorithm has been selected
     */


    public String generatePassword(int length) {
        if (algorithm == null) {
            throw new IllegalStateException("Algorithm not set");
        }
        return algorithm.generate(length);
    }
}
package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for classifying product prices into human-readable ranges.
 * <p>
 * Used in the Transform phase of the ETL pipeline to assign a 
 * {@code PriceRange} label for each product. Ranges are defined as:
 * <ul>
 *   <li>$0.00 – $10.00 → {@code "Low"}</li>
 *   <li>$10.01 – $100.00 → {@code "Medium"}</li>
 *   <li>$100.01 – $500.00 → {@code "High"}</li>
 *   <li>$500.01 and above → {@code "Premium"}</li>
 * </ul>
 * <p>
 * Prices are rounded to two decimal places using 
 * {@link RoundingMode#HALF_UP} before comparison.
 * </p>
 *
 * <p>This class is declared {@code final} and has a private constructor
 * because it is a static utility and should not be instantiated.</p>
 */
public final class PriceRangeUtil {
    /** Threshold constant: $10.00 */
    private static final BigDecimal TEN = new BigDecimal("10.00");
    /** Threshold constant: $100.00 */
    private static final BigDecimal HUNDRED = new BigDecimal("100.00");
    /** Threshold constant: $500.00 */
    private static final BigDecimal FIVE_HUNDRED = new BigDecimal("500.00");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PriceRangeUtil() {}

    /**
     * Computes the price range label from the given final (post-discount) price.
     *
     * @param price the product price after transformations and discounts.
     *              If {@code null}, returns an empty string.
     * @return one of {@code "Low"}, {@code "Medium"}, {@code "High"}, 
     *         {@code "Premium"}, or an empty string if {@code price} is null.
     */
    public static String fromPrice(BigDecimal price) {
        if (price == null) return "";
        BigDecimal p = price.setScale(2, RoundingMode.HALF_UP);

        if (p.compareTo(TEN) <= 0) {
            return "Low";
        } else if (p.compareTo(HUNDRED) <= 0) {
            return "Medium";
        } else if (p.compareTo(FIVE_HUNDRED) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }
}

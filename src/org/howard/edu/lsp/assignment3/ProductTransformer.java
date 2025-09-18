package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@code ProductTransformer} applies all required transformation rules
 * to a {@link Product} in the order specified by the assignment.
 * <p>
 * Transformation steps:
 * <ol>
 *   <li>Convert the product name to uppercase.</li>
 *   <li>If the product’s category is "Electronics", apply a 10% discount.</li>
 *   <li>If the product’s <em>original category</em> was "Electronics" and
 *       the post-discount price exceeds $500.00, recategorize as
 *       {@code "Premium Electronics"}.</li>
 *   <li>Compute and assign the {@code priceRange} label using
 *       {@link PriceRangeUtil#fromPrice(BigDecimal)}.</li>
 * </ol>
 *
 * <p>This class encapsulates the business rules but delegates
 * price-range computation to {@link PriceRangeUtil}.</p>
 */
public class ProductTransformer implements Transformer<Product> {
    private static final BigDecimal DISCOUNT = new BigDecimal("0.10");
    private static final BigDecimal FIVE_HUNDRED = new BigDecimal("500.00");

    /**
     * Transforms a given {@link Product} by applying the ETL transformation rules.
     *
     * @param p the product to transform; if {@code null}, this method does nothing
     */
    @Override
    public void transform(Product p) {
        if (p == null) return;

        // Step 1: Uppercase name
        if (p.getName() != null) {
            p.setName(p.getName().toUpperCase());
        }

        // Step 2: Apply 10% discount if category is Electronics
        BigDecimal price = p.getPrice();
        if (p.getCategory() != null && p.getCategory().equalsIgnoreCase("Electronics")) {
            price = price.multiply(BigDecimal.ONE.subtract(DISCOUNT));
        }
        price = price.setScale(2, RoundingMode.HALF_UP);
        p.setPrice(price);

        // Step 3: Recategorize if original category was Electronics and post-discount price > 500
        if ("Electronics".equalsIgnoreCase(p.getOriginalCategory())
                && price.compareTo(FIVE_HUNDRED) > 0) {
            p.setCategory("Premium Electronics");
        }

        // Step 4: Assign price range from final price
        p.setPriceRange(PriceRangeUtil.fromPrice(price));
    }
}

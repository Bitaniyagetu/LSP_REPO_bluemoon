package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Represents a single product record (one row in the CSV file).
 * <p>
 * Each {@code Product} holds:
 * <ul>
 *   <li>{@code productId} – unique identifier</li>
 *   <li>{@code name} – product name (transformed to uppercase during processing)</li>
 *   <li>{@code price} – product price, stored as {@link BigDecimal} and rounded to two decimals</li>
 *   <li>{@code category} – current category (may be recategorized during transformations)</li>
 *   <li>{@code originalCategory} – the original category from the input CSV (used for Premium Electronics rule)</li>
 *   <li>{@code priceRange} – derived classification label ("Low", "Medium", "High", "Premium")</li>
 * </ul>
 *
 * <p>This class is mutable: {@code name}, {@code price}, {@code category}, 
 * and {@code priceRange} may be updated during transformation. 
 * The {@code productId} and {@code originalCategory} remain fixed.</p>
 */
public class Product {
    private final int productId;
    private String name;
    private BigDecimal price; // always kept at scale(2) where needed
    private String category;
    private final String originalCategory; // captured at load time
    private String priceRange; // derived field after transformations

    /**
     * Constructs a new {@code Product} with the given properties.
     *
     * @param productId unique product identifier
     * @param name product name as read from input
     * @param price product price before transformations
     * @param category category as read from input
     */
    public Product(int productId, String name, BigDecimal price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.originalCategory = category; // stored for recategorization rule
    }

    /**
     * @return the unique product ID
     */
    public int getProductId() { return productId; }

    /**
     * @return the current product name (possibly transformed to uppercase)
     */
    public String getName() { return name; }

    /**
     * @return the current product price (rounded to two decimals where applicable)
     */
    public BigDecimal getPrice() { return price; }

    /**
     * @return the current category (may differ from original if recategorized)
     */
    public String getCategory() { return category; }

    /**
     * @return the original category value from the input CSV
     */
    public String getOriginalCategory() { return originalCategory; }

    /**
     * @return the derived price range label ("Low", "Medium", "High", "Premium")
     */
    public String getPriceRange() { return priceRange; }

    /**
     * Updates the product name.
     *
     * @param name the new product name (e.g., uppercase during transformation)
     */
    public void setName(String name) { this.name = name; }

    /**
     * Updates the product price.
     *
     * @param price the new product price (rounded as necessary)
     */
    public void setPrice(BigDecimal price) { this.price = price; }

    /**
     * Updates the product category.
     *
     * @param category the new category (e.g., "Premium Electronics")
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * Updates the derived price range label.
     *
     * @param priceRange the new price range ("Low", "Medium", "High", "Premium")
     */
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }

    /**
     * Builds a CSV row string in the assignment’s required column order:
     * {@code ProductID,Name,Price,Category,PriceRange}.
     *
     * @return a CSV row string (e.g., {@code 2,LAPTOP,899.99,Premium Electronics,Premium})
     */
    public String toCsvRow() {
        // Keep two decimals exactly; use toPlainString to avoid scientific notation
        String priceStr = price.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
        return productId + "," + name + "," + priceStr + "," + category + "," + priceRange;
    }
}

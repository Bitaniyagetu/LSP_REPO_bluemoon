package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Author: Bitaniya Getu
 * Assignment 2 – CSV ETL Pipeline (Extract → Transform → Load)
 *
 * This program reads product data from {@code data/products.csv}, applies a series
 * of transformations to the data (uppercasing product names, applying discounts,
 * recategorization, and price range calculation), and writes the transformed data
 * to {@code data/transformed_products.csv}.
 *
 * It demonstrates:
 * <ul>
 *   <li>File I/O using relative paths</li>
 *   <li>CSV parsing and data transformations</li>
 *   <li>Separation of concerns (extract, transform, load methods)</li>
 *   <li>Error handling for missing or empty input files</li>
 * </ul>
 */
public class ETLPipeline {

    /**
     * Entry point for the program.
     * Orchestrates the Extract → Transform → Load process.
     *
     * @param args not used in this assignment
     */
    public static void main(String[] args) {
        new ETLPipeline().run();
    }

    /**
     * Runs the ETL pipeline: extract, transform, load.
     * Also prints a run summary to the console.
     */
    public void run() {
        try {
            List<String[]> rows = extract();
            List<String[]> transformedRows = transform(rows);
            load(transformedRows);

            // Print run summary
            System.out.println("Run Summary:");
            System.out.println("Rows read: " + (rows.size() - 1)); // exclude header
            System.out.println("Rows transformed: " + (transformedRows.size() - 1));
            System.out.println("Rows skipped: 0");
            System.out.println("Output written to: data/transformed_products.csv");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Extract step of the ETL pipeline.
     * Reads {@code data/products.csv} into memory.
     *
     * @return a list of String arrays representing rows (first row is header)
     * @throws IOException if the file is missing or cannot be read
     */
    private List<String[]> extract() throws IOException {
        Path inputPath = Paths.get("data/products.csv");

        if (!Files.exists(inputPath)) {
            throw new IOException("Input file not found: " + inputPath.toString());
        }

        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line.split(","));
            }
        }

        return rows;
    }

    /**
     * Transform step of the ETL pipeline.
     * Applies transformations to each row:
     * <ol>
     *   <li>Convert product names to uppercase</li>
     *   <li>Apply 10% discount to Electronics (round half-up to 2 decimals)</li>
     *   <li>Reclassify Electronics > $500 as Premium Electronics</li>
     *   <li>Add a PriceRange column based on final price</li>
     * </ol>
     *
     * @param rows input rows (first row is header, not transformed)
     * @return transformed rows with an added PriceRange column
     */
    private List<String[]> transform(List<String[]> rows) {
        List<String[]> transformed = new ArrayList<>();

        // Add header with new PriceRange column
        String[] header = {"ProductID", "Name", "Price", "Category", "PriceRange"};
        transformed.add(header);

        // Process rows (skip header at index 0)
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String productId = row[0];
            String name = row[1].toUpperCase(Locale.ROOT);
            double price = Double.parseDouble(row[2]);
            String category = row[3];

            // Apply 10% discount for Electronics
            if (category.equalsIgnoreCase("Electronics")) {
                price = price * 0.9;
                price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }

            // Reclassify as Premium Electronics if final price > 500
            if (price > 500.0 && category.equalsIgnoreCase("Electronics")) {
                category = "Premium Electronics";
            }

            // Determine PriceRange
            String priceRange;
            if (price <= 10.0) {
                priceRange = "Low";
            } else if (price <= 100.0) {
                priceRange = "Medium";
            } else if (price <= 500.0) {
                priceRange = "High";
            } else {
                priceRange = "Premium";
            }

            transformed.add(new String[]{
                productId,
                name,
                String.valueOf(price),
                category,
                priceRange
            });
        }

        return transformed;
    }

    /**
     * Load step of the ETL pipeline.
     * Writes transformed data into {@code data/transformed_products.csv}.
     *
     * @param rows transformed rows to write (includes header row)
     * @throws IOException if the file cannot be written
     */
    private void load(List<String[]> rows) throws IOException {
        Path outputPath = Paths.get("data/transformed_products.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(writer)) {

            for (String[] row : rows) {
                printWriter.println(String.join(",", row));
            }
        }
    }
}

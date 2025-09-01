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
import java.util.Locale;

/**
 * Name: <Your Firstname Lastname>
 * Assignment 2 - CSV ETL Pipeline (Extract -> Transform -> Load)
 * 
 * Reads data/products.csv (relative path), transforms rows per spec, and writes
 * data/transformed_products.csv with a header. Prints a run summary.
 */
public class ETLPipeline {
    public static void main(String[] args) {
        new ETLPipeline().run();
    }

    public void run() {
        Path input  = Paths.get("data", "products.csv");                     // relative
        Path output = Paths.get("data", "transformed_products.csv");         // relative

        // Case C: missing input file -> message and exit (no crash)
        if (!Files.exists(input)) {
            System.err.println("ERROR: Input file not found at " + input.toAbsolutePath()
                    + " (expected relative path data/products.csv). Exiting.");
            return;
        }

        // Make sure data/ exists before writing output
        try {
            Files.createDirectories(output.getParent());
        } catch (IOException e) {
            System.err.println("ERROR: Could not create output directory: " + output.getParent());
            return;
        }

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = Files.newBufferedReader(input, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(output, StandardCharsets.UTF_8);
             PrintWriter out = new PrintWriter(bw)) {

            // Always write header
            out.println("ProductID,Name,Price,Category,PriceRange");

            String line = br.readLine(); // read header from input (do not transform)
            if (line == null) {
                // Case B: empty input file (no header even) -> we've already written header only
                printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
                return;
            }

            // Process each data row
            int lineNum = 1; // header line
            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty()) {
                    // empty line -> skip
                    continue;
                }

                rowsRead++;
                String[] parts = line.split(",", -1); // no quoted commas per spec
                if (parts.length != 4) {
                    System.err.println("WARN: Skipping line " + lineNum + " (expected 4 columns): " + line);
                    rowsSkipped++;
                    continue;
                }

                String idStr      = parts[0].trim();
                String name       = parts[1].trim();
                String priceStr   = parts[2].trim();
                String category   = parts[3].trim();

                try {
                    int productId = Integer.parseInt(idStr);

                    // (1) uppercase name
                    String upperName = name.toUpperCase(Locale.ROOT);

                    // parse price as BigDecimal for precise rounding
                    BigDecimal price = new BigDecimal(priceStr);

                    // (2) discount if Electronics
                    String originalCategory = category;
                    BigDecimal finalPrice = price;
                    if ("Electronics".equals(originalCategory)) {
                        finalPrice = price.multiply(new BigDecimal("0.90")); // 10% off
                    }

                    // round HALF_UP to 2 decimals
                    finalPrice = finalPrice.setScale(2, RoundingMode.HALF_UP);

                    // (3) recategorize if finalPrice > 500 AND original category was Electronics
                    if ("Electronics".equals(originalCategory) && finalPrice.compareTo(new BigDecimal("500.00")) > 0) {
                        category = "Premium Electronics";
                    }

                    // (4) price range from FINAL price
                    String priceRange = toPriceRange(finalPrice);

                    // output order: ProductID,Name,Price,Category,PriceRange
                    out.printf("%d,%s,%.2f,%s,%s%n",
                            productId, upperName, finalPrice.doubleValue(), category, priceRange);

                    rowsTransformed++;

                } catch (NumberFormatException ex) {
                    System.err.println("WARN: Skipping line " + lineNum + " (bad number): " + line);
                    rowsSkipped++;
                } catch (Exception ex) {
                    System.err.println("WARN: Skipping line " + lineNum + " (" + ex.getMessage() + "): " + line);
                    rowsSkipped++;
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: I/O problem: " + e.getMessage());
            return;
        }

        printSummary(rowsRead, rowsTransformed, rowsSkipped, output);
    }

    private static String toPriceRange(BigDecimal price) {
        if (price.compareTo(new BigDecimal("10.00")) <= 0) {
            return "Low";
        } else if (price.compareTo(new BigDecimal("100.00")) <= 0) {
            return "Medium";
        } else if (price.compareTo(new BigDecimal("500.00")) <= 0) {
            return "High";
        } else {
            return "Premium";
        }
    }

    private static void printSummary(int read, int transformed, int skipped, Path outputPath) {
        System.out.println("Run Summary");
        System.out.println("-----------");
        System.out.println("Rows read:        " + read);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
        System.out.println("Output written to: " + outputPath.toAbsolutePath());
    }
}

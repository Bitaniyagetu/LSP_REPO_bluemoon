package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class for Assignment 3: Object-Oriented ETL Pipeline.
 * <p>
 * This class coordinates the ETL (Extract → Transform → Load) process:
 * <ul>
 *   <li><b>Extract</b>: Reads product data from {@code data/products.csv}.</li>
 *   <li><b>Transform</b>: Applies all transformation rules 
 *       (uppercase names, discounts, recategorization, price range classification).</li>
 *   <li><b>Load</b>: Writes the transformed data to {@code data/transformed_products.csv} 
 *       with a header row included.</li>
 *   <li><b>Summary</b>: Prints a run summary including rows read, transformed, skipped, 
 *       and the output file path.</li>
 * </ul>
 *
 * <p>The application uses relative paths and must be run from the project root 
 * (the directory containing {@code src/} and {@code data/}).</p>
 */
public class ETLPipelineApp {
    /** Input CSV file path (relative to project root). */
    private static final Path INPUT  = Paths.get("data", "products.csv");
    /** Output CSV file path (relative to project root). */
    private static final Path OUTPUT = Paths.get("data", "transformed_products.csv");

    /**
     * Program entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new ETLPipelineApp().run();
    }

    /**
     * Executes the ETL process:
     * <ol>
     *   <li>Extracts products from the input CSV.</li>
     *   <li>Transforms each product using {@link ProductTransformer}.</li>
     *   <li>Loads transformed products into the output CSV.</li>
     *   <li>Prints a summary of the run (rows read, transformed, skipped).</li>
     * </ol>
     *
     * <p>Handles missing or empty input files gracefully by printing
     * appropriate error messages instead of crashing.</p>
     */
    public void run() {
        int rowsRead = 0;
        int rowsSkipped = 0;
        int rowsTransformed = 0;

        List<Product> products = new ArrayList<>();

        // === Extract ===
        try {
            CsvUtil.ReadResult rr = CsvUtil.readProducts(INPUT);
            products = rr.products;
            rowsRead = rr.rowsRead;
            rowsSkipped = rr.rowsSkipped;
        } catch (NoSuchFileException missing) {
            System.err.println("ERROR: Input file not found: " + INPUT);
            System.err.println("Make sure you run from the project root and that data/products.csv exists.");
            printSummary(rowsRead, rowsTransformed, rowsSkipped);
            return;
        } catch (IOException io) {
            System.err.println("ERROR reading input: " + io.getMessage());
            printSummary(rowsRead, rowsTransformed, rowsSkipped);
            return;
        }

        // === Transform ===
        Transformer<Product> transformer = new ProductTransformer();
        for (Product p : products) {
            transformer.transform(p);
        }
        rowsTransformed = products.size();

        // === Load ===
        try {
            CsvUtil.writeProducts(OUTPUT, products);
        } catch (IOException io) {
            System.err.println("ERROR writing output: " + io.getMessage());
        }

        // === Summary ===
        printSummary(rowsRead, rowsTransformed, rowsSkipped);
        System.out.println("Output written to: " + OUTPUT.toString());
    }

    /**
     * Prints a run summary to standard output.
     *
     * @param read number of rows read from input
     * @param transformed number of rows successfully transformed
     * @param skipped number of rows skipped due to parsing or validation errors
     */
    private void printSummary(int read, int transformed, int skipped) {
        System.out.println("Run Summary");
        System.out.println("Rows read:        " + read);
        System.out.println("Rows transformed: " + transformed);
        System.out.println("Rows skipped:     " + skipped);
    }
}

package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and writing {@link Product} objects
 * to and from CSV files. This class enforces the assignment's
 * constraints:
 * <ul>
 *   <li>Delimiter is a comma (",").</li>
 *   <li>No quoted fields or embedded commas are supported.</li>
 *   <li>Always writes a header row in output files.</li>
 * </ul>
 *
 * <p>This class is final and has a private constructor to prevent instantiation.
 * All functionality is exposed via static methods.</p>
 */
public final class CsvUtil {
    private CsvUtil() {}

    /**
     * Represents the result of reading a CSV file.
     * Contains the parsed list of {@link Product} objects,
     * along with counters for rows read and rows skipped.
     */
    public static final class ReadResult {
        /** Parsed products from the CSV file. */
        public final List<Product> products;
        /** Number of data rows encountered (excludes header). */
        public final int rowsRead;
        /** Number of rows skipped due to invalid formatting or parse errors. */
        public final int rowsSkipped;

        /**
         * Constructs a new {@code ReadResult}.
         *
         * @param products list of successfully parsed products
         * @param rowsRead number of rows read (excluding header)
         * @param rowsSkipped number of rows skipped due to parsing issues
         */
        public ReadResult(List<Product> products, int rowsRead, int rowsSkipped) {
            this.products = products;
            this.rowsRead = rowsRead;
            this.rowsSkipped = rowsSkipped;
        }
    }

    /**
     * Reads products from a CSV file.
     *
     * <p>Each row must contain four fields:
     * {@code ProductID, Name, Price, Category}.
     * Rows that fail to parse are skipped and counted.</p>
     *
     * @param input the relative path to the CSV file (e.g., {@code data/products.csv})
     * @return a {@link ReadResult} containing parsed products and row counters
     * @throws IOException if an I/O error occurs, including
     *         {@link NoSuchFileException} if the file is missing
     */
    public static ReadResult readProducts(Path input) throws IOException {
        if (!Files.exists(input)) {
            throw new NoSuchFileException(input.toString());
        }

        List<Product> products = new ArrayList<>();
        int rowsRead = 0;
        int rowsSkipped = 0;

        try (BufferedReader br = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line = br.readLine(); // header
            if (line == null) {
                return new ReadResult(products, 0, 0); // empty file with no header
            }

            while ((line = br.readLine()) != null) {
                rowsRead++;
                String[] t = line.split(",", -1);
                if (t.length != 4) {
                    rowsSkipped++;
                    continue;
                }
                try {
                    int id = Integer.parseInt(t[0].trim());
                    String name = t[1].trim();
                    BigDecimal price = new BigDecimal(t[2].trim());
                    String category = t[3].trim();
                    products.add(new Product(id, name, price, category));
                } catch (Exception parseFailure) {
                    rowsSkipped++;
                }
            }
        }
        return new ReadResult(products, rowsRead, rowsSkipped);
    }

    /**
     * Writes a list of products to a CSV file.
     * <p>The output file will always begin with a header row:
     * {@code ProductID,Name,Price,Category,PriceRange}.</p>
     *
     * @param output the relative path to the CSV file (e.g., {@code data/transformed_products.csv})
     * @param products the list of products to write (may be empty; header still written)
     * @throws IOException if an I/O error occurs during writing
     */
    public static void writeProducts(Path output, List<Product> products) throws IOException {
        if (output.getParent() != null) {
            Files.createDirectories(output.getParent());
        }
        try (BufferedWriter bw = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();
            for (Product p : products) {
                bw.write(p.toCsvRow());
                bw.newLine();
            }
        }
    }
}

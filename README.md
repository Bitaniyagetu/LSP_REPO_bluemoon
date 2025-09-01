# Assignment 2 – CSV ETL Pipeline in Java

## Author
Bitaniya Getu

## Overview
This project implements a simple **ETL (Extract → Transform → Load) pipeline** in Java.  
The program:
- Reads product data from `data/products.csv`
- Applies transformations to product names, prices, and categories
- Adds a `PriceRange` column
- Writes the transformed data to `data/transformed_products.csv`

---

## Project Structure
LSP_Project/
├── src/
│ └── org/howard/edu/lsp/assignment2/
│ └── ETLPipeline.java
├── data/
│ ├── products.csv
│ └── transformed_products.csv


---

## Features & Transformations
1. **Extract**  
   - Reads CSV from `data/products.csv` (relative path, not absolute).  
   - Handles missing or empty input file gracefully.  

2. **Transform**  
   - Converts all product names to **UPPERCASE**  
   - Applies **10% discount** to products in the Electronics category  
   - Rounds prices to **2 decimals (half-up)**  
   - Reclassifies Electronics over **$500** → Premium Electronics  
   - Adds `PriceRange` column:  
     - $0–10 → Low  
     - $10.01–100 → Medium  
     - $100.01–500 → High  
     - $500.01+ → Premium  

3. **Load**  
   - Writes transformed results to `data/transformed_products.csv`  
   - Always includes a header row.  

---

## How to Run
From the **project root** (not inside `src/`):

``bash
javac src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp src org.howard.edu.lsp.assignment2.ETLPipeline
Input file: data/products.csv

Output file: data/transformed_products.csv

Example Input (products.csv)
ProductID,Name,Price,Category
1,Book,12.99,Education
2,Laptop,999.99,Electronics
3,Notebook,2.49,Stationery
4,Headphones,199.99,Electronics
5,Pencil,0.99,Stationery
6,Smartphone,699.49,Electronics

Example Output (transformed_products.csv)
ProductID,Name,Price,Category,PriceRange
1,BOOK,12.99,Education,Medium
2,LAPTOP,899.99,Premium Electronics,Premium
3,NOTEBOOK,2.49,Stationery,Low
4,HEADPHONES,179.99,Electronics,High
5,PENCIL,0.99,Stationery,Low
6,SMARTPHONE,629.54,Premium Electronics,Premium

Assumptions

CSVs have no quoted fields or commas inside fields.

Input always contains a header row.

Price values are valid decimal numbers.

Program is run from the project root, so relative paths work.

Design Notes

Code is split into extract, transform, load methods for clarity.

Used BigDecimal with RoundingMode.HALF_UP for correct rounding.

Summary printed after run: rows read, transformed, skipped, and output path.

JavaDocs are included for all methods and the class.

Testing

Case A (Normal Input): 6 rows processed correctly, all transformations applied.

Case B (Empty Input): Only header written to output file.

Case C (Missing Input): Program prints error message and exits gracefully.

Compared outputs against provided golden files.

AI Usage

This assignment was completed with assistance from ChatGPT.

How AI was used:

Helped generate method structure (extract, transform, load).

Provided Javadoc formatting examples.

Helped create this README template.

Prompt Example:

"My professor said 'make sure you use JavaDocs.' Can you show me how to add them properly to my ETLPipeline.java file?"

AI Response Excerpt:

"Add a class-level Javadoc and method-level Javadocs for extract, transform, load... Example:
/** Extract step of the ETL pipeline. Reads products.csv into memory. */"

How I used it:

I copied and adapted the generated Javadocs to match my code.

I modified AI-suggested README sections to match my testing and assignment details.

External Sources

Baeldung – Java CSV
https://www.baeldung.com/java-csv

Used to review different ways to split CSV lines. Adapted simple string split approach since no quoted fields were required.


---

✅ This README is fully polished, copy–paste ready, and covers **all rubric requirements**.  

Do you also want me to draft a **short `ai_transcript.txt` file** so you can drop it in the repo alongside the README (in case your professor prefers it separated)?

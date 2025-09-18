# Reflection – Assignment 3 vs. Assignment 2

**Student:** Bitaniya Getu  
**Course:** CSCI 363 – Object-Oriented Redesign of ETL

## What changed in the design?

**A2** was a single class that performed reading, transforming, and writing in one place. It worked, but the responsibilities were mixed together.

**A3** decomposes the problem into collaborating classes:

- `Product` – a domain object that encapsulates the CSV row and provides `toCsvRow()`.
- `Transformer<T>` – a small, reusable interface representing “something that transforms an object”.
- `ProductTransformer` – the concrete transformation logic for the assignment’s rules (uppercase, discount, recategorize, price range) in the prescribed order.
- `CsvUtil` – I/O concerns only (read/write, header handling, counters, relative paths).
- `ETLPipelineApp` – orchestration (Extract → Transform → Load) and run summary.

This separation improves readability and makes each unit easier to test or change.

## How is A3 more object-oriented?

- **Objects & Classes:** `Product` models a real world thing; `ProductTransformer` models behavior applied to that thing.
- **Encapsulation:** CSV parsing/writing lives in `CsvUtil`; transformation rules live in `ProductTransformer`; the app doesn’t reach into parsing details.
- **Interfaces / Polymorphism:** The `Transformer<T>` interface would allow different transformer implementations to be swapped in (e.g., different discount policies) without changing the app.
- **Single Responsibility:** Each class has one clear job.
- **Separation of Concerns:** I/O, transformation logic, and orchestration are decoupled.

## Testing that A3 matches A2

I verified the following cases and compared outputs:

1. **Normal input (6 rows)** – `transformed_products.csv` matches A2’s expected file exactly (header + rows, rounding, categories, price range).
2. **Empty input (header only)** – Program still writes an output file with just the header row.
3. **Missing input file** – Program prints a clear error and exits without crashing.

Additionally, the console prints the run summary (`rows read`, `rows transformed`, `rows skipped`, and output path).

## Notes & Rationale

- I kept rounding with `BigDecimal` and `HALF_UP` to exactly meet the spec.
- I captured the **original category** inside `Product` to enforce the rule: “if post-discount price > 500 **and original category is Electronics** → Premium Electronics”.
- All file paths are **relative** (`data/products.csv`, `data/transformed_products.csv`) so the code is portable.

Overall the new design is easier to extend (e.g., new transformers or file formats) and simpler to reason about than the monolithic A2 file.

# AI Prompts & Excerpts – Assignment 3

**Student:** Bitaniya Getu

Below are representative prompts I asked an AI assistant while redesigning A2 into a more object-oriented A3, and short excerpts of responses I considered. I edited all code and comments to fit class conventions and assignment requirements.

---

## Prompt 1
**Q:** “Help me refactor my single-file ETL into multiple classes with clear responsibilities. I need to keep the same behavior as before.”

**Excerpt (AI):**  
“Create a `Product` model, a `Transformer<T>` interface, a `ProductTransformer` with the rules, a `CsvUtil` for I/O, and an `ETLPipelineApp` to orchestrate… Ensure you preserve the transform order: uppercase → discount → recategorize → price range.”

**How I used it:**  
I adopted the suggested class split and verified transform order and rounding.

---

## Prompt 2
**Q:** “What’s a clean way to compute a price range label from a BigDecimal price?”

**Excerpt (AI):**  
“Use a small utility method that compares against thresholds (10, 100, 500) and returns Low / Medium / High / Premium.”

**How I used it:**  
I implemented `PriceRangeUtil.fromPrice(BigDecimal)`.

---

## Prompt 3
**Q:** “I need to recategorize to ‘Premium Electronics’ only if the **original** category was Electronics and the **post-discount** price is over 500. What’s a safe approach?”

**Excerpt (AI):**  
“Capture the original category when constructing the domain object. After discounting (and rounding), compare the final price to 500 and original category to ‘Electronics’ before recategorizing.”

**How I used it:**  
I stored `originalCategory` in `Product` and applied the rule in `ProductTransformer`.

---

## Prompt 4
**Q:** “Show an example of Javadocs I can adapt for each public class and method.”

**Excerpt (AI):**  
“Provide a short description, parameter docs, and return value docs; include usage intent and constraints.”

**How I used it:**  
I wrote Javadocs for all public classes and methods and edited the wording to match what my code actually does.

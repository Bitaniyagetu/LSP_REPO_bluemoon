package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
        // Required exact prints:
        System.out.println("Circle radius 3.0 \u2192 area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 \u2192 area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 \u2192 area = " + AreaCalculator.area(4));

        // Exception demo (any invalid dimension works)
        try {
            AreaCalculator.area(-1.0);  // invalid radius
            System.out.println("This line should not print.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
      Brief note on overloading vs separate names (2–3 sentences):
      Using method overloading keeps a single, memorable API name (area)
      while letting the compiler choose the correct formula by signature.
      It improves readability at call sites and groups related behavior.
      Separate names (circleArea, rectangleArea, etc.) can work, but they
      scatter the concept and slightly reduce discoverability.
    */
}

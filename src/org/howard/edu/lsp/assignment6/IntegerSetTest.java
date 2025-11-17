package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test suite for the IntegerSet class.
 * <p>
 * This class provides unit tests that validate every public method
 * in IntegerSet. Tests include normal behavior as well as edge cases,
 * such as duplicates, empty sets, and exception handling.
 * </p>
 */
public class IntegerSetTest {

    /**
     * Tests that clear() removes all elements and isEmpty() returns true.
     */
    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.clear();
        assertTrue(set.isEmpty());
    }

    /**
     * Tests that length() returns the correct number of elements.
     */
    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertEquals(2, set.length());
    }

    /**
     * Tests that equals() returns true when two sets contain the same
     * elements in any order.
     */
    @Test
    public void testEquals() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();

        s1.add(1);
        s1.add(2);

        s2.add(2);
        s2.add(1);

        assertTrue(s1.equals(s2));
    }

    /**
     * Tests contains() for both present and absent values.
     */
    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        assertTrue(set.contains(5));
        assertFalse(set.contains(10));
    }

    /**
     * Tests that largest() returns the maximum value.
     */
    @Test
    public void testLargest() {
        IntegerSet set = new IntegerSet();
        set.add(4);
        set.add(9);
        set.add(1);
        assertEquals(9, set.largest());
    }

    /**
     * Ensures largest() throws an IllegalStateException when called on
     * an empty set.
     */
    @Test
    public void testLargestException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.largest());
    }

    /**
     * Tests that smallest() returns the minimum value.
     */
    @Test
    public void testSmallest() {
        IntegerSet set = new IntegerSet();
        set.add(4);
        set.add(9);
        set.add(1);
        assertEquals(1, set.smallest());
    }

    /**
     * Ensures smallest() throws an IllegalStateException when called on
     * an empty set.
     */
    @Test
    public void testSmallestException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.smallest());
    }

    /**
     * Tests that add() does not allow duplicates.
     */
    @Test
    public void testAddNoDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(1);  // duplicate attempt
        assertEquals(1, set.length());
    }

    /**
     * Tests that remove() deletes the element if present.
     */
    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.remove(1);
        assertFalse(set.contains(1));
    }

    /**
     * Tests the union() operation and verifies correct modification.
     */
    @Test
    public void testUnion() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);

        s1.union(s2);
        assertEquals("[1, 2, 3]", s1.toString());
    }

    /**
     * Tests the intersect() operation and verifies correct modification.
     */
    @Test
    public void testIntersect() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);

        s1.intersect(s2);
        assertEquals("[2]", s1.toString());
    }

    /**
     * Tests the diff() operation (set difference).
     */
    @Test
    public void testDiff() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);

        s1.diff(s2);
        assertEquals("[1]", s1.toString());
    }

    /**
     * Tests the complement() operation (other \ this).
     */
    @Test
    public void testComplement() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);

        s1.complement(s2);
        assertEquals("[3]", s1.toString());
    }

    /**
     * Tests that toString() correctly formats the set.
     */
    @Test
    public void testToString() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertEquals("[1, 2]", set.toString());
    }
}

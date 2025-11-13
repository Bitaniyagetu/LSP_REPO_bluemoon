package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IntegerSetTest {

    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.clear();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertEquals(2, set.length());
    }

    @Test
    public void testEquals() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(1);
        assertTrue(s1.equals(s2));
    }

    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        assertTrue(set.contains(5));
        assertFalse(set.contains(3));
    }

    @Test
    public void testLargest() {
        IntegerSet set = new IntegerSet();
        set.add(1); set.add(10); set.add(3);
        assertEquals(10, set.largest());
    }

    @Test
    public void testLargestException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.largest());
    }

    @Test
    public void testSmallest() {
        IntegerSet set = new IntegerSet();
        set.add(8); set.add(2); set.add(5);
        assertEquals(2, set.smallest());
    }

    @Test
    public void testSmallestException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.smallest());
    }

    @Test
    public void testAddNoDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(1);
        assertEquals(1, set.length());
    }

    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.remove(3);
        assertFalse(set.contains(3));
    }

    @Test
    public void testUnion() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);
        s1.union(s2);
        assertEquals("[1, 2, 3]", s1.toString());
    }

    @Test
    public void testIntersect() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(2); s2.add(3);
        s1.intersect(s2);
        assertEquals("[2]", s1.toString());
    }

    @Test
    public void testDiff() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2); s1.add(3);
        s2.add(2);
        s1.diff(s2);
        assertEquals("[1, 3]", s1.toString());
    }

    @Test
    public void testComplement() {
        IntegerSet s1 = new IntegerSet();
        IntegerSet s2 = new IntegerSet();
        s1.add(1); s1.add(2);
        s2.add(1); s2.add(3);
        s1.complement(s2);   // s1 = s2 \ s1 = [3]
        assertEquals("[3]", s1.toString());
    }

    @Test
    public void testToString() {
        IntegerSet set = new IntegerSet();
        set.add(1); set.add(2);
        assertEquals("[1, 2]", set.toString());
    }
}

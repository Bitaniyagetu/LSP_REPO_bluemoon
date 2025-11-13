package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class representing a mathematical set of integers.
 * No duplicates and supports standard set operations.
 */
public class IntegerSet  {
    private List<Integer> set = new ArrayList<Integer>();

    /** Clears the internal representation of the set. */
    public void clear() {
        set.clear();
    }

    /** Returns the number of elements in the set. */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if this set is equal to another object.
     * Two sets are equal if they contain the same elements in ANY order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerSet)) return false;

        IntegerSet other = (IntegerSet) o;

        if (this.length() != other.length()) return false;

        List<Integer> copy1 = new ArrayList<>(this.set);
        List<Integer> copy2 = new ArrayList<>(other.set);

        Collections.sort(copy1);
        Collections.sort(copy2);

        return copy1.equals(copy2);
    }

    /** Returns true if the set contains the value. */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /** Returns the largest item in the set. Throws exception if empty. */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty.");
        }
        return Collections.max(set);
    }

    /** Returns the smallest item in the set. Throws exception if empty. */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty.");
        }
        return Collections.min(set);
    }

    /** Adds a unique item to the set. */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /** Removes an item from the set if present. */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /** Set union: this = this ∪ other */
    public void union(IntegerSet other) {
        for (int value : other.set) {
            if (!this.set.contains(value)) {
                this.set.add(value);
            }
        }
    }

    /** Set intersection: this = this ∩ other */
    public void intersect(IntegerSet other) {
        this.set.retainAll(other.set);
    }

    /** Set difference: this = this \ other */
    public void diff(IntegerSet other) {
        this.set.removeAll(other.set);
    }

    /** Set complement: this = other \ this */
    public void complement(IntegerSet other) {
        List<Integer> result = new ArrayList<>(other.set);
        result.removeAll(this.set);
        this.set = result;
    }

    /** Returns true if the set is empty. */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /** Returns formatted string representation of the set. */
    @Override
    public String toString() {
        return set.toString();
    }
}

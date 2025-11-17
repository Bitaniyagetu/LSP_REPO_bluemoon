package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * IntegerSet models a mathematical set of unique integers.
 * It supports standard set operations such as union, intersection,
 * difference, complement, and basic queries.
 */
public class IntegerSet {

    /** Internal list storing unique integers. */
    private List<Integer> set = new ArrayList<>();

    /**
     * Clears all elements from the set.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set.
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this set with another object for equality.
     * Two sets are equal if they contain the same elements, regardless of order.
     *
     * @param o the object to compare with.
     * @return true if both sets contain the same values, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntegerSet)) {
            return false;
        }

        IntegerSet other = (IntegerSet) o;

        return this.set.containsAll(other.set) &&
               other.set.containsAll(this.set);
    }

    /**
     * Checks whether the set contains the given value.
     *
     * @param value integer to search for.
     * @return true if found, false otherwise.
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest element in the set.
     *
     * @return the largest integer.
     * @throws IllegalStateException if the set is empty.
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot find largest element in an empty set.");
        }

        int max = set.get(0);
        for (int num : set) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    /**
     * Returns the smallest element in the set.
     *
     * @return the smallest integer.
     * @throws IllegalStateException if the set is empty.
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot find smallest element in an empty set.");
        }

        int min = set.get(0);
        for (int num : set) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    /**
     * Adds a value to the set if it is not already present.
     *
     * @param item the value to add.
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes a value from the set if present.
     *
     * @param item the value to remove.
     */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /**
     * Performs the union operation: this = this ∪ other.
     *
     * @param other another IntegerSet to union with.
     */
    public void union(IntegerSet other) {
        for (int num : other.set) {
            if (!this.set.contains(num)) {
                this.set.add(num);
            }
        }
    }

    /**
     * Performs intersection: this = this ∩ other.
     *
     * @param other another IntegerSet.
     */
    public void intersect(IntegerSet other) {
        set.retainAll(other.set);
    }

    /**
     * Performs set difference: this = this \ other.
     *
     * @param other another IntegerSet.
     */
    public void diff(IntegerSet other) {
        set.removeAll(other.set);
    }

    /**
     * Performs complement: this = other \ this.
     *
     * @param other another IntegerSet.
     */
    public void complement(IntegerSet other) {
        List<Integer> newSet = new ArrayList<>();

        for (int num : other.set) {
            if (!this.set.contains(num)) {
                newSet.add(num);
            }
        }

        this.set = newSet;
    }

    /**
     * Checks whether the set is empty.
     *
     * @return true if the set contains no elements.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a string representation of the set.
     * Elements appear inside brackets, comma-separated.
     *
     * @return formatted string of the set.
     */
    @Override
    public String toString() {
        return set.toString();
    }
}

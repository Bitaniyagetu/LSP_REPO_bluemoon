package org.howard.edu.lsp.assignment3;

/**
 * A generic transformation interface used by the ETL pipeline.
 * <p>
 * Implementations define how to apply one or more transformation
 * rules to an object of type {@code T}.
 * </p>
 *
 * @param <T> the type of object this transformer operates on
 */
public interface Transformer<T> {
    /**
     * Applies in-place transformations to the given item.
     * <p>
     * Implementations may modify the state of {@code item} directly,
     * but should not produce other side effects outside of this object.
     * </p>
     *
     * @param item the object to transform; may be mutated
     */
    void transform(T item);
}

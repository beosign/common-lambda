package de.beosign.common.lambda.function;

/**
 * A function that may throw a specific exception.
 * 
 * @author florian
 * @param <T> input type
 * @param <R> result type
 * @param <E> exception type that may be thrown during execution
 */
@FunctionalInterface
public interface CheckedExceptionFunction<T, R, E extends Exception> extends CheckedFunction<T, R> {

    @Override
    R applyWithException(T t) throws E;

}

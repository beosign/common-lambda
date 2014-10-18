package de.beosign.common.lambda.function;

import java.util.function.Consumer;

/**
 * A {@link Consumer} that may throw a specific exception.
 * 
 * @author florian
 * @param <T> input type
 * @param <E> exception type that may be thrown during execution
 */
public interface CheckedExceptionConsumer<T, E extends Exception> extends CheckedConsumer<T> {

    @Override
    void acceptWithException(T t) throws E;
}

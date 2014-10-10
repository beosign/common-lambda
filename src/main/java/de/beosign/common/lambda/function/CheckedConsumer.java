package de.beosign.common.lambda.function;

import java.util.function.Consumer;

import de.beosign.common.lambda.LambdaUtil;

/**
 * A {@link Consumer} that may throw an {@link Exception}.
 * 
 * @author florian
 * @param <T> the type of the input to the operation
 */
@FunctionalInterface
public interface CheckedConsumer<T> extends Consumer<T> {

    /**
     * Performs this operation on the given argument, may throw an {@link Exception}.
     *
     * @param elem the elem
     * @throws Exception the exception
     */
    void acceptWithException(T elem) throws Exception;

    /**
     * Convert from a {@link CheckedConsumer} to a {@link Consumer}.
     *
     * @param <T> input type
     * @param c checked consumer
     * @return normal consumer
     */
    public static <T> Consumer<T> convert(CheckedConsumer<T> c) {
        return t -> c.accept(t);
    }

    /**
     * Applies logic, catching the exception and rethrowing it.
     *
     * @param t input
     */
    @Override
    default void accept(T t) {
        try {
            acceptWithException(t);
        } catch (Exception e) {
            LambdaUtil.<RuntimeException> sneakyThrow(e);
        }
    }

}

package de.beosign.common.lambda.function;

import java.util.function.Function;

import de.beosign.common.lambda.LambdaUtil;

/**
 * A {@link Function} that may throw an {@link Exception}.
 * 
 * @author florian
 * @param <T> input type
 * @param <R> return type
 */
@FunctionalInterface
public interface CheckedFunction<T, R> extends Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     * @throws Exception may throw any Exception
     */
    R applyWithException(T t) throws Exception;

    /**
     * Converts given {@link CheckedFunction} to a normal function.
     *
     * @param <T> input type
     * @param <R> result type
     * @param f the {@link CheckedFunction}
     * @return the converted {@link Function}
     */
    public static <T, R> Function<T, R> convert(CheckedFunction<T, R> f) {
        return elem -> f.apply(elem);
    }

    /**
     * Applies logic, catching the exception and rethrowing it.
     *
     * @param t input
     * @return result
     */
    @Override
    default R apply(T t) {
        try {
            return applyWithException(t);
        } catch (Exception e) {
            LambdaUtil.<RuntimeException> sneakyThrow(e);
        }
        return null;
    }

}

package de.beosign.common.lambda;

import java.util.function.Function;

import de.beosign.common.lambda.function.CheckedFunction;

/**
 * A work processor implementation that uses a given function to do its job.
 * 
 * @author florian
 * @param <T> type of the object to be processed
 * @param <R> result type
 */
public class DefaultWorkProcessor<T, R> extends AbstractWorkProcessor<T, R> {
    private final Function<T, R> processor;

    /**
     * Creates a processor based on the given function.
     * 
     * @param processor processor (function)
     */
    public DefaultWorkProcessor(Function<T, R> processor) {
        this.processor = processor;
    }

    /**
     * Creates a processor based on the given function.
     *
     * @param processor processor (function)
     */
    public DefaultWorkProcessor(CheckedFunction<T, R> processor) {
        this((Function<T, R>) processor);
    }

    @Override
    protected R doProcess(T item) throws Exception {
        return processor.apply(item);
    }

}

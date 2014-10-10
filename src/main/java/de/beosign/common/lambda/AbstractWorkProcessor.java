package de.beosign.common.lambda;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An execution environment for parallel processing that stores caught exceptions and results in a map.
 * <b>Usage</b> example: Lambda's that throw an exception.
 * 
 * <pre>
 * StringWorkProcessor wp = new StringWorkProcessor();
 * strings.parallelStream().forEach(s -&gt; wp.process(s));
 * System.out.println(wp.getCaughtExceptions());
 * </pre>
 * 
 * @param <T> type of the object to be processed
 * @param <R> result type
 */
public abstract class AbstractWorkProcessor<T, R> {
    private Map<T, Throwable> caughtExceptions = Collections.synchronizedMap(new HashMap<T, Throwable>());
    private Map<T, R> results = Collections.synchronizedMap(new HashMap<T, R>());

    /**
     * Processing logic is implemented here.
     *
     * @param item item to process
     * @return result
     * @throws Exception the exception
     */
    protected abstract R doProcess(T item) throws Exception;

    /**
     * Process an item and store any exception in a map that can be later retrieved via {@link AbstractWorkProcessor#getCaughtExceptions()} and store
     * the result in a map that can later be retrieved via {@link AbstractWorkProcessor#getResults()}.
     *
     * @param item item to process
     * @return result
     */
    public R process(T item) {
        R result = null;
        try {
            result = doProcess(item);
            results.put(item, result);
        } catch (Exception t) {
            caughtExceptions.put(item, t);

        }
        return result;
    }

    /**
     * Gets the caught exceptions. Call this after all the work is done.
     *
     * @return the caught exceptions
     */
    public Map<T, Throwable> getCaughtExceptions() {
        return caughtExceptions;
    }

    /**
     * Returns all results as a map from the processed item to its result.
     * 
     * @return results all results as a map from the processed item to its result
     */
    public Map<T, R> getResults() {
        return results;
    }
}

package de.beosign.common.lambda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains utility classes for dealing with lambdas.
 */
public interface LambdaUtil {

    /**
     * Transforms a collection of <code>T</code> into an <code>ArrayList</code> of <code>R</code> using the given transformation function.
     *
     * @param <T> source collection element type
     * @param <R> result collection element type
     * @param col source collection, may be <code>null</code>. In this case, an empty collection is returned.
     * @param mapper mapper function
     * @return transformed collection, never <code>null</code>
     */
    static <T, R> List<R> transform(Collection<T> col, Function<? super T, ? extends R> mapper) {
        if (col == null || col.isEmpty()) {
            return new ArrayList<R>();
        } else {
            return col.stream().map(mapper).collect(Collectors.toList());
        }
    }

    /**
     * Throws the given throwable. The caller can explicitly cast his (caught) exception to a {@link RuntimeException} by using target typing.
     * Example:
     * 
     * <pre>
     * public interface ConsumerCheckException&lt;T&gt;{
     *     void accept(T elem) throws Exception;
     * }
     * 
     *  public class Wrappers {
     *      public static &lt;T&gt; Consumer&lt;T&gt; rethrow(ConsumerCheckException&lt;T&gt; c) {
     *        return elem -&gt; {
     *          try {
     *            c.accept(elem);
     *          } catch (Exception ex) {
     * 
     * 
     *          within sneakyThrow() we cast to the parameterized type T. 
     *          In this case that type is RuntimeException. 
     *          At runtime, however, the generic types have been erased, so 
     *          that there is no T type anymore to cast to, so the cast
     *          disappears.
     * 
     *            LambdaUtil.&lt;RuntimeException&gt;sneakyThrow(ex);
     *          }
     *        };
     *      }
     * </pre>
     *
     * @param <T> exception type
     * @param t the throwable
     * @return nothing, because t is always thrown
     * @throws T always throws the passed throwable
     * @see <a href="http://www.mail-archive.com/javaposse@googlegroups.com/msg05984.html">link</a>
     */
    @SuppressWarnings("unchecked")
    static <T extends Throwable> T sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

}

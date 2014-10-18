package de.beosign.common.lambda.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import de.beosign.common.lambda.function.CheckedExceptionFunction;
import de.beosign.common.lambda.function.CheckedFunction;

/**
 * Tests around the {@link CheckedFunction} interface.
 * 
 * @author Florian Dahlmanns
 */
public class CheckedFunctionsTest {

    /**
     * Verifies that the apply method throws an {@link Exception}.
     */
    @Test(expected = Exception.class)
    public void testSneakyFunction() {
        Function<String, Integer> f = new CheckedExceptionFunctionAlwaysExceptionThrower();
        f.apply("asd");
    }

    /**
     * Verifies that the apply method throws an {@link IOException}.
     */
    @Test(expected = IOException.class)
    public void testSneakyExceptionConsumer() {
        Function<String, Integer> f = new CheckedExceptionFunctionAlwaysIOExceptionThrower();
        f.apply("asd");
    }

    /**
     * Verifies that a {@link FileNotFoundException} (a <i>checked</i> exception) is raised from within the lambda expression.
     */
    @Test(expected = FileNotFoundException.class)
    public void testSneakyCheckedFunctionWithConversion() {
        List<String> strings = Arrays.asList("Hello", "World", "Test");
        strings.stream().map(CheckedFunction.convert(s -> new FileInputStream(s))).collect(Collectors.toList());
    }

    /**
     * A function that always throws an exception.
     * 
     * @author Florian Dahlmanns
     */
    private static class CheckedExceptionFunctionAlwaysExceptionThrower implements CheckedFunction<String, Integer> {
        @Override
        public Integer applyWithException(String t) throws Exception {
            throw new Exception("Not calculatable " + t);
        }
    }

    /**
     * A function that always throws an {@link IOException}.
     * 
     * @author Florian Dahlmanns
     */
    private static class CheckedExceptionFunctionAlwaysIOExceptionThrower implements CheckedExceptionFunction<String, Integer, IOException> {
        @Override
        public Integer applyWithException(String t) throws IOException {
            throw new IOException("Failed to open dummy " + t);
        }
    }

}

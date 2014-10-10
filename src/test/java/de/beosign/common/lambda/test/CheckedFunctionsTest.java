package de.beosign.common.lambda.test;

import java.io.IOException;
import java.util.function.Function;

import org.junit.Test;

import de.beosign.common.lambda.function.CheckedExceptionFunction;
import de.beosign.common.lambda.function.CheckedFunction;

public class CheckedFunctionsTest {

    @Test(expected = Exception.class)
    public void testSneakyFunction() {
        Function<String, Integer> f = new CheckedExceptionFunctionAlwaysExceptionThrower();
        f.apply("asd");

    }

    @Test(expected = IOException.class)
    public void testSneakyExceptionConsumer() {
        Function<String, Integer> f = new CheckedExceptionFunctionAlwaysIOExceptionThrower();
        f.apply("asd");

    }

    private static class CheckedExceptionFunctionAlwaysExceptionThrower implements CheckedFunction<String, Integer> {

        @Override
        public Integer applyWithException(String t) throws Exception {
            throw new Exception("Not calculatable " + t);
        }

    }

    private static class CheckedExceptionFunctionAlwaysIOExceptionThrower implements CheckedExceptionFunction<String, Integer, IOException> {

        @Override
        public Integer applyWithException(String t) throws IOException {
            throw new IOException("Failed to open dummy " + t);
        }

    }

}

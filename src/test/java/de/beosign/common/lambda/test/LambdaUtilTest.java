package de.beosign.common.lambda.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.beosign.common.lambda.LambdaUtil;
import de.beosign.common.lambda.function.CheckedConsumer;

/**
 * Tests for {@link LambdaUtil} class.
 * 
 * @author Florian Dahlmanns
 */
public class LambdaUtilTest {

    /**
     * Tests the transform method.
     */
    @Test
    public void transformTestDefault() {
        List<String> strings = new ArrayList<String>();
        strings.add("J");
        strings.add("Hi");

        List<Integer> res = LambdaUtil.transform(strings, s -> s.length());
        Assert.assertEquals(res.size(), 2);
        Assert.assertEquals(res.get(0), new Integer(1));
        Assert.assertEquals(res.get(1), new Integer(2));
    }

    /**
     * Tests that the transform method can handle a null parameter.
     */
    @Test
    public void transformTestNull() {
        List<String> strings = null;

        List<Integer> res = LambdaUtil.transform(strings, s -> s.length());
        Assert.assertEquals(res.size(), 0);

    }

    /**
     * Test that we can actually rethrow an Exception without requiring this method to declare it.
     */
    @Test(expected = Exception.class)
    public void testSneakyThrowException() {
        CheckedConsumer<String> cce = new CheckedExceptionConsumerAlwaysThrower();

        try {
            cce.acceptWithException("asd");
        } catch (Exception e) {
            LambdaUtil.<RuntimeException> sneakyThrow(e);
        }
    }

    /**
     * Consumer that always throws an {@link Exception}.
     * 
     * @author florian
     */
    private static class CheckedExceptionConsumerAlwaysThrower implements CheckedConsumer<String> {
        @Override
        public void acceptWithException(String s) throws Exception {
            throw new Exception(s + " not accepted");
        }

    }
}

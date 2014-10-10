package de.beosign.common.lambda.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.beosign.common.lambda.AbstractWorkProcessor;

public class AbstractWorkProcessorTest {

    @Test
    public void testResultsAndExceptions() {
        List<String> strings = new ArrayList<String>();
        strings.add("Hi");
        strings.add("Jo");
        strings.add("Fine");
        strings.add("Fine2");
        strings.add("Fine3");
        strings.add("Fine4");
        strings.add("J");
        strings.add("Fine5");

        StringWorkProcessor wp = new StringWorkProcessor();
        strings.parallelStream().forEach(s -> wp.process(s));
        System.out.println(wp.getResults());
        System.out.println(wp.getCaughtExceptions());

        Assert.assertEquals(wp.getResults().get("Fine"), "Fin");
        Assert.assertEquals(wp.getResults().get("Fine2"), "Fin");
        Assert.assertEquals(wp.getResults().get("Fine3"), "Fin");
        Assert.assertEquals(wp.getResults().get("Fine4"), "Fin");
        Assert.assertEquals(wp.getResults().get("Fine5"), "Fin");

        Assert.assertNotNull(wp.getCaughtExceptions().get("J"));
        Assert.assertNotNull(wp.getCaughtExceptions().get("Jo"));
        Assert.assertNotNull(wp.getCaughtExceptions().get("Hi"));

    }

    private static class StringWorkProcessor extends AbstractWorkProcessor<String, String> {

        @Override
        protected String doProcess(String item) {
            System.out.println("Processing " + item);
            return item.substring(0, 3);
        }

    }

}

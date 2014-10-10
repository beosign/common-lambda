package de.beosign.common.lambda.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.beosign.common.lambda.DefaultWorkProcessor;

public class DefaultWorkProcessorTest {

    private List<String> strings = new ArrayList<String>();

    @Before
    public void initList() {
        strings.clear();

        strings.add("1");
        strings.add("22");
        strings.add("333");
        strings.add("4444");
    }

    @Test
    public void testNoException() {
        DefaultWorkProcessor<String, Integer> wp = new DefaultWorkProcessor<>(s -> s.length());

        strings.parallelStream().forEach(s -> wp.process(s));

        for (Entry<String, Integer> entry : wp.getResults().entrySet()) {
            Assert.assertTrue(entry.getKey().length() == entry.getValue());
        }
    }

    @Test
    public void testRuntimeException() {
        DefaultWorkProcessor<String, Integer> wp = new DefaultWorkProcessor<>(s -> {
            if (s.length() == 4) {
                throw new RuntimeException(s);
            } else {
                return s.length();
            }
        });

        strings.parallelStream().forEach(wp::process);

        for (Entry<String, Integer> entry : wp.getResults().entrySet()) {
            System.out.println(entry);
            Assert.assertTrue(entry.getKey().length() == entry.getValue());
        }

        Assert.assertTrue(wp.getCaughtExceptions().size() == 1);
        System.out.println(wp.getCaughtExceptions());

    }

    @Test
    public void testCheckedException() {
        DefaultWorkProcessor<String, Integer> wp = new DefaultWorkProcessor<>(s -> {
            if (s.length() == 4) {
                throw new Exception(s);
            } else {
                return s.length();
            }
        });

        strings.parallelStream().forEach(wp::process);

        for (Entry<String, Integer> entry : wp.getResults().entrySet()) {
            System.out.println(entry);
            Assert.assertTrue(entry.getKey().length() == entry.getValue());
        }

        System.out.println(wp.getCaughtExceptions());
        Assert.assertTrue(wp.getCaughtExceptions().size() == 1);
    }
}

package com.zuehlke;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;


public class RoundRobinTest {

    private static final List<String> list = Arrays.asList("A","B","C", "D");

    @Test
    public void allElementsIteration(){
        Iterator<String> testee = new RoundRobin<>(list, 2).iterator();

        assertEquals("C",testee.next());
        assertEquals("D",testee.next());
        assertEquals("A",testee.next());
        assertEquals("B",testee.next());
    }

    @Test
    public void lastElementOfIteration(){
        Iterator<String> testee = new RoundRobin<>(list, 2).iterator();

        assertEquals("C",testee.next());
        assertEquals("D",testee.next());
        assertEquals("A",testee.next());
        assertEquals("B",testee.next());

        assertFalse(testee.hasNext());
    }

    @Test(expected = RuntimeException.class)
    public void afterEndOfIterationThroughAllElements(){
        Iterator<String> testee = new RoundRobin<>(list, 2).iterator();

        assertEquals("C",testee.next());
        assertEquals("D",testee.next());
        assertEquals("A",testee.next());
        assertEquals("B",testee.next());

        testee.next();
    }
}
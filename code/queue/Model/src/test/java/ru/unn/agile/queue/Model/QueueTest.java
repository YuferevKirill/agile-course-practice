package ru.unn.agile.queue.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class QueueTest {
    @Test
    public void canCreateQueueWithInteger() {
        Queue<Integer> newQueue = new Queue<>();

        assertNotNull(newQueue);
    }

    @Test
    public void canCreateQueueWithDouble() {
        Queue<Double> newQueue = new Queue<>();

        assertNotNull(newQueue);
    }

    @Test
    public void canCreateQueueWithString() {
        Queue<String> newQueue = new Queue<>();

        assertNotNull(newQueue);
    }

    @Test
    public void canPushIntegerElementToQueue() {
        Queue<Integer> newQueue = new Queue<>();
        Integer expectedElement = 1;

        newQueue.push(expectedElement);

        assertEquals(expectedElement, newQueue.getTail());
    }

    @Test
    public void canPushDoubleElementToQueue() {
        Queue<Double> newQueue = new Queue<>();
        Double expectedElement = 1.0;

        newQueue.push(expectedElement);

        assertEquals(expectedElement, newQueue.getTail());
    }

    @Test
    public void canPushStringElementToQueue() {
        Queue<String> newQueue = new Queue<>();
        String expectedElement = "Test string";

        newQueue.push(expectedElement);

        assertEquals(expectedElement, newQueue.getTail());
    }


    @Test(expected = IllegalArgumentException.class)
    public void throwWhenPushNullElementToQueue() {
        Queue<Integer> newQueue = new Queue<>();

        newQueue.push(null);
    }

    @Test
    public void isQueueWithoutIntegerElementsEmptiness() {
        Queue<Integer> newQueue = new Queue<>();

        assertTrue(newQueue.isEmpty());
    }

    @Test
    public void isQueueWithoutDoubleElementsEmptiness() {
        Queue<Double> newQueue = new Queue<>();

        assertTrue(newQueue.isEmpty());
    }

    @Test
    public void isQueueWithoutStringElementsEmptiness() {
        Queue<String> newQueue = new Queue<>();

        assertTrue(newQueue.isEmpty());
    }

    @Test
    public void isQueueWithOneIntegerElementNotEmpty() {
        Queue<Integer> newQueue = new Queue<>();
        newQueue.push(1);

        assertFalse(newQueue.isEmpty());
    }

    @Test
    public void isQueueWithOneDoubleElementNotEmpty() {
        Queue<Double> newQueue = new Queue<>();
        newQueue.push(1.0);

        assertFalse(newQueue.isEmpty());
    }

    @Test
    public void isQueueWithOneStringElementNotEmpty() {
        Queue<String> newQueue = new Queue<>();
        newQueue.push("Test string");

        assertFalse(newQueue.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetTailIntegerElementFromEmptyQueue() {
        Queue<Integer> newQueue = new Queue<>();

        newQueue.getTail();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetTailDoubleElementFromEmptyQueue() {
        Queue<Double> newQueue = new Queue<>();

        newQueue.getTail();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetTailStringElementFromEmptyQueue() {
        Queue<String> newQueue = new Queue<>();

        newQueue.getTail();
    }

    @Test
    public void canClearQueue() {
        Queue<String> newQueue = new Queue<>();
        String firstElement = "Test string number one";
        newQueue.push(firstElement);

        newQueue.clear();

        assertTrue(newQueue.isEmpty());
    }

    @Test
    public void canPopIntegerElementFromQueue() {
        Queue<Integer> newQueue = new Queue<>();
        Integer expectedElement = 1;
        newQueue.push(expectedElement);

        Integer actualElement = newQueue.pop();

        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void canPopDoubleElementFromQueue() {
        Queue<Double> newQueue = new Queue<>();
        Double expectedElement = 1.0;
        newQueue.push(expectedElement);

        Double actualElement = newQueue.pop();

        assertEquals(expectedElement, actualElement);
    }

    @Test
    public void canPopStringElementFromQueue() {
        Queue<String> newQueue = new Queue<>();
        String expectedElement = "Test string";
        newQueue.push(expectedElement);

        String actualElement = newQueue.pop();

        assertEquals(expectedElement, actualElement);
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenPopIntegerFromEmptyQueue() {
        Queue<Integer> newQueue = new Queue<>();

        newQueue.pop();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenPopDoubleFromEmptyQueue() {
        Queue<Double> newQueue = new Queue<>();

        newQueue.pop();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenPopStringFromEmptyQueue() {
        Queue<String> newQueue = new Queue<>();

        newQueue.pop();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetHeadIntegerElementFromEmptyQueue() {
        Queue<Integer> newQueue = new Queue<>();

        newQueue.getHead();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetHeadDoubleElementFromEmptyQueue() {
        Queue<Double> newQueue = new Queue<>();

        newQueue.getHead();
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenGetHeadStringElementFromEmptyQueue() {
        Queue<String> newQueue = new Queue<>();

        newQueue.getHead();
    }

    @Test
    public void isFIFOPrincipleWorkWithInteger() {
        Queue<Integer> newQueue = new Queue<>();
        Integer firstExpectedElement = 1;
        Integer secondExpectedElement = 2;
        newQueue.push(firstExpectedElement);
        newQueue.push(secondExpectedElement);

        Integer firstActualElement = newQueue.pop();
        Integer secondActualElement = newQueue.pop();

        assertEquals(firstExpectedElement, firstActualElement);
        assertEquals(secondExpectedElement, secondActualElement);
    }

    @Test
    public void isFIFOPrincipleWorkWithDouble() {
        Queue<Double> newQueue = new Queue<>();
        Double firstExpectedElement = 1.0;
        Double secondExpectedElement = 2.0;
        newQueue.push(firstExpectedElement);
        newQueue.push(secondExpectedElement);

        Double firstActualElement = newQueue.pop();
        Double secondActualElement = newQueue.pop();

        assertEquals(firstExpectedElement, firstActualElement);
        assertEquals(secondExpectedElement, secondActualElement);
    }

    @Test
    public void isFIFOPrincipleWorkWithString() {
        Queue<String> newQueue = new Queue<>();
        String firstExpectedElement = "Test string number one";
        String secondExpectedElement = "Test string number two";
        newQueue.push(firstExpectedElement);
        newQueue.push(secondExpectedElement);

        String firstActualElement = newQueue.pop();
        String secondActualElement = newQueue.pop();

        assertEquals(firstExpectedElement, firstActualElement);
        assertEquals(secondExpectedElement, secondActualElement);
    }
}

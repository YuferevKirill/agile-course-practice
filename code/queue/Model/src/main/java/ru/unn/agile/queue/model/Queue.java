package ru.unn.agile.queue.model;

import java.util.LinkedList;
import java.util.List;

public class Queue<Type> {

    private List<Type> list;

    public Queue() {
        list = new LinkedList<Type>();
    }

    public void push(final Type element) {
        if (element == null) {
            throw new IllegalArgumentException("The item cannot be empty.");
        }

        list.add(element);
    }

    public Type pop() {
        checkIsNotEmpty();

        return list.remove(0);
    }

    public Type getTail() {
        checkIsNotEmpty();

        return list.get(list.size() - 1);
    }

    public Type getHead() {
        checkIsNotEmpty();

        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }

    private void checkIsNotEmpty() {
        if (isEmpty()) {
            throw new NullPointerException("Queue is empty");
        }
    }
}

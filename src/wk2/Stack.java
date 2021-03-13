package wk2;

import java.util.NoSuchElementException;

public class Stack {
    private int n = 0;
    private String[] items;

    public Stack(int size) {
        if (size <= 0) {
            throw new RuntimeException("Stack size can not be less than 1.");
        }
        items = new String[size];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean isFull() {
        return n == items.length;
    }

    public void push(String s) {
        if (isFull()) {
            throw new NoSuchElementException("Error: Stack is full");
        }
        items[n++] = s;
    }

    public String pop() {
        String out;
        if (isEmpty()) {
            throw new NoSuchElementException("Error: Stack is empty");
        }
        out = items[--n];
        items[n] = null;
        return out;
    }

    public int size() {
        return n;
    }
}

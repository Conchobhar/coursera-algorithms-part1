import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int dequeSize = 0;

    private class Node {
        private Item item = null;
        private Node next = null;
        private Node prev = null;
    }
    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Node node = new Node();
        // When creating adding a new node from an empty deque,
        // both front and back are updated (to that node).
        // Otherwise, update the current first node to track the new first node
        // as being `previous` to it, before tracking the new node as first.
        if (isEmpty()) { last = node; } else {
            first.prev = node;
        }
        node.item = item;
        node.next = first;
        first = node;
        dequeSize += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Node node = new Node();
        // Same as for addFirst() but reversed.
        if (isEmpty()) { first = node; } else {
            last.next = node;
        }
        node.item = item;
        node.prev = last;
        last = node;
        dequeSize += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue is empty");
        }
        Item item;
        item = first.item;
        first = first.next;
        // For case where only element is removed we need to
        // set the opposite end node to null explicitly since
        // first == last in this case.
        // Otherwise, after moving the edge tracking node to the next one
        // we need to set the link back to the original edge node to null.
        if (size() > 1) { first.prev = null; }
        else last = null;
        dequeSize -= 1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue is empty");
        }
        Item item;
        item = last.item;
        last = last.prev;
        if (size() > 1) { last.next = null; }
        else first = null;
        dequeSize -= 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        // Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.

        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { return current != null; }
        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else { throw new NoSuchElementException(); }
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addLast(1);
        deque.iterator();
        deque.removeLast();
        deque.iterator();
        // Integer item = deque.removeFirst();


        for (Integer i: deque) {
            StdOut.println("Iterating: '" + i + "'");
        }

    }

}
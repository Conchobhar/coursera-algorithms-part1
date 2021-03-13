import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    /*
    Went for a linked listed queue with both directions tracked
    but a resizing list seems more appropriate for constant time indexing.
     */

    private int queueSize;
    private Node first;
    private Node last;

    private class Node {
        private Item item = null;
        private Node next = null;
        private Node prev = null;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        queueSize = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return queueSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) { first = last; }
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        queueSize += 1;
    }

    private Node sampleNode() {
        int idx = StdRandom.uniform(queueSize);
        Node tracker = first;
        while (idx > 0) {
            tracker = tracker.next;
            idx -= 1;
        }
        return tracker;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item;
        Node tracker = sampleNode();
        item = tracker.item;
        if (tracker != first) {
            tracker.prev.next = tracker.next;
        }
        else first = first.next;
        if (tracker != last) {
            tracker.next.prev = tracker.prev;
        }
        else last = last.prev;
        queueSize -= 1;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        Node tracker = sampleNode();
        return tracker.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomisedQueueIterator();
    }

    private class RandomisedQueueIterator implements Iterator<Item> {
        // Create a copy of the current queue as `crq` to dequeue from
        // to preserve the collection.
        private final RandomizedQueue<Item> crq = new RandomizedQueue<>();
        private RandomisedQueueIterator() {
            Node traverser = new Node();
            traverser.next = first;
            while (traverser.next != null) {
                crq.enqueue(traverser.next.item);
                traverser = traverser.next;
            }
        }
        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { return !crq.isEmpty(); }
        public Item next() {
            if (hasNext()) {
                Item item = crq.dequeue();
                return item;
            } else { throw new NoSuchElementException(); }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        // for (int i = 0; i < 5; i++) {
        //     rq.enqueue(i);
        //     StdOut.println("Enquing: " + i);
        // }
        // for (int i = 0; i < 5; i++) {
        //     StdOut.println("Dequing: " + rq.dequeue());
        // }
        // StdOut.println("Empty? " + rq.isEmpty());

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        Iterator<Integer> i1 = rq.iterator();
        Iterator<Integer> i2 = rq.iterator();
        // for (Integer item : rq) {
        //     StdOut.println("Deque'd item: " + item);
        // }

        while (i1.hasNext()) {
            StdOut.println(i1.next());
            StdOut.println(i2.next());
        }
    }
}
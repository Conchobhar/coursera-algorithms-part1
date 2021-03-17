# Week 2
## Overview
Consider 2 fundamental data types for storing collections - the *stack* and *queue*.
### Stacks and Queues
Implement using either a singly-linked list, or a resizing array. Introduce 2 new Java features, generics and iterators.
Application: Parsing arithmetic expressions to simulating queueing systems.
### Elementary Sorts
Introduce sorting problem and Javas Comparable interface, study two elementary sorting methods (selection and insertion) and a variation (shellsort).
Consider 2 algorithms for uniformly shuffling an array. Application: Computing the convex hull via the Graham scan algorithm.

## Stacks and Queues
Stack - examine item most recently added (LastInFirstOut). Push and Pop to stack end.

Queue - examine the item least recently added (FirstInFirstOut). Enqueue and Dequeue at opposite ends of queue.


### resizing list
For list of some initial length N, when it becomes filled, we can double its length. If it empties back to a quarter of its length
we can then half the array. Halving array size at the quarter fill mark avoids the case of resizing sequentially if one element is repeatedly added and removed at the half way mark.

Resize operation costs o(n) time to copy elements to the new array, but resizing happens less frequently since it takes twice as long each time
to fill it. The amortized

## Elementary Sorts
Need to design a sort for different types of data without
knowledge beforehand of the data type. We can achieve this with:

`callback` - A reference to executable code i.e. passing functions as args to functions

Java uses `interfaces` for this. Compared to Python where all functions are *first-class functions* and can be directly 
treated like any other variable.

Client passes objects to `sort()`, which calls back object's `compareTo()` method.

# Key Java ideas

## Iterators and Iterables
An iterator is something that
`iterables` are objects that can generate `iterators`. An object 
that implements Iterable will have an `iterator()` method defined which
returns an iterator that has three core methods:
- hasNext()
- next()
- remove()

```
public class MyClass<Item> implements Iterable<Item> {
    ...
    public Iterator<Item> iterator() {
        ...
        return new myClassIterator();
    }
    private class myClassIterator implements Iterator<Item> {
        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { ... }
        public Item next() {
            if (hasNext()) {
                ...
            } else { throw new NoSuchElementException(); }
        }
    }
}
```

## Loitering and the Garbage Collector
For an object to be garbage collected and hence released from memory, it needs to 
become completely unreachable by nullifying all references to it. Objects
that are no longer needed, but still have stray references are `loitering`.

## Sorting and Total Order
A `total order` is a binary relation <= that satisfies
- antisymmetry - if v <= w and w <= v then v == w
- transitivity - if v <= w and w <= x then v <= x
- totality - either v<=w or w<=v or both

"Rock-paper-scissors" is an example of something without that is not a total order.

A sort is said to be stable if after sorting by 2 or more comparators, the earlier sorts
retain their order when the later sorts have equal precedence:
```
Initial state:
C,2
B,2
A,1

After sorting by first column:
A,1
B,2
C,2

After sorting by second column, for a stable sort the result is the same. 
The B --> C precedence is maintained.
```
## Type Primitives
Primitives like `int` are not classes and cannot implement an interface, so an `int[]` cannot be passed to something
that expects `Comparable[]` and you must instead used `Integer[]`.

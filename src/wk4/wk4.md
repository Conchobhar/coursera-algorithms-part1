# Week 4
## Overview
Covers Mergesort and Quick sort
## Priority Queues
Variant of sorting generalised to provide flexibility to data structure.
Assuming items have a total order, we can remove the mix/max value.

### Binary Heaps
Binary Tree - Empty or links to nodes to left and right trees
complete Tree - Perfectly balanced, except for bottom level

Property - Height of complete tree with N nodes is lg N. Height only increases when N is a power of 2.

Impose condition - heap ordering. Parents key is no smaller than childrens keys.
Starting at 1 (makes index arithmetic simpler) - left to right enumerate the indices. In actual data structure
we don't need links at all, its a simple array

Array indices arithmetic lets us traverse nodes:
- Parent of node at k is at k/2
- Children of node at k are at 2k and 2k+1

Heap Promotion - When child key becomes larger than parents key.
Swim operation:
```
private void swim(int k)
{
    # i.e. parent = k/2
    while (k > 1 && less(k/2, k))
    {
        exch(k, k/2);
        k = k/2;
    }
}
```
Insertion is then just adding a new element at the end and swimming it to its appropriate position.

Heap Demotion
Determine which child is larger
```
private void sink(int k)
{
    # i.e. children are at 2*k and 2*k+1
    while (2*k <= N)
    {
        int j = 2*k;
        if (k < N && less(j, j+1)) j++;
        if (!less(k, j)) break;
        exch(k, j);
        k = j;
    }
}
```

Delete Max - Exchange root with node at end, then sink it down.
Cost - At most 2 lg N compares.
```
public Key delMax()
{
    Key max = pq[1];
    exch(1, N--);
    sink(1);
    pq[N+1] = null;
    return max
}
```

### Heapsort
- N log N worst-case with in-place sorting
- Not stable

Given a shuffled array, we can
- Construct a max-heap
- Sort array by repeatedly taking the max
So the largest elements move to the end of the array, which is subsequently removed from the scope of the
heap (effective heap size N decremented)ste

Heap construction <= 2 N compares and exchanges.
Heapsort uses <= 2 N lg N compares and exchanges.

## Symbol Tables
Key-value pair abstraction
- Insert a value with a specific key
- Given a key, search for the corresponding value

If we assume null is not allowed as a value, we can return null for keys not in the table and subsequently
 test for key membership by checking if the value is non-null. Likewise, deleting an entry requires only setting
 the value to null.

### Equality test
All java classes inherit a method equals()

Requirements:
- Reflexive: x.equals(x) is true
- Symmetric: x.equals(y) and y.equals(x)
- Transitive: if x.equals(x) and y.equals(z) then x.equals(z)
- Non-null: x.equals(null) is false

## Elementary Implementations

Unordered Linked-List
- Need to search through it to find if key is recorded

Maintain sorted lists for keys and values
- Use binary search to find `rank` of key i.e. how many keys there are
presently smaller than it. Then either confirm if key exists using rank as
index to array, or insert it at that index (necessitating shifting the RHS of the arrays)



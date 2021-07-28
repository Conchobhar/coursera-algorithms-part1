# Week 6

## Hash Tables
Can use HTs to get better performance, if we don't need ordered operations. Space-time tradeoff.

For a given key: hash(key) = integer array index

Additional complexities:
- Need to compute hash
- Need method to determine equality of two keys
- Hash collisions

### Hash function
Should be efficient with each index uniformly likely.

All Java classes inherit a `hashCode()` method, returns a 23-bit int. Default implementation
is the memory address of an object.

Example hashCode for an object with 3 fields:
```
public int hashCode()
{
    int hash = 17; nonzero constant
    hash = 31*hash + myStringField.hashCode();
    hash = 31*hash + myDatetimeField.hashCode();
    hash = 31*hash + ((Double) myDoubleField).hashCode();
    return hash;
}
```

For a user-defined type, can use the following "standard" recipe:
- Combine each significant field with 31x+y rule
- For primitive fields, use wrapper type hashCode() e.g. `((Double) myDoubleField).hashCode()`
- For null, return 0
- For reference type, use hashCode() (applies rule recursively)
- For an array, apply to each element (or use Arrays.deepHashCode())

#### Reference Type
A reference type is a data type that’s based on a class rather than on one of the primitive types that are built in
to the Java language. The class can be a class that’s provided as part of the Java API class library or a class that you
write yourself.

#### Uniform hashing assumption
- Expect a collision after `~SQRT(PI*M/2)` hashes
- Expect every unique hash encountered >= 1 times after `~M*ln(M)` hashes

### Collision resolution strategies
#### Separate Chaining

Maintain linked lists at the table position corresponding to the hash value. Only have to search over
one short LL after indexing the hash.

Two-probe hashing - hash to two keys. Use key with shorter chain.

#### Linear Probing
Maintain extra space in the array. At a collision, increment index until a free space is found for insertion (looping
around at end of array). When searching for a key, we iterating forward from the initial hash index until the key
or an empty element is found. Needs to implement resizing as well to ensure appropriate number of empty elements.

Double hashing - Skip a variable amount each time (not just 1) to eliminate clustering, but makes
deleting more difficult.

Cuckoo hashing - hash two keys. Insert into either, but go to alternative if
one is occupied.



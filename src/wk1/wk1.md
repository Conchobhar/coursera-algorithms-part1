# Week I
## Union-Find

Explore the union-find, for solving dynamic connectivity problem.

General problem solving involves:
- modelling the problem
- finding a base algorithm to solve
- iterate algorithm:
    - measure performance (Time and Space complexity)
    - improve algo

###Dynamic Connectivity
`union` - connects 2 objects

`find` - Determine if connection path exists between 2 objects

Here, objects are uniquely named by enumeration from zero, hence for a zero-index array we can access each object by
indexing with its name.

Properties of objects:
  - `reflexive`   p is connected to itself
  - `symmetric`   if p is is con to q, then q is con to p
  - `transitive`  if p is con to q, and q is con to r, then p is con to r

####Solutions:
#####quick find - eager
Keeps trees flat but at expense of `union`.
#####quick union - lazy
Trees can become tall and `find` is expensive
#####weighted quick union
When connecting 2 trees, avoid putting the larger tree lower than the smaller
i.e. link the root of the smaller tree to the root of the larger.
 
Now we need to track the size of trees.

Depth of any node x is at most  ln(N)

#####path compression QU
While traversing to find the root, we can actively point each object to its grandparent if the parent is not the root.
This way, every other object in the path will point to its grandparent. 
Result is, any sequence of M union-find ops on N objects will 
take <= c( N + M ln*(N)) array accesses.

Where ln*() is iterative log function.
c means "A constant of (...)"

Can be further improved (e.g. Ackerman function) but proven that linear time is never completely reachable.

 Algorithm | initalize | union | find
 --- |---  |---  | ----
 quick-find | N | N | 1
 quick-union | N | N* | N 
 weighted QU | N | ln(N)*| ln(N)
 *incl. cost of finding roots. This is worst case for quick-union.
 
 #####Applications
 Labeling areas in images
 
 Percolation
 
 Consider n*n grid of sites, which are either open or closed.
 If open sites are conected from e.g. from top to bottom, the system percolates. Adjacent sites
 are union'd, and we can introduce single 'virtual' sites at the top and bottom (connected to those rows 
 respectively), from which we can test if connected.
 



## Analysis of Algorithms
We can determine running time as a function of input length from empirical experimentation. Measuring time taking for several 
input sizes (increasing e.g. by a constant factor each time), we can plot T vs N and use linear regression to model:

T(N) = a * N^b

For the case of doubling N each time, the ratio between subsequent time counts = 2^b

Functional forms may have lower order terms, but for large N we can ignore this as their contribution becomes negligible.
### Order-of-growth
Can describe order well enough with small set of functions:


Order-of-Growth | Name
--- |---
1 | constant
log(N) | logarithmic
N | linear
N log(N) | linearithmic
N^2 | quadratic
N^3 | cubic
2^N | exponential
### Theory of Algorithms
Notations for classification:

Notation | provides | use
--- | --- | ---
Tilde | leading term | approximate model
Big Theta | asymptotic growth | classify
Big O | <= Theta(N^2) | upper bound
Big Omega | \>= Theta(N^2) | lower bound
e.g. for 1-sum problem, we are asking if there is any 0's in the array.

Upper and lower bounds are identical, each element must be checked regardless of the contents.
 Hence notation is Theta(N) and Omega(N)
### Memory

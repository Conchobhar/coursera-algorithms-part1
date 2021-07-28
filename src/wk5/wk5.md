# Week 5
## 2-3 Search Trees
Looking at balanced Search Trees which will lead to a symbol table implementation for
all the features looked at so far with guaranteed logarithmic time for search and insert.

This involves looking at 'left leaning red-black Trees' (an implementation of 2-3 Tree)
then look at a generalisation called B-trees.

2-3 tree generalises BSTs to provide flexibility to guarantee faster performance.

- Allow 1 or 2 keys per node where
    - 2-node: one key, two children
    - 3-node: two keys, three children
- Every path from root to null link has the same length

For finding a key, when we traverse to a 3 node, the middle child will contain a key that is between the two
keys of the parent.

For inserting a key, traverse to bottom and add new key to appropriate node. If this creates a 4-node, it
must split into two 2-nodes, with the middle key rising up to the parent node. This splitting continues
until the tree only contains 2 or 3 nodes, including going back to the root at which point the height of the tree
will increase by 1.

## Red-Black BSTs
- Represent 2-3 tree as a BST
- Use *internal* left-leaning links as a "glue" for 3-nodes

We designate the left leaning link as 'red'

Search is the same for elementary BST, but we gain speed due to better balance.

During an insertion operation, we may end up with red links leaning in the wrong direction. To address
this we need to implement `rotation` operations which locally changes a few links between nodes, maintaining
symmetric order and maintaining perfect blank balance. `rotateLeft` is necessary for maintaining the left leaning
property of red links but we also require `rotateRight` for some insertions when we need to temporarily move
the red link away from the left.


## Geometric properties

Given points on a plane, and rectangles (aligned with the plane) we can ask
- Are points inside a given rectangle?
- Do rectangles intersect?

### 1d Range Search
Extension of ordered symbol table. We want to
- Insert key-value pair
- search for key k
- delete key k
- `range search` find all keys between k1, k2
- `range count` count all keys between k1, k2

In our geometric context, keys are points on a line.


rank
"keep rank tree as a field or keep a field which has the size of a field and its easy to compute from that"

range count between lo and hi
if contains(hi) return rank(hi) - rank(lo) + 1;
else            return rank(hi) - rank(lo);

range search between lo and hi
recursively find all keys in R subtree if they can fall in range (i.e. look at right branch from hi)
recursively find all keys in L subtree if they can fall in range (i.e. look at left branch from lo)

### Line segment intersection
Given a set of orthogonal line segments, identify the points of intersection (assume all coordinates used are distinct to simplify degeneracies).

Scanning from left to right along the x-axis:
- When we encounter the left point of a horizontal LS, add the y value to a BST.
- When we encounter the right point of a horizontal LS, remove the y value from the BST.
- When we encounter the points of a vertical LS, the y values of those points define a range to search over the BST for intersecting lines.

### Kd-Trees (K-Dimensional Tree)
Extension of BSTs that allows us to do efficient processing of sets of points in space. Need to extend keys to 2d
i.e. a point. It is a BST  with each split having geometrical meaning.

#### Grid Implementation (2d-Tree)

- Divide space into a regular M-by-M grid of squares
- Create a list of points contained in each square (a 2d array could then be directly indexed to find a relevant square)
- `insert` : add(x,y) to list for corresponding square
- `range search` : examine only those squares intersecting a 2d range query

Space-Time trade off for M. We can balance this with M = sqrt(N) and works well if the points are distributed. We need
a method that can adapt to the distribution of the data.

Recursively partition plane into half-planes.

`range` - Find all points inside a given (axis-aligned) query rectangle.
- Traverse from root node
- Check if point is within rectangle
- Check if points partition line intersects rectangle
    - If not, we can exclude one of the paths relating to the plane without the rectangle

`nearest neighbor search` - Find point closest to a given query point.
- Traverse from root node
- Track and update distance of point from query if it is closer than previous record.
- Can't rule out a sub-tree yet, but we traverse the tree corresponding to the plane containing the query point first
- Points in the plane of the query may enable pruning of the other sub-tree and the plane also contains a greater area where points would qualify as closer.


### Interval Search Trees
Instead of points, our data is intervals (left and right point, or lo and hi)
`Interval intersection query` - Given a query interval, find intervals that intersects it.

Simplification for this: *non-degeneracy assumption* - No two intervals have the same left endpoint.

Create a BST where each node stores an interval (lo, hi) but we only use the left endpoint as a BST key.
We also store the maximum (rightmost) endpoint in the subtree rooted at each node.

`insert` is just BST insertion but with an additional traversal back up to check if the maximum endpoint records need updated.
`search` is more complicated.

Implementation - use a red-black BST


### Rectangle Intersection

Can reduce problem to 1d interval search, sweeping left to right and recording x coordinates

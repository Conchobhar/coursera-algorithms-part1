package wk1;
// Dynamic connectivity

/*
Given set of N obj
  union - connect two objects
  find - is there a path between two?

Properties
  reflexive   p is connected to itself
  symmetric   if p is is con to q, then q is con to p
  transitive  if p is con to q, and q is con to r, then p is con to r
 */

/* QUICK FIND - eager approach
 * Two objects are connected if they hold the same value.
 * Hence, find is a simple equality check for two indices.
 * There is no retention of the order of connection, all connected objects
 * simply point to one other in the group.
 */
public class QuickFindUF
{
    private int[] id;
    public QuickFindUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public boolean connected(int p, int q)
    { return id[p] == id[q]; }

    /*
     * To connect p to q, we find each element set to p's value, and reset it to q's value.
     */
    public void union(int p, int q)
    {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }
}

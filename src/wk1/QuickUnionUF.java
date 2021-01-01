package wk1;

/* QUICK UNION - lazy approach
 * id[i] gives parent of i until id[id[id[..]]] == i (the root)
 * union now only changes one entry in array, connecting it to the root of the joining object
 * we can then compare roots for our connected()
 */
public class QuickUnionUF {
    private int[] id;

    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
    }

    private int root(int c) {
        while (id[c] != c) c = id[c];
        return c;
    }

    public boolean connected(int p, int q) {
        int tpid, tqid;
        tpid = root(p);
        tqid = root(q);
        return tpid == tqid;
    }

    public void union(int p, int q) {
        int rp = root(p);
        int rq = root(q);
        id[rp] = rq;
    }
}

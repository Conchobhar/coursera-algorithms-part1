package wk1;

public class WeightedQuickUnionUF {
    private int[] id, idw;

    public WeightedQuickUnionUF(int n) {
        id = new int[n];
        idw = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            idw[i] = 1;
        }
    }

    private int root(int c) {
        while (id[c] != c) {
            // Path compression single-line optimization. Flatten tree while traversing to root.
            // Make every other node in the path point to its grandparent.
            id[c] = id[id[c]];
            c = id[c];
        }
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
        if (rp == rq) return;
        if (idw[rp] < idw[rq]) {
            id[rp] = rq;
            idw[rp] = idw[p] + idw[q];
        } else {
            id[rq] = rp;
            idw[rq] = idw[q] + idw[p]; // Tree weights are tracked by the root node
        }
    }
}

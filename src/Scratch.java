import wk1.QuickFindUF;
import wk1.QuickUnionUF;

/**
 * Scratch space for running short code examples
 */
public class Scratch {
    public static void main(String[] args) {
        boolean output = true;
        QuickUnionUF qu = new QuickUnionUF(7);
        QuickFindUF qf = new QuickFindUF(7);
        qf.union(2, 5);
        qf.union(3, 2);
        qf.union(1, 5);
        output = qf.connected(2, 5);
        System.out.println(output);
        output = qf.connected(3, 5);
        System.out.println(output);
        output = qf.connected(4, 5);
        System.out.println(output);
    }
}

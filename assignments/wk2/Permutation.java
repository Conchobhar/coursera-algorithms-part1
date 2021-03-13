import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        // StdRandom.setSeed(7);
        // for (int i = 0; i < 3; i++) {
        //     rq.enqueue(String.valueOf(i));
        //     StdOut.println("Enquing: " + i);
        // }
        while ((n > 0) && !rq.isEmpty()) {
        // for (String s : rq) {
            StdOut.println(rq.dequeue());
            n -= 1;
        }
    }
}

package wk3;

// Use QuickSort like iteration to find kth item.
// Takes linear time
public class Selection {

    private Selection() {}

    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        ...
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void show(Comparable[] a) {
        for (Comparable i : a) {
            System.out.println(String.valueOf(i) + ", ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] a = { 1, 3, 34, 2, 7, 9, 3, 67, 10};
        // Integer[] a = { 5, 3, 1};
        o = Selection.select(a, 3);

    }

}

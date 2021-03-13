// Divide and Conquer sort
// O(N log N)
// Recursively split array into halves
public class MergeSort {

    private MergeSort() { }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // Need to re-populate aux every merge as the array is partially sorted.
        for (int k = 0; k < a.length; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)               a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
    }

    // Recursively call sort with smaller lo/hi splits.
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + ((hi - lo) / 2);
        sort(a, aux, 0, mid);
        sort(a, aux, mid+1, hi);
        // Optimization - If largest elem of lhs is smaller than smallest of rhs,
        // then the sub-arrays are already sorted and do not need merged.
        if (!less(a[mid+1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    // MergeSort Top Down
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length -1);
    }

    // MergeSort Bottom Up
    // Iterate over array in 2**n window sizes.
    // Avoid recursion
    public static void sortBU(Comparable[] a) {
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for (int ws = 1; ws < n; ws *= 2) {
            for (int lo = 0; lo < n-ws; lo += ws+ws) {
                int mid  = lo+ws-1;
                int hi = Math.min(lo+ws+ws-1, n-1);
                merge(a, aux, lo, mid, hi);
            }
        }
        assert isSorted(a);
    }


    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void show(Comparable[] a) {
        for (Comparable i : a) {
            System.out.println(String.valueOf(i) + ", ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Integer[] a = { 1, 3, 34, 2, 7, 9, 3, 67, 10};
        // Integer[] a = { 5, 3, 1};
        MergeSort.show(a);
        MergeSort.sort(a);
        MergeSort.show(a);
    }

}

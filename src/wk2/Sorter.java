package wk2;


public class Sorter {

    private Sorter() { }

    // Selection sort
    // For each element in the array, find the smallest element after it and replace it if found.
    // 1/2 N**2
    public static void selectionSort(Comparable[] a) {
        int n = a.length;
        int im;
        for (int i = 0; i < n; i++) {
            im = i;
            for (int j = im; j < n; j++) {
                // Find the min
                if (less(a[j], a[im])) im = j;
                if (i != im) exch(a, i, im);
            }
        }
    }

    // Insertion sort
    // 1/4 N **2, but near linear for partially sorted arrays
    // For each element along the array, we move it backwards until it is no longer
    // greater than the element behind it.
    // This means the left wing of the array behind our initial iterator is kept sorted.
    public static void insertionSort(Comparable[] a) {

        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j-1])) exch(a, j, j-1);
                else break;
            }
        }
    }

    // Shell sort (invented by Shell 1959)
    // Move entries more than one position at a time by h-sorting the array
    // i.e. we perform several insertion sorts but with decreasing strides
    // until the stride == 1, and the full insertion sort is performed over a partially sorted array.
    // with 3x+1 O(n**(3/2))
    public static void shellSort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        // Iteratively increase the stride h until the stride will at most sort 2 elements.
        while (h < n/3) h = 3*h + 1;
        while (h >= 1) {
            for (int i = 1; i < n; i += h) {
                for (int j = i; j > 0; j--) {
                    if (less(a[j], a[j-1])) exch(a, j, j-1);
                    else break;
                }
            }
            h /= 3;
        }

    }

    private static int randInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Knuth Shuffle
    // Uniformly shuffle an array.
    // i.e. all n! (factorial) permutations should be possible, with equal probability.
    // As we increment i, we only exchange i with elements up to and including i.
    public static void knuthShuffle(Comparable[] a) {
        int r, n = a.length;
        for (int i = 1; i < n; i++) {
            r = randInt(0, i);
            exch(a, i, r);
        }
    }

    // Compare if first arg is less than second.
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // Exchange values at two indices
    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] array = {7, 3, 8, 5, 2};
        System.out.println("Current array:");
        for (int i : array) {
            System.out.print(String.valueOf(i) + ' ');
        }
        Sorter.shellSort(array);
        System.out.println("\nSorted array:");
        for (int i : array) {
            System.out.print(String.valueOf(i) + ' ');
        }
    }
}

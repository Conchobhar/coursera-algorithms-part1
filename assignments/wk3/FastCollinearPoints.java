import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lss = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        // if (points.length < 4) return;
        // With sorted array, we know that the first and fourth points will define the longest LineSegment
        // out of the group of points looked over.
        Arrays.sort(points);
        Point[] spoints;

        // Think of p as the origin.
        //
        // For each other point q, determine the slope it makes with p.
        //
        // Sort the points according to the slopes they makes with p.
        //
        // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
        // If so, these points, together with p, are collinear.
        Point refPoint;
        double[] slopes;
        int maxlen = points.length - 1;
        // For each point...
        for (int i = 0; i < points.length; i++) {
            refPoint = points[i];
            spoints = new Point[maxlen];
            slopes = new double[maxlen];
            int si = 0;
            // Compare slopes from subsequent points...
            for (int j = i + 1; j < points.length; j++) {
                spoints[si] = points[j];
                slopes[si++] = refPoint.slopeTo(points[j]);
            }
            Arrays.sort(slopes);
            Arrays.sort(spoints, refPoint.slopeOrder());
            si = 0;
            int sc;
            int ss = slopes.length;
            // si is slope index that is updated to point to the next slope
            // that is different from the prev
            // sc is slope counter for sequential equal slopes
            while (si + 2 < ss) {
                sc = 0;
                // While there is at least one more elem in the array
                // and current and next elements are the same, increment counter.
                while (si + sc < ss && slopes[si + sc] == slopes[si + sc + 1])
                    sc++;
                // If counter is >= 2, then 3 or more elems are the same
                // and we add the maximal line segment between the 4 points.
                if (sc >= 2) {
                    Point maxPoint = Collections.max(
                            Arrays.asList(Arrays.copyOfRange(spoints, si, si + sc + 1)));
                    lss.add(new LineSegment(refPoint, maxPoint));
                }
                si += sc + 1; // Next si value shold be at next higher slope value
            }
            // For each point, we only need to compare with subsequent points
            // in the sorted array, since it will have been accounted for in previous
            // points searches.
            maxlen--;
        }


    }

    public int numberOfSegments() {
        return lss.size();
    }

    public LineSegment[] segments() {
        // Move values from ArrayList to an array
        return lss.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // In in = new In(args[0]);
        In in = new In("input1.txt");
        int x, y, n = in.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            x = in.readInt();
            y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        /*
        Line segment: (10000, 0) -> (3000, 7000)
        Line segment: (3000, 4000) -> (14000, 15000)

        more like
        Line segment: (10000, 0) -> (0, 10000)
        Line segment: (3000, 4000) -> (20000, 21000)
         */

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment ls : bcp.segments()) {
            StdOut.println("Line segment: " + ls.toString());
            ls.draw();
        }
        StdDraw.show();
    }
}

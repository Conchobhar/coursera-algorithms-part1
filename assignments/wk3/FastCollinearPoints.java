import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private static final int SCALE_MAX = 32768; // Appeasing the CheckStyle dieties
    private final ArrayList<LineSegment> lss = new ArrayList<>();


    public FastCollinearPoints(Point[] pointsInput) {
        if (pointsInput == null) throw new IllegalArgumentException();
        for (int i = 0; i < pointsInput.length; i++) {
            if (pointsInput[i] == null) throw new IllegalArgumentException();
        }
        for (int i = 0; i < pointsInput.length; i++) {
            for (int j = i + 1; j < pointsInput.length; j++) {
                if (pointsInput[i].compareTo(pointsInput[j]) == 0) throw new IllegalArgumentException();
            }
        }
        Point[] points = pointsInput.clone();  // Don't mutate the client input!
        Arrays.sort(points);  // Sort initial input

        // Consider a reference point p.
        // For each other point q, determine the slope it makes with p.
        // Sort the points according to the slopes they makes with p.
        // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
        // If so, these points, together with p, are collinear.
        Point refPoint;
        double[] slopes;
        // points = new Point[points.length];
        slopes = new double[points.length];
        // For each point...
        for (int i = 0; i < points.length; i++) {
            refPoint = pointsInput[i];
            int si = 0;
            // Compare slopes from subsequent points...
            for (int j = 0; j < points.length; j++) {
                points[si] = points[j];
                slopes[si++] = refPoint.slopeTo(points[j]);
            }
            Arrays.sort(slopes);
            Arrays.sort(points);
            Arrays.sort(points, refPoint.slopeOrder());
            si = 0;
            int sc;
            int ss = slopes.length;
            // si is slope index that is updated to point to the next slope that is different from the prev
            // sc is slope counter for sequential equal slopes
            while (si + 2 < ss) {
                sc = 0;
                // While there is at least one more elem in the array
                // and current and next elements are the same, increment slope counter.
                while (si + sc + 1 < ss && slopes[si + sc] == slopes[si + sc + 1]) sc++;
                // If counter is >= 2, then >= 3 slopes are the same and >= 4 points are colinear.
                if (sc >= 2) {
                    // Only add a line segment if the reference point is the smallest. This avoids adding subsegments.
                    if (points[si].compareTo(refPoint) > 0) {
                        // Point maxPoint = points[si + sc].compareTo(refPoint) < 0 ? points[si + sc] : refPoint;
                        lss.add(new LineSegment(refPoint, points[si + sc]));
                    }
                }
                si += sc + 1; // Next si value should be at next different slope value
            }
        }
    }

    public int numberOfSegments() {
        return lss.size();
    }

    public LineSegment[] segments() {
        // Move values from ArrayList to an array
        return lss.toArray(new LineSegment[0]);
    }

    private void show(Point[] points) {
        // draw the points

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, SCALE_MAX);
        StdDraw.setYscale(0, SCALE_MAX);
        for (Point p : points) {
            p.draw();
        }
        // FastCollinearPoints bcp = new FastCollinearPoints(points);
        StdOut.println(numberOfSegments());
        for (LineSegment ls : segments()) {
            StdOut.println("Line segment: " + ls.toString());
            ls.draw();
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        // In in = new In(args[0]);
        In in = new In("input9.txt");
        int x, y, n = in.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            x = in.readInt();
            y = in.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints bcp = new FastCollinearPoints(points);
        StdOut.println(bcp.numberOfSegments());
        bcp.show(points);
    }
}

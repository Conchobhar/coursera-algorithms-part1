import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lss = new ArrayList<>();

    public BruteCollinearPoints(Point[] pointsInput) {
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
        // With sorted array, we know that the first and fourth points will define the longest LineSegment
        // out of the group of points looked over.
        Arrays.sort(points);
        Point pi, pj, pk, pf;
        // Find all line segments that connect 4 points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    pi = points[i];
                    pj = points[j];
                    pk = points[k];
                    if (are3PointsColinear(pi, pj, pk)) {
                        for (int f = k + 1; f < points.length; f++) {
                            pf = points[f];
                            if (are3PointsColinear(pi, pj, pf)) lss.add(new LineSegment(pi, pf));
                        }
                    }
                }
            }
        }
    }

    private boolean are3PointsColinear(Point pi, Point pj, Point  pk) {
        return (pi.slopeTo(pj) == pi.slopeTo(pk));
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
        In in = new In("input8.txt");
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

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment ls : bcp.segments()) {
            StdOut.println("Line segment: " + ls.toString());
            ls.draw();
        }
        StdDraw.show();
    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private LineSegment[] lss;
    private int number_lss = 0;

    public BruteCollinearPoints(Point[] points) {
        assert points.length >= 4;
        lss = new LineSegment[points.length];

        // Find all line segments that connect 4 points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (are3PointsColinear(p,q,r)) {
                        for (int l = j + 1; l < points.length; l++) {
                            if (are3PointsColinear(p, q, l)) appendLargestSegment({ p, q, r, s })
                        }
                    }
                }
            }
        }
    }
    private void appendLargestSegment(Point[] points) {
        Point pmin, pmax;
        Arrays.sort(points);
        pmin = points[0];
        pmax = points[3];
        lss[number_lss++] = new LineSegment(pmin, pmax);
    }
    private boolean are3PointsColinear(Point p, Point q, Point  r) {
        assert points.length == 3;
        Point pp = points[0];
        Point pq = points[1];
        Point pr = points[2];
        Point ps = points[3];
        if (pp.slopeTo(pr) == pp.slopeTo(pq)
                && pp.slopeTo(pr) == pp.slopeTo(ps)) return true;
        else return false;

    }
    public int numberOfSegments() {
        return 0;
    }
    // public LineSegment[] segments() {
    //     return [];
    // }
    public static void main(String[] args) {
        // In in = new In(args[0]);
        In in = new In("input6.txt");
        int x, y, n = in.readInt();

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            x = in.readInt();
            y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println(bcp.numberOfSegments());
        // for (LineSegment ls : bcp.segments()) {
        //     StdOut.print("Line segment: " + ls.toString());
        // }
    }
}

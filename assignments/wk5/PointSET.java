import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class PointSET {

    private static final int SCALE_MAX = 1;
    private final SET<Point2D> setOfPoints;

    // construct an empty set of points
    public PointSET() {
        setOfPoints = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return setOfPoints.isEmpty();
    }

    // number of points in the set
    public int size() {
        return setOfPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument can not be null.");
        setOfPoints.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument can not be null.");
        return setOfPoints.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, SCALE_MAX);
        StdDraw.setYscale(0, SCALE_MAX);
        StdDraw.setPenRadius(0.02);
        for (Point2D p : setOfPoints) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Argument can not be null.");
        LinkedList<Point2D> pointsInRange = new LinkedList<Point2D>();
        for (Point2D p : setOfPoints) {
            if ((p.x() <= rect.xmax() && p.x() >= rect.xmin())
                    && (p.y() <= rect.ymax() && p.y() >= rect.ymin())) {
                pointsInRange.add(p);
            }
        }
        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D ref) {
        if (ref == null) throw new IllegalArgumentException("Argument can not be null.");
        if (setOfPoints.isEmpty()) return null;
        // Use the first element available in set to define initial nearest point.
        Point2D nearestPoint = setOfPoints.iterator().next();
        double dis, disMin = ref.distanceSquaredTo(nearestPoint);
        for (Point2D p : setOfPoints) {
            dis = p.distanceSquaredTo(ref);
            if (dis < disMin) {
                disMin = dis;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        In in = new In("circle10.txt");
        double x, y;
        Point2D[] points = new Point2D[10];
        for (int i = 0; i < 10; i++) {
            x = in.readDouble();
            y = in.readDouble();
            points[i] = new Point2D(x, y);
        }

        RectHV r = new RectHV(0, 0, 1, 1);
        PointSET ps = new PointSET();
        for (Point2D p : points) {
            ps.insert(p);
        }
        // ps.draw();
        StdOut.println(ps.range(r));
        StdOut.println(ps.nearest(new Point2D(0.2, 0.2)));
        StdDraw.show();
    }
}

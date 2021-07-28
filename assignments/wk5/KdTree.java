import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedList;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle (not implemented!)
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int depth;      // the depth of the node from root (depth 0)

        public Node() {
        }
    }

    private static final int SCALE_MAX = 1;
    private final Node rootNode = new Node();
    private int nodeCount = 0;
    // Vars to keep track of current best min for nearest neighbours
    private double currentMinDistanceSquared;
    private Point2D nearestPoint;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return rootNode.p == null;
    }

    // number of points in the set
    public int size() {
        return nodeCount;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        Node scout = rootNode;
        if (p == null) throw new IllegalArgumentException("Argument can not be null.");
        int depth = 0;  // Keep track of depth to determine if splitting is vertical or horizontal.
        while (scout != null && scout.p != null) {
            scout = traverse(scout, p);
            if (scout.p.equals(p)) scout = null;
            depth++;
        }
        // If point is not already inserted in the tree (i.e. scout will be null), create a new entry.
        if (scout != null) {
            scout.p = p;
            scout.rt = new Node();
            scout.lb = new Node();
            scout.depth = depth;
            // scout.rect = new RectHV()
            nodeCount++;
        }
    }

    private Node traverse(Node scout, Point2D point) {
        /* Traverse down one node in tree, given a reference point.
         */
        if (scout.depth % 2 == 0) {
            if (point.x() < scout.p.x()) scout = scout.lb;
            else scout = scout.rt;
        }
        else {
            if (point.y() < scout.p.y()) scout = scout.lb;
            else scout = scout.rt;
        }
        return scout;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node scout = rootNode;
        if (p == null) throw new IllegalArgumentException("Argument can not be null.");
        while (scout.p != null) {
            if (p.equals(scout.p)) return true;
            scout = traverse(scout, p);
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, SCALE_MAX);
        StdDraw.setYscale(0, SCALE_MAX);
        StdDraw.setPenRadius(0.02);
        for (Point2D p : new LinkedList<Point2D>()) {
            p.draw();
        }
    }

    private ArrayList<Point2D> findPointsFromNode(Node n, RectHV rect) {
        /* Recursively search down the tree from node n, collecting all points that
        lie within rectangle rect, and only searching branches that could possibly
        contain another point inside the rectangle.
         */
        ArrayList<Point2D> foundPoints = new ArrayList<Point2D>();
        if (n.p == null) return foundPoints;
        if (rect.contains(n.p)) foundPoints.add(n.p);
        // if rect is to L of vert p, or R of vert p, only search one, else both
        if (0 == n.depth % 2) { // vertical split, consider x values
            if (rect.xmin() < n.p.x()) foundPoints.addAll(findPointsFromNode(n.lb, rect));
            if (rect.xmax() >= n.p.x()) foundPoints.addAll(findPointsFromNode(n.rt, rect));
        }
        else { // horizontal split, consider y values
            if (rect.ymin() < n.p.y()) foundPoints.addAll(findPointsFromNode(n.lb, rect));
            if (rect.ymax() >= n.p.y()) foundPoints.addAll(findPointsFromNode(n.rt, rect));
        }
        return foundPoints;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        /* Need to consider while traversing:
            - is rectangle contained to one side of a points splitting axis?
            - prioritize searching left
         */
        if (rect == null) throw new IllegalArgumentException("Argument can not be null.");
        return findPointsFromNode(rootNode, rect);
    }

    // a nearest neighbor in the set to point p - null if the set is empty
    private void searchForNearerPoint(Node n, Point2D refPoint) {
        if (n.p == null) return;  // Exit if node is empty
        double distanceSquared, minPossibleSquaredDistanceFromSecondTree;
        boolean searchLBFirst;
        distanceSquared = n.p.distanceSquaredTo(refPoint);
        if (distanceSquared
                < currentMinDistanceSquared) { // Update min distance and point if new minimum found
            currentMinDistanceSquared = distanceSquared;
            nearestPoint = n.p;
        }
        if (0 == n.depth % 2) { // vertical split, consider x values
            minPossibleSquaredDistanceFromSecondTree = Math.pow(refPoint.x() - n.p.x(), 2);
            searchLBFirst = refPoint.x() < n.p.x();
            // Search first tree
            if (searchLBFirst) searchForNearerPoint(n.lb, refPoint);
            else searchForNearerPoint(n.rt, refPoint);
            // Search second tree
            if (currentMinDistanceSquared > minPossibleSquaredDistanceFromSecondTree) {
                if (searchLBFirst) searchForNearerPoint(n.rt, refPoint);
                else searchForNearerPoint(n.lb, refPoint);
            }
        }
        else { // horizontal split, consider y values
            minPossibleSquaredDistanceFromSecondTree = Math.pow(refPoint.y() - n.p.y(), 2);
            searchLBFirst = refPoint.y() < n.p.y();
            // Search first tree
            if (searchLBFirst) searchForNearerPoint(n.lb, refPoint);
            else searchForNearerPoint(n.rt, refPoint);
            // Search second tree
            if (currentMinDistanceSquared
                    > minPossibleSquaredDistanceFromSecondTree) {  // Search second tree
                if (searchLBFirst) searchForNearerPoint(n.rt, refPoint);
                else searchForNearerPoint(n.lb, refPoint);
            }
        }
    }

    public Point2D nearest(Point2D refPoint) {
        /*
        Starting from root
            determine distance to current point
                - if smaller, set new min
                - search subtree which contains point first
                - search other sub-tree IF min has not been updated to be less than the minimum possible value in this tree
         */
        if (refPoint == null) throw new IllegalArgumentException("Argument can not be null.");
        nearestPoint = rootNode.p;
        currentMinDistanceSquared = Double.POSITIVE_INFINITY;
        searchForNearerPoint(rootNode, refPoint);
        return nearestPoint;
    }

    public static void main(String[] args) {
        In in = new In("circle10.txt");
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        while (!in.isEmpty()) {
            points.add(new Point2D(in.readDouble(), in.readDouble()));
        }

        RectHV r = new RectHV(0.75, 0.25, 1, 1);
        KdTree ps = new KdTree();
        for (Point2D p : points) {
            ps.insert(p);
        }

        Iterable<Point2D> rangeOut = ps.range(r);
        ArrayList<Point2D> rangeList = new ArrayList<Point2D>();
        for (Point2D p : rangeOut) rangeList.add(p);
        StdOut.printf("Range found %d values:\n", rangeList.size());
        StdOut.println(rangeList);

        StdOut.println("\n\nNearest found:\n");
        StdOut.println(ps.nearest(new Point2D(0.19, 0.66)));
        StdOut.println("\n\nContains?\n");
        StdOut.println(ps.contains(new Point2D(0.975528, 0.654508)));
        // StdDraw.show();
    }
}

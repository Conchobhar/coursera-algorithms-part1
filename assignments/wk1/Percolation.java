package wk1.assignment;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private WeightedQuickUnionUF graph, graphFullness;
    private int numOpenSites = 0, gridSize;
    private int dim, vin, vout;  // Dimension, virtual input and output nodes
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Error: Dimension 'n' must be > 0");
        }
        gridSize = (n*n) + 2;
        grid = new boolean[gridSize];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = false;
        }
        // Maintain an identical graph excluding the virtual out site.
        // This is used only to test Fullness and avoids the backwash problem
        // of sites appearing full due to connections to the bottom virtual site.
        graph = new WeightedQuickUnionUF(gridSize);
        graphFullness = new WeightedQuickUnionUF(gridSize);

        dim = n;
        // Define the virtual sites at index 0 and n**2 + 1
        vin = 0;
        vout = (n*n)+1;
        // union the virtual sites to their respective rows
        for (int site = 1; site <= n; site++) {
            graph.union(vin, site);
            graphFullness.union(vin, site);
            graph.union(vout, n*n - site + 1);
        }
    }

    private void validateArrayIndicies(int row, int col) {
        if (!(row <= dim && col <= dim && row > 0 && col > 0)) {
            throw new IllegalArgumentException("Error: row and col values must both be in the range: 0 < value <= grid dimension");
        }
    }

    private boolean areAdjArrayIndiciesValid(int adjRow, int adjCol) {
        return adjRow <= dim && adjCol <= dim && adjRow > 0 && adjCol > 0;
    }

    private int coords2index(int row, int col) {
        return (row-1)*dim + (col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateArrayIndicies(row, col);
        int graphIndex, graphAdjacentIndex, adjRow, adjCol;
        int[][] possibleAdjCells = {{row+1, col}, {row, col+1}, {row-1, col}, {row, col-1}};
        // If not already open, set to open and union with any (of the possible 4) open adjacent cells.
        // If adj cell does not exist (indicies are invalid/ out of bounds) we ignore it.
        if (!grid[coords2index(row, col)]) {
            grid[coords2index(row, col)] = true;
            numOpenSites += 1;
            graphIndex = coords2index(row, col);
            for (int[] adjCell: possibleAdjCells) {
                adjRow = adjCell[0];
                adjCol = adjCell[1];
                if (areAdjArrayIndiciesValid(adjRow, adjCol) && grid[coords2index(adjRow, adjCol)]) {
                    graphAdjacentIndex = coords2index(adjRow, adjCol);
                    graph.union(graphIndex, graphAdjacentIndex);
                    graphFullness.union(graphIndex, graphAdjacentIndex);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateArrayIndicies(row, col);
        return grid[coords2index(row, col)];
    }

    // is the site open and can be connected to a top row open site?
    // i.e. does the system percolate to that site from the top.
    public boolean isFull(int row, int col) {
        validateArrayIndicies(row, col);
        return isOpen(row, col) && (graphFullness.find(vin) == graphFullness.find(coords2index(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // For edge case of only 1 site (excluding virtual sites)
        // We simply check if it is open
        if (gridSize-2 == 1) {
            return grid[1];
        }
        // Test if virtual input node shares the same root at the virtual output node.
        return graph.find(vin) == graph.find(vout);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

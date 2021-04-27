import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private final int dim;
    private int br, bc; // blank row and col

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("Argument can not be null.");
        this.dim = tiles.length;
        this.tiles = new int[this.dim][this.dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                this.tiles[r][c] = tiles[r][c];
                if (tiles[r][c] == 0) {
                    br = r;
                    bc = c;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.dim);
        int padWidth = (int) Math.floor(Math.log10(this.dim * this.dim)) + 2;
        for (int[] row : tiles) {
            sb.append("\n");
            for (int i : row) {
                sb.append(String.format("%" + padWidth + "s", String.valueOf(i)));
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of tiles out of place
    public int hamming() {
        int h = 0, c = 1;
        for (int[] row : tiles) {
            for (int i : row) {
                // blank square is not considered a tile and therefore not costed.
                if (i != c++ && i != 0) h += 1;
            }
        }
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int tr, tc, r = 0, c, m = 0;
        // Compare sum of abs diff between actual row, col (r, c)
        // and true row, col (tr, tc)
        for (int[] row : tiles) {
            c = 0;
            for (int i : row) {
                if (i != 0) {
                    tr = (i - 1) / dim;
                    tc = (i - 1) % dim;
                    m += Math.abs(tr - r) + Math.abs(tc - c);
                }
                c++;
            }
            r++;
        }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that;
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        that = (Board) y;
        if (that.dim != this.dim) return false;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    private class Move {
        // Define the relative location of the tile to be moved into the empty space.
        // i.e. the move delta for row and column (r, c)
        public final int r, c;

        public Move(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private Stack<Move> getLegalMoves() {
        Stack<Move> moves = new Stack<Move>();
        if (br > 0) moves.push(new Move(-1, 0));
        if (br < dim - 1) moves.push(new Move(+1, 0));
        if (bc > 0) moves.push(new Move(0, -1));
        if (bc < dim - 1) moves.push(new Move(0, +1));
        return moves;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        for (Move move : getLegalMoves()) {
            Board neighbour = this.getMovedBoard(move);
            boards.push(neighbour);
        }
        return boards;
    }

    private void swapBlankRow(int r) {
        this.tiles[br][bc] = this.tiles[br + r][bc];
        this.tiles[br + r][bc] = 0;
        this.br = br + r;
    }

    private void swapBlankCol(int c) {
        this.tiles[br][bc] = this.tiles[br][bc + c];
        this.tiles[br][bc + c] = 0;
        this.bc = bc + c;
    }

    private Board getMovedBoard(Move move) {
        // Create new copy of board and
        Board b = new Board(this.tiles);
        if (move.r != 0) b.swapBlankRow(move.r);
        if (move.c != 0) b.swapBlankCol(move.c);
        return b;
    }

    private class Tile {
        // Store row and column index of a tile.
        public final int r, c;

        public Tile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private void swapTiles(Tile t1, Tile t2) {
        // Swap the values of any two tiles.
        int v = this.tiles[t1.r][t1.c];
        this.tiles[t1.r][t1.c] = this.tiles[t2.r][t2.c];
        this.tiles[t2.r][t2.c] = v;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Tile t1, t2;
        Board b = new Board(tiles);
        // If empty tile is not on the first row, swap the first two files of the first row.
        if (br != 0) {
            t1 = new Tile(0, 0);
            t2 = new Tile(0, 1);
        }
        // Other wise swap the next two tiles of the second row.
        else {
            t1 = new Tile(1, 0);
            t2 = new Tile(1, 1);
        }
        b.swapTiles(t1, t2);
        return b;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In("puzzle3x3-02.txt");
        int dim = in.readInt();
        int[][] tiles = new int[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                tiles[r][c] = in.readInt();
            }
        }

        Board b = new Board(tiles);
        int m = b.manhattan();
        int h = b.hamming();
        StdOut.println(m);
        StdOut.println(h);

        // for (int i = 0; i < 4; i++) {
        //     b = b.twin();
        //     StdOut.println(b.toString());
        // }

        for (Board n : b.neighbors()) {
            StdOut.println(n.toString());
        }
        // StdOut.println(b.toString());

    }
}

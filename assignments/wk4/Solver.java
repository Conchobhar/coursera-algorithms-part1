import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode minNode;
    private final boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Argument can not be null.");
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
        SearchNode initialSearchNode = new SearchNode(initial, null, 0);
        SearchNode initialSearchNodeTwin = new SearchNode(initial.twin(), null, 0);
        minNode = initialSearchNode;
        SearchNode minNodeTwin = initialSearchNodeTwin;
        pq.insert(minNode);
        pqTwin.insert(minNodeTwin);
        // While the current minimum priority node is not in the goal state...
        while (!minNode.board.isGoal() && !minNodeTwin.board.isGoal()) {
            minNode = pq.delMin();
            minNodeTwin = pqTwin.delMin();
            Board prevBoard, prevBoardTwin;
            prevBoard = minNode.prev == null ? null : minNode.prev.board;
            prevBoardTwin = minNodeTwin.prev == null ? null : minNodeTwin.prev.board;
            // Get the next min priority node and add its neighbours to the queue
            // so long as the neighbour is not going back to the previous state.
            for (Board b : minNode.board.neighbors()) {
                if (!b.equals(prevBoard)) pq.insert(new SearchNode(b, minNode, minNode.moves + 1));
            }
            for (Board b : minNodeTwin.board.neighbors()) {
                if (!b.equals(prevBoardTwin))
                    pqTwin.insert(new SearchNode(b, minNodeTwin, minNodeTwin.moves + 1));
            }
        }
        this.solvable = minNode.board.isGoal();
    }

    private class SearchNode implements Comparable<SearchNode> {
        public final Board board;
        public final SearchNode prev;
        public final int moves;
        public final int manhattan;

        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.manhattan + this.moves,
                                   that.manhattan + that.moves);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return minNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> boards = new Stack<>();
        SearchNode node = minNode;
        while (node != null) {
            boards.push(node.board);
            node = node.prev;
        }
        return boards;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In("puzzle14.txt");
        int dim = in.readInt();
        int[][] tiles = new int[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                tiles[r][c] = in.readInt();
            }
        }

        Board initialBoard = new Board(tiles);
        Solver s = new Solver(initialBoard);
        StdOut.println(s.isSolvable());
        StdOut.println(s.moves());
        if (s.isSolvable()) {
            for (Board b : s.solution()) {
                StdOut.println(b.toString());
            }
        }
    }
}

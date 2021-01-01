package wk1.assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double resultsStdDev, resultsMean;
    private int numTrials;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Error: Dimension 'n' and trials must both be > 0");
        }
        int openedSites;
        numTrials = trials;
        double[] results = new double[trials];

        for (int i = 0; i < numTrials; i++) {
            Percolation perc = new Percolation(n);
            openedSites = 0;
            // Run n trials and store fraction required to percolate
            while (!perc.percolates()) {
                while (true) {
                    int row = StdRandom.uniform(1, n + 1);
                    int col = StdRandom.uniform(1, n + 1);
                    if (!perc.isOpen(row, col)) {
                        perc.open(row, col);
                        openedSites++;
                        break;
                    }
                }
            }
            results[i] = (double) openedSites / (double) (n*n);
        }
        resultsMean = StdStats.mean(results);
        resultsStdDev = StdStats.stddev(results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return resultsMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return resultsStdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (resultsMean - (CONFIDENCE_95 * resultsStdDev / Math.sqrt(numTrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (resultsMean + (CONFIDENCE_95 * resultsStdDev / Math.sqrt(numTrials)));
    }

    // test client (see below)
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("CLI requires two input arguments, n and t.");
        }
        int n, t;

        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);
        String confidenceInterval = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidenceInterval);
    }

}

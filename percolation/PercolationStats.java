import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Integer n;
    private Integer trials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw  new IllegalArgumentException("not allowed N < 1 or trials < 1");
        }

        this.n = n;
        this.trials = trials;
        this.thresholds = new double[trials];

        double nSites = n * n;

        for (int s = 0; s < trials; s++) {
            Percolation percolation = new Percolation(n);
            int opened = 0;

            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);

                if (!percolation.isOpen(i, j)) {
                    opened +=1;
                }
                // open random site (i, j)
                percolation.open(i, j);
            }

            this.thresholds[s] = opened / nSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return  mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        // check for required command line arguments
        if (args.length < 2) {
            throw new ArrayIndexOutOfBoundsException("Provide N & T");
        }

        // set N & T
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        // create percolationStats object
        PercolationStats ps = new PercolationStats(N, T);

        // compute quantities to report
        double mean = ps.mean();
        double stddev = ps.stddev();
        double c95m = ps.confidenceLo(); // mean plus conf95
        double c95p = ps.confidenceHi(); // mean minus conf95

        // print the results
        StdOut.println("mean                    = "+mean);
        StdOut.println("stddev                  = "+stddev);
        StdOut.println("95% confidence interval = "+c95m+", "+c95p);
    }

}
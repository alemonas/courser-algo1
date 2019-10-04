public class PercolationStats {

    private Integer n;
    private Integer trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return 10.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 10.0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 10.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 10.0;
    }

    // test client (see below)
    public static void main(String[] args) {
        System.out.println("Percolation stats");
    }

}
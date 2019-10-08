import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 * Corner cases
 *  By convention, the row and column indices are integers between 1 and n,
 *  where (1, 1) is the upper-left site: Throw an IllegalArgumentException if any argument
 *  to open(), isOpen(), or isFull() is outside its prescribed range.
 *  Throw an IllegalArgumentException in the constructor if n ≤ 0.
 ******************************************************************************/

public class Percolation {

    private int matrix[][];
    public WeightedQuickUnionUF quickUnionUF;
    private int countOpenSites;
    private int size;
    private int quSize;
    private int openSites[];

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        matrix = new int[n][n];
        countOpenSites = 0;
        quSize = n*n;
        quickUnionUF = new WeightedQuickUnionUF(quSize+2);
        openSites = new int[quSize+2];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndexFromMatrixToArray(row, col);
        if (!isOpen(row, col)) {
            countOpenSites++;
            openSites[index] = 1;
            unionWithAdjacent(row, col);
        }
    }

    // Return the related index in a flat array
    // used by the Weighted Quick Union
    private int getIndexFromMatrixToArray(int row, int col) {
        return ((row - 1) * size) + col;
    }

    private void unionWithAdjacent(int i, int j) {
        int top = i - 1;
        int bottom = i + 1;
        int left = j - 1;
        int right = j + 1;

        int index = getIndexFromMatrixToArray(i, j);

        if ( top <= 0 ) {
            quickUnionUF.union(0, index);
        }

        if ( bottom > size ) {
            quickUnionUF.union((quSize+1), index);
        }

        if (top > 0 && bottom <= size) {
            attemptUnion(top, j, index);
            attemptUnion(bottom, j, index);
        }

        if (top > 0 && bottom <= (size + 1)) {
            attemptUnion(top, j, index);
        }

        if (left > 0 && right <= size) {
            attemptUnion(i, right, index);
        }

        if (left > 0 && right <= (size+1)) {
            attemptUnion(i, left, index);

        }
    }

    private void attemptUnion(int i, int j, int index) {
        if (isOpen(i, j)) {
            quickUnionUF.union(getIndexFromMatrixToArray(i, j), index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        int index = getIndexFromMatrixToArray(row, col);
        return openSites[index] == 1;
    }

    private void checkBounds(int i, int j) {
        if (i <= 0 || i > quSize) {
            throw new java.lang.IllegalArgumentException("row index i out of bounds");
        }
        if (j <= 0 || j > quSize) {
            throw new java.lang.IllegalArgumentException("row index i out of bounds");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = getIndexFromMatrixToArray(row, col);
        return  quickUnionUF.connected(0, pos);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, (quSize + 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 1);
        percolation.open(3, 2);
        percolation.open(3, 3);
        boolean isPercolate = percolation.percolates();

        System.out.println(isPercolate);
    }
}
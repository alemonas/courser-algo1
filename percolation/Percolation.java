import java.lang.reflect.Array;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
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

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if ( n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        matrix = new int[n][n];
        countOpenSites = 0;
        quSize = n*n+2;
        quickUnionUF = new WeightedQuickUnionUF(quSize);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }

        System.out.println(matrix);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            countOpenSites++;
            matrix[row][col] = 1;
            unionWithAdjacents(row, col);
        }
    }

    // Return the related index in a flat array
    // used by the Weighted Quick Union
    private int getIndexFromMatrixToArray(int row, int col) {
        return (row * size) + (col + 1);
    }

    //
    private void unionWithAdjacents(int row, int col) {
        int topX = row - 1;
        int bottomX = row + 1;
        int leftY = col - 1;
        int rightY = col + 1;

        int pos = getIndexFromMatrixToArray(row, col);

        if ( topX < 0 ) {
            quickUnionUF.union(0, pos);
        }

        if ( bottomX >= size) {
            quickUnionUF.union((quSize - 1), pos);
        }

        if ( row > 0 && isOpen(topX, col)) {
            int topPos = getIndexFromMatrixToArray(topX, col);
            quickUnionUF.union(pos, topPos);
        }

        if ( row < (size - 1)) {
            if (isOpen(bottomX, col)) {
                int bottomPos = getIndexFromMatrixToArray(bottomX, col);
                quickUnionUF.union(pos, bottomPos);
            }
        }

        if ( col > 0 && isOpen(row, leftY)) {
            int leftPos = getIndexFromMatrixToArray(row, leftY);
            quickUnionUF.union(pos, leftPos);
        }

        if ( col < (size-1) && isOpen(row, rightY)) {
            int rightPos = getIndexFromMatrixToArray(row, rightY);
            quickUnionUF.union(pos, rightPos);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (matrix[row][col] == 1)
            return true;
        return false;
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
        return quickUnionUF.connected(0, (quSize - 1));
    }

    public void printMatrix() {
        for (int i = 0 ; i < size ; i++) {
            for (int j = 0 ; j < size ; j++) {
                if (this.matrix[i][j] == 1) {
                    System.out.print((char)27 + "[32m" + this.matrix[i][j] + " ");
                } else {
                    System.out.print((char)27 + "[37m" + this.matrix[i][j] + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("-");
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        percolation.open(0, 0);
        percolation.open(0, 1);
        percolation.open(1, 1);
        percolation.open(2, 0);
        boolean full = percolation.isFull(2,0);
        System.out.println(full);
        percolation.printMatrix();
        boolean isPercolate = percolation.percolates();

        System.out.println(isPercolate);
    }
}
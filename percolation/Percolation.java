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
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                matrix[i][j] = 0;
//            }
//        }
//
//        System.out.println(matrix);
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
//        return (row * size) + (col + 1);
//        return ((row*quSize - quSize) + col) - 1;
    }

    //
    private void unionWithAdjacent(int i, int j) {
        int topX = i - 1;
        int bottomX = i + 1;

        int index = getIndexFromMatrixToArray(i, j);


        if ( topX <= 0 ) {
            quickUnionUF.union(0, index);
        }

        if ( bottomX > size ) {
            quickUnionUF.union((quSize+1), index);
        }
//        if (j < size) attemptUnion(i, j+1, index);
//        if (j > 1) attemptUnion(i, j-1, index);
//
//        if (i < size) {
//            attemptUnion(i+1, j, index);
//        } else {
//            quickUnionUF.union(index, size+1);
//        }
//        if (i > 1) {
//            attemptUnion(i-1, j, index);
//        } else {
//            quickUnionUF.union(index, size);
//        }
//        int topX = row - 1;
//        int bottomX = row + 1;
//        int leftY = col - 1;
//        int rightY = col + 1;
//
//        int index = getIndexFromMatrixToArray(row, col);
//
//        if ( topX <= 0 ) {
//            quickUnionUF.union(0, index);
//        } else {
//            if (isOpen(topX, col)) {
//                int topIndex = getIndexFromMatrixToArray(topX, col);
//                quickUnionUF.union(index, topIndex);
//            }
//
//            if (isOpen(row, leftY)) {
//                int leftIndex = getIndexFromMatrixToArray(row, leftY);
//                quickUnionUF.union(index, leftIndex);
//            }
//
//            if (isOpen(row, rightY)) {
//                int rightIndex = getIndexFromMatrixToArray(row, rightY);
//                quickUnionUF.union(index, rightIndex);
//            }
//
//        }
//
//        if ( bottomX > size) {
//            quickUnionUF.union((quSize + 1), index);
//        } else {
//            if (isOpen(bottomX, col)) {
//                int bottomIndex = getIndexFromMatrixToArray(bottomX, col);
//                quickUnionUF.union(index, bottomIndex);
//            }
//        }
    }

    private void attemptUnion(int i, int j, int index) {
        if (isOpen(i, j)) {
            quickUnionUF.union(getIndexFromMatrixToArray(i, j), index);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
//        row--;
//        col--;
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
//        return quickUnionUF.connected(size, size+1);
        return quickUnionUF.connected(0, (quSize + 1));
    }

//    public void printMatrix() {
//        for (int i = 0 ; i < size ; i++) {
//            for (int j = 0 ; j < size ; j++) {
//                if (this.matrix[i][j] == 1) {
//                    System.out.print((char)27 + "[32m" + this.matrix[i][j] + " ");
//                } else {
//                    System.out.print((char)27 + "[37m" + this.matrix[i][j] + " ");
//                }
//            }
//            System.out.println("");
//        }
//        System.out.println("-");
//    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(3, 1);
        percolation.open(3, 3);
//        boolean full = percolation.isFull(2,0);
//        System.out.println(full);
//        percolation.printMatrix();
        boolean isPercolate = percolation.percolates();

        System.out.println(isPercolate);
    }
}
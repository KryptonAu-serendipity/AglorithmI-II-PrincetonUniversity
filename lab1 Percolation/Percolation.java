import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public  class Percolation {
    private static int[][] dirs = {{-1, 0},{1, 0},{0, 1},{0,-1}};
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[]sites;
    private  int gridsize;
    private  int opensites; 

    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException("n must be lager than 0");
        gridsize = n;
        opensites = 0;
        sites = new boolean[n*n+2];
        uf1 = new WeightedQuickUnionUF(n*n+1);
        uf2 = new WeightedQuickUnionUF(n*n+2);
        sites[0] = true;
        sites[n*n+1] = true;
        opensites = 0;
    }
    private void validate(int p, String desc) {
        if (p < 1 || p > gridsize)
            throw new IllegalArgumentException(desc + " index i out of bounds");

    }

    private boolean isInGrid(int row, int col) {
        return row >= 1 && row <= gridsize && col >= 1 && col <= gridsize;
    }
    
    private int getsites(int row, int col) {
        if (row == 0) return 0;
        if (row == gridsize + 1) col = 1;
        return (row - 1) * gridsize + col;
    }

    public  void open(int row, int col) {
        validate(row, "row");
        validate(col, "col");
        if(!isOpen(row,col)) {
            opensites++;
            sites[getsites(row,col)] = true;
            //遍历四周，判断是否存在已经open的节点或者虚拟节点
            int length = dirs.length;
            for(int i = 0; i<length;i++) {
                int neighborRow = row + dirs[i][0];
                int neighborCol = col + dirs[i][1];
                if(neighborRow == gridsize +1){
                    uf2.union(getsites(neighborRow, neighborCol),getsites(row, col));
                }
                if(neighborRow==0||isInGrid(neighborRow, neighborCol) && isOpen(neighborRow, neighborCol)) {
                    uf2.union(getsites(neighborRow, neighborCol),getsites(row, col));
                    uf1.union(getsites(neighborRow, neighborCol),getsites(row, col));     
                }


            }
        }
    }
    public boolean isOpen(int row,int col) {
        validate(col, "col");
        validate(row, "row");
        return sites[getsites(row, col)];
    }
    //利用uf1判断是否full
    public boolean isFull(int row,int col) {
        validate(col, "col");
        validate(row, "row");
        return isOpen(row, col) &&  (uf1.find(getsites(row, col)) == uf1.find(0)); 
    }
    //利用uf2判断是否能够完全联通
    public boolean percolates() {
        return uf2.find(gridsize * gridsize + 1) == uf2.find(0);
    }

    public int numberOfOpenSites() {
        return opensites;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        while (!p.percolates()) {//只要还没有渗透就一直迭代
            for (int row = 1; row <= n; row++)
                for (int col = 1; col <= n; col++) {
                    if (!p.isOpen(row, col))
                        if (StdRandom.bernoulli(1.0 / (n * n - p.numberOfOpenSites())))
                            p.open(row, col);
                }
        }
        StdOut.println("Percolation threshold is " + p.numberOfOpenSites() / Math.pow(n, 2));
    }

}
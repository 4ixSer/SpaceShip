/**
 * Created by qny4i on 11.01.2017.
 */
public class Canvas {

    private int width;
    private int height;
    private char[][]matrix ;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new char[height+2][width+2];           //хз
    }

    public void setPoint(double x, double y, char c){
        int newX=(int) Math.round(x);
        int newY=(int) Math.round(y);

        if(newX<0||newX>=matrix[0].length) return;
        if(newY<0||newY>=matrix.length) return;

        matrix[newY][newX]=c;

    }

    public void drawMatrix(double x, double y, int[][] matrix, char c){

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j]==1)
                    setPoint(x+j,y+i,c);
            }
        }
    }

    public void clear(){
        this.matrix = new char[height+2][width+2];
    }

    public void print(){
        System.out.println();
        for (int i = 0; i < matrix.length; i++)  {

            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(" "+ matrix[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }
}

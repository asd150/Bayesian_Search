public class Cell {

    private double priorBelief;
    private char terrian;
    private int row;
    private int column;
    private boolean isTarget;
    private double falseNegative;

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }

    public double getFalseNegative() {
        return falseNegative;
    }

    public void setFalseNegative(double falseNegative) {
        this.falseNegative = falseNegative;
    }

    public Cell(){
        this.priorBelief = 0;
        this.terrian = '0';
        this.isTarget = false;
        this.falseNegative =0;


    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }



    public double getPriorBelief() {
        return priorBelief;
    }

    public void setPriorBelief(double priorBelief) {
        this.priorBelief = priorBelief;
    }

    public char getTerrian() {
        return terrian;
    }

    public void setTerrian(char terrian) {
        this.terrian = terrian;
    }
}

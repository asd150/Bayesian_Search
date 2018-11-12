public class Cell {

    private double priorBelief;
    private char terrian;
    private int row;
    private int column;
    private boolean isTarget;
    private double falseNegative;
    private int timeSearched;
    private double pFindingTarget;


    public double getpFindingTarget() {
        return pFindingTarget;
    }

    public void setpFindingTarget(double pFindingTarget) {
        this.pFindingTarget = pFindingTarget;
    }

    public Cell(){
        this.priorBelief = 0;
        this.terrian = '0';
        this.isTarget = false;
        this.falseNegative =0;
        this.timeSearched = 0;
        this.pFindingTarget = 0;


    }


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

    public int getTimeSearched() {
        return timeSearched;
    }

    public void setTimeSearched(int timeSearched) {
        this.timeSearched = timeSearched;
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

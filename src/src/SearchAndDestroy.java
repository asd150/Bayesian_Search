import java.util.*;

/*
 * 'F' = FLAT
 * 'O' = Forested
 * 'H' = HILLY
 * 'C' = Cave*/

/* p(Target in Cell i | Observations) = (p(Observations = Ttype ^ Target Not Found | Target in the cell) * p(Target in the Cell))
                                            __________________________________________________________________

                                                            (Sum of all probability of Cells)
 *
 * */

/* Question 4
 * For Neighbor Cells Searc
 *  1. Start with the random Cell
 *  2. Check all neighbors
 *      Select the NCEll that has highest probability
 *      If  all NCells have same probability, selection is based on terrain
 *      if all have same probability and terrain, our best option is to select the cell at random from all four neighbor**/
public class SearchAndDestroy {
    private static Cell[][] map = new Cell[5][5];
     static int row = map.length;
     static int column = map[0].length;
     static int rule;
    static boolean bool = false;
    private static int targetRow;
    private static int targetCol;
    private static Cell target;
    private static Queue<Cell> ruleTwoQ =  ruleTwoQ = new LinkedList<>();;

    public static void main(String[] args)
    {
        //populate the map +  terrain distribution + set random target
        populateMap();
        //assign false Negative according to type of terrian
        falseNegative();

//        Scanner sn = new Scanner(System.in);
//        int option = sn.nextInt();


            //use rule 1
          //  ruleOne();
       // printCells();

        ruleTwo();
        ruleOne();
    //printCells();

       //implement Rule 1 and Rule 2, "how to select the cell"


    }
    private static void ruleOne(){
        //rule one says
        // At any time, search the cell with the highest probability of containing the target
        // highest belief cell
        boolean targetHit  = false;
        int steps =0;
        Cell CellToSearch = null;
        while(!targetHit) {
            CellToSearch = addCells();
          targetHit =   findTarget(CellToSearch.getRow(), CellToSearch.getColumn());
          steps++;
        }
//        int Y =0;
//        for(int i=0;i<row;i++){
//            for(int j=0;j<column;j++){
//                if(CellToSearch.getPriorBelief() >= map[i][j].getPriorBelief()){
//                    Y++;
//                }
//            }
//        }
//        System.out.println("Y " + Y);
       System.out.println("Target Found " + steps + "  " +CellToSearch.getPriorBelief() + "  "+CellToSearch.getTimeSearched());
        System.out.println(CellToSearch.getRow() + " " + CellToSearch.getColumn());
        System.out.println(targetRow + " " + targetCol);
    }

    private static void ruleTwo(){
        int steps =0;
        boolean target = false;
        Cell cell = null;
        while(!target){
            steps ++;
            Cell T = addCellRule2();
            target = findTarget(T.getRow(),T.getColumn());
            cell = T;
        }
        System.out.println(cell.getRow() + " " + cell.getColumn());
        System.out.println(targetRow + " " + targetCol + " " + steps);
    }


    //Rule 1: At any time, search the cell with the highest probability of containing the target.
    private static Cell addCells(){
        Cell HighestCell = null;
        double HighestBelief = Double.MIN_VALUE;
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(HighestBelief <  map[i][j].getPriorBelief()){
                    HighestBelief = map[i][j].getPriorBelief();
                    HighestCell = map[i][j];
                }
            }
        }
        return HighestCell;
    }

    //Rule 2: At any time, search the cell with the highest probability of finding the target
    private static Cell addCellRule2(){

     double selectCell =0;
     Cell cell = null;

     for(int i=0;i<row;i++){
         for(int j=0;j<column;j++){

             double temp = map[i][j].getPriorBelief() * map[i][j].getpFindingTarget();
             if(selectCell < temp){
                 selectCell = temp;
                 cell = map[i][j];
             }
         }
     }
return cell;
    }
    private static void falseNegative() {
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(map[i][j].getTerrian() == 'F'){
                    map[i][j].setFalseNegative(0.1);
                    map[i][j].setpFindingTarget(0.9);

                }
                else if(map[i][j].getTerrian() == 'H'){
                    map[i][j].setFalseNegative(0.3);
                    map[i][j].setpFindingTarget(0.7);
                }
                else if(map[i][j].getTerrian() == 'O'){
                    map[i][j].setFalseNegative(0.7);
                    map[i][j].setpFindingTarget(0.3);
                }
                else{ // 'C' = cave
                    map[i][j].setFalseNegative(0.9);
                    map[i][j].setpFindingTarget(0.1);
                }
            }
        }
    }

    private static void populateMap(){
        double prior;
        double product = row * column;
        prior = (1)/(product);
       // System.out.print(prior);
        for(int i=0;i<row;i++){
         for(int j=0;j<column;j++){
             map[i][j] = new Cell();
             map[i][j].setPriorBelief(prior);
             map[i][j].setRow(i);
             map[i][j].setColumn(j);
             }
        }
        terrainAssign();
    }

   private static void terrainAssign(){
        Random rand = new Random();

        Queue<Cell> queue = new LinkedList<>();
        int counter = 0;
       while(counter < row*column){

            if(!queue.isEmpty()){
                //assign flat
                double randNum = rand.nextDouble();
                if(randNum<0.25){
                    //assign flat
                    Cell cell = queue.poll();
                    cell.setTerrian('F');

                }
                else if(randNum> 0.25 && randNum < 0.5){
                    //assign hilly
                    Cell cell = queue.poll();
                    cell.setTerrian('H');
                }
                else if(randNum > 0.5 && randNum< 0.75){
                    //forested
                    Cell cell = queue.poll();
                    cell.setTerrian('O');

                }
                else{
                    //assign cave
                    Cell cell = queue.poll();
                    cell.setTerrian('C');
                }
                counter++;

            }
            else{
                int randRow = rand.nextInt(row);
                int randCol = rand.nextInt(column);
                 if(map[randRow][randCol].getTerrian()=='0'){
                     queue.add(map[randRow][randCol]);

                }

            }
        }
       int randRow = rand.nextInt(row);
       int randCol = rand.nextInt(column);
       targetRow = randRow;
       targetCol = randCol;
       map[randRow][randCol].setTarget(true);
       target = map[randRow][randCol];

    }

    private static void printCells(){
        for(int i=0;i<row ; i++){
            for(int j=0;j<column; j++){

                System.out.print(map[i][j].getPriorBelief() + "  ");
            }
            System.out.println();
            System.out.println();
        };

    }

    private static boolean findTarget(int row,int col){
        int X = map[row][col].getTimeSearched();
        X = X+1;
        map[row][col].setTimeSearched(X);
        Random rand = new Random();
        if(map[row][col].isTarget() && map[row][col].getPriorBelief() > Math.random()){
           return true;
        }


       double priorBelif =  map[row][col].getPriorBelief() * map[row][col].getFalseNegative();

        map[row][col].setPriorBelief(priorBelif);
        //System.out.println(priorBelif);
        normalize();


        return false;
    }

    private static void normalize(){

        double b = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){

                b = b + map[i][j].getPriorBelief();
            }
        }

        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                double tempB = map[i][j].getPriorBelief()/b;
                map[i][j].setPriorBelief(tempB);

            }
        }
    }

}

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/***
 * 'F' = FLAT
 * 'O' = Forested
 * 'H' = HILLY
 * 'C' = Cave*/

public class SearchAndDestroy {

    private static Cell[][] map = new Cell[50][50];
     static int row = map.length;
     static int column = map[0].length;
    private static int targetRow;
    private static int targetCol;
    private static Cell target;


    public static void main(String[] args)
    {
        //populate the map +  terrain distribution + set random target
        populateMap();
        //assign false Negative according to type of terrian
        falseNegative();


        //two rules for picking the next cell
        //ask from user which one to choose

       boolean isDestroyd = false;
       Random rand = new Random();
        int steps=0;
        Cell target = null;
       while(!isDestroyd){
           int randRow = rand.nextInt(row);
           int randCol = rand.nextInt(column);
           isDestroyd = findTarget(randRow,randCol);
           if(isDestroyd){
               target = map[randRow][randCol];
           }
           steps++;


       }
    printCells();

       //implement Rule 1 and Rule 2, "how to select the cell"

    System.out.println(steps + " " + target.getRow() + " " + target.getColumn() + " " + target.getPriorBelief());
    System.out.println(targetRow + " " + targetCol);


    }

    private static void falseNegative() {
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(map[i][j].getTerrian() == 'F'){
                    map[i][j].setFalseNegative(0.1);

                }
                else if(map[i][j].getTerrian() == 'H'){
                    map[i][j].setFalseNegative(0.3);
                }
                else if(map[i][j].getTerrian() == 'O'){
                    map[i][j].setFalseNegative(0.7);
                }
                else{ // 'C' = cave
                    map[i][j].setFalseNegative(0.9);
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

        if(map[row][col].isTarget()){
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

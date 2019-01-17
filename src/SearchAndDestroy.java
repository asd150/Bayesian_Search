import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class SearchAndDestroy {
    static double iterations = 0;
    static int steps =0;
	static class Type1Type2 {
		Cell locOne;
		Cell locTwo;

		public Type1Type2(Cell locOne, Cell locTwo) {
			this.locOne = locOne;
			this.locTwo = locTwo;
		}
	}

	static Cell[][] map;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 1 for Part 1 or 2 for Part 2: ");
		int part = Integer.parseInt(sc.nextLine());
		System.out.println("Enter 1 for Rule 1 or 2 for Rule 2: ");
		int rule = Integer.parseInt(sc.nextLine());
		System.out.println("This program can apply a specific rule for a specific part of the project once or 100 times for each terrain.");
		System.out.println("Enter 1 for only one iteration, and 100 for hundred iterations for each target (use this for averages) or Enter 4 for question 4: ");
		int oneOrHundred = Integer.parseInt(sc.nextLine());
		
		System.out.printf("Applying Part %d with Rule %d %d time(s)\n", part, rule, oneOrHundred);
		if(part == 1) {	
			if(rule == 1) {	
				if(oneOrHundred == 1) {
					initializeMap(null);
					partOneApplyRule(1, false);
				} else if(oneOrHundred == 100){
					// apply part1 rule1 100 times
					hundredTimesEach(1, 1);
				}
				else if(oneOrHundred == 4){
				    //rule 1 question 4 part 1
                    initializeMap(null);
                    questionfour(1);
                    System.out.println("Total Steps : " + steps);
                }
			} else if(rule == 2) {
				if(oneOrHundred == 1) {	
					// apply part1 rule2 once
					initializeMap(null);
					partOneApplyRule(2, false);
				} else if(oneOrHundred == 100){
					// apply part1 rule2 100 times
					hundredTimesEach(1, 2);
				}
				else if(oneOrHundred == 4){
                    initializeMap(null);
				    questionfour(2);
                    System.out.println("Total Steps : " + steps);
                }
			}

		} else {	
			if(rule == 1) {	
				if(oneOrHundred == 1) {	
					// apply part2 rule1 once
					initializeMap(null);
					partTwoApplyRule(1, false);
				} else if(oneOrHundred ==100) {
					// apply part2 rule1 100 times
					hundredTimesEach(2, 1);
				}
				else if(oneOrHundred == 4){
                    initializeMap(null);
				    questionfour(1);
                    System.out.println("Total Steps : " + steps);
                }
			} else if(rule == 2){
				if(oneOrHundred == 1) {	
					// apply part2 rule2 once
					initializeMap(null);
					partTwoApplyRule(2, false);
				} else if(oneOrHundred == 100) {
					// apply part2 rule2 100 times
					hundredTimesEach(2, 2);
				}
				else if(oneOrHundred == 4){
                    initializeMap(null);
				    questionfour(2);
                    System.out.println("Total Steps : " + steps);
                }
			}

		}
//		initializeMap(null);
//		 partOneApplyRule(2, false);
//		 partTwoApplyRule(1, false);
//		partOne100TimesEach(part, rule);
	}

	public static void hundredTimesEach(int part, int rule) {
		int count = 0;
		map = new Cell[50][50];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell.Terrain t;
				double p = Math.random();
				if (p < 0.2) {
					t = Cell.Terrain.FLAT;
				} else if (p < 0.5) {
					t = Cell.Terrain.HILLY;
				} else if (p < 0.7) {
					t = Cell.Terrain.FORESTED;
				} else {
					t = Cell.Terrain.MAZE_OF_CAVES;
				}
				map[i][j] = new Cell(i, j, t);
			}
		}
		
		int flatCounter = 0, hillyCounter = 0, forestedCounter = 0, caveCounter = 0;
		long flatIteration = 0, hillyIteration = 0, forestedIteration = 0, caveIteration = 0;
		
		while(flatCounter < 100 || hillyCounter < 100 || forestedCounter < 100 || caveCounter < 100) {

			// reset maze
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					map[i][j].priorBelief = 0.0004;
					map[i][j].isTarget = false;
					map[i][j].numOfTimesExamined = 0;
				}
			}
			
			boolean repeat = true;
			int randomRow = -1;
			int randomCol = -1;
			
			while(repeat) {
				// generate random coordinate
				Random rand = new Random();
				randomRow = rand.nextInt(50);
				randomCol = rand.nextInt(50);
				
				// check if that terrain is checked already or not
				Cell.Terrain randomCellTerrain = map[randomRow][randomCol].terrain;
				switch(randomCellTerrain) {
					case FLAT:
						flatCounter++;
						break;
					case HILLY:
						hillyCounter++;
						break;
					case FORESTED:
						forestedCounter++;
						break;
					case MAZE_OF_CAVES:
						caveCounter++;
						break;
				}
				
				if(flatCounter > 100 || hillyCounter > 100 || forestedCounter > 100 || caveCounter > 100) {
					if(flatCounter > 100) flatCounter--;
					if(hillyCounter > 100) hillyCounter--;
					if(forestedCounter > 100) forestedCounter--;
					if(caveCounter > 100) caveCounter--;
					//redo everything again
				} else {
					repeat = false;
				}
			}
				
			map[randomRow][randomCol].assignTarget();


			switch(map[randomRow][randomCol].terrain) {
				case FLAT:
					flatIteration = flatIteration + (part == 1 ? partOneApplyRule(rule, true) : partTwoApplyRule(rule, true));
					break;
				case HILLY:
					hillyIteration = hillyIteration + (part == 1 ? partOneApplyRule(rule, true) : partTwoApplyRule(rule, true));
					break;
				case FORESTED:
					forestedIteration = forestedIteration + (part == 1 ? partOneApplyRule(rule, true) : partTwoApplyRule(rule, true));
					break;
				case MAZE_OF_CAVES:
					caveIteration = caveIteration + (part == 1 ? partOneApplyRule(rule, true) : partTwoApplyRule(rule, true));
					break;
			}
			
			
			// end of iteration
			map[randomRow][randomCol].isTarget = false;
			System.out.println("Completed " + ++count + " iterations.");
		}
		
		System.out.println("---------------------------");
		System.out.printf("Average number of iterations for Part %d using Rule %d:\n", part, rule );
		System.out.println("Flat: " + flatIteration/100);
		System.out.println("Hilly: " + hillyIteration/100);
		System.out.println("Forested: " + forestedIteration/100);
		System.out.println("Maze of Caves: " + caveIteration/100);
		
	}

	public static void initializeMap(Type1Type2 partTwoInitializingObj) {
		map = new Cell[50][50];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Cell.Terrain t;
				double p = Math.random();
				if (p < 0.2) {
					t = Cell.Terrain.FLAT;
				} else if (p < 0.5) {
					t = Cell.Terrain.HILLY;
				} else if (p < 0.7) {
					t = Cell.Terrain.FORESTED;
				} else {
					t = Cell.Terrain.MAZE_OF_CAVES;
				}
				map[i][j] = new Cell(i, j, t);
			}
		}

		// pick a random cell and make it a target
		Random rand = new Random();
		int targetRow = rand.nextInt(50);
		int targetCol = rand.nextInt(50);
		map[targetRow][targetCol].assignTarget();
		if (partTwoInitializingObj != null) {
			partTwoInitializingObj.locOne = map[targetRow][targetCol];
		}
	}

	// public static void RULE_ONE() {
	// Cell randomCell = selectCellForRule1();
	// int iteration = 0;
	// while(!cellIsATarget(randomCell)) {
	// iteration++;
	// randomCell = selectCellForRule1();
	// }
	// System.out.println("Number of iterations: " + iteration);
	// System.out.println(randomCell);
	// }

	public static int partOneApplyRule(int rule, boolean hundredTimes) {
		Cell randomCell = rule == 1 ? selectCellForRule1() : selectCellForRule2();
		int iteration = 0;
		while (!cellIsATarget(randomCell)) {
			iteration++;
			randomCell = rule == 1 ? selectCellForRule1() : selectCellForRule2();
		}
		
		if(!hundredTimes) {
			System.out.println("Number of iterations: " + iteration);
			System.out.println(randomCell);
		}
		return iteration;
	}

	public static Cell selectCellForRule1() {
		List<Cell> list = new ArrayList<Cell>();
		list.add(map[0][0]);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == 0 && j == 0)
					continue;
				if (map[i][j].priorBelief > list.get(0).priorBelief) {
					list.clear();
					list.add(map[i][j]);
				} else if (map[i][j].priorBelief == list.get(0).priorBelief) {
					list.add(map[i][j]);
				}
			}
		}

		Random rand = new Random();
		int indexOfRandomCell = rand.nextInt(list.size());
		return list.get(indexOfRandomCell);
	}

	public static Cell selectCellForRule2() {
		List<Cell> list = new ArrayList<Cell>();
		list.add(map[0][0]);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == 0 && j == 0)
					continue;
				Cell currentCell = map[i][j];
				if (currentCell.priorBelief * (1 - currentCell.falseNegative) > list.get(0).priorBelief
						* (1 - list.get(0).falseNegative)) {
					list.clear();
					list.add(map[i][j]);
				} else if (currentCell.priorBelief * (1 - currentCell.falseNegative) == list.get(0).priorBelief
						* (1 - list.get(0).falseNegative)) {
					list.add(map[i][j]);
				}
			}
		}

		Random rand = new Random();
		int indexOfRandomCell = rand.nextInt(list.size());
		return list.get(indexOfRandomCell);
	}

	public static boolean cellIsATarget(Cell c) {
		c.numOfTimesExamined++;

		// TODO: IS THE BIASED COIN A CONSTANT?????? CAN Math.random() WORK FOR
		// THE BIASED COIN?
		if (c.isTarget && c.falseNegative < Math.random())
			return true;
		c.priorBelief = c.falseNegative * c.priorBelief;
		normalize();
		return false;
	}

	public static void normalize() {
		double sumOfProbabilities = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				sumOfProbabilities += map[i][j].priorBelief;
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].priorBelief = map[i][j].priorBelief * (1 / sumOfProbabilities);
			}
		}
	}

	public static void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.println(map[i][j]);
			}
		}
	}

	public static List<Cell> getNeighbors(Cell cell) {
		List<Cell> list = new ArrayList<>();
		int r = cell.row;
		int c = cell.col;

		if (r < map.length - 1) {
			list.add(map[r + 1][c]);
		}
		if (r > 0) {
			list.add(map[r - 1][c]);
		}

		if (c < map.length - 1) {
			list.add(map[r][c + 1]);
		}
		if (c > 0) {
			list.add(map[r][c - 1]);
		}
		return list;
	}

	public static Cell selectHighCell(Cell C) {
		Cell temp = null;
		double flag = 0;
		List<Cell> list = getNeighbors(C);
		for (Cell cell : list) {
			if (cell.priorBelief > flag) {
				temp = cell;
				flag = cell.priorBelief;
			}
		}
		return temp;
	}

	public static void questionfour(int rule) {
		Cell nextCell = map[0][0];
		int i = 0;
		boolean X = false;
		while (!X) {
			// System.out.println(nextCell.priorBelief);
			Cell desiredCell = map[0][0];
			Cell maxNeighbor = map[0][0];
			double numSpacesAway = 0;

			// find the next cell to be searched
            if(rule ==1) {
                desiredCell =selectCellForRule1();
                steps++;
            }
            else{
                desiredCell = selectCellForRule2();
                steps++;// or Rules.ruleTwo(map); or
            }
												// Rules.ourRule(map);
			maxNeighbor = selectHighCell(nextCell);
			numSpacesAway = (Math.abs(desiredCell.col - nextCell.col) + Math.abs(desiredCell.row - nextCell.row))
					/ ((2 * map.length) - 2);

			if (numSpacesAway > Math.abs(desiredCell.priorBelief - maxNeighbor.priorBelief)) {
				nextCell = desiredCell;

			} else {
				nextCell = maxNeighbor;

			}
			i++;
            iterations = iterations+i;
			X = cellIsATarget(nextCell);

		}
		//System.out.println(i);
	}

	// public static void partOneApplyRule(int rule) {
	// Cell randomCell = rule == 1 ? selectCellForRule1() :
	// selectCellForRule2();
	// int iteration = 0;
	// while (!cellIsATarget(randomCell)) {
	// iteration++;
	// randomCell = rule == 1 ? selectCellForRule1() : selectCellForRule2();
	// }
	// System.out.println("Number of iterations: " + iteration);
	// System.out.println(randomCell);
	// }

	public static int partTwoApplyRule(int rule, boolean hundredTimes) {
		Type1Type2 currentMovement = new Type1Type2(null, null);
		initializeMap(currentMovement); // [Cell,null]
		// Random rand = new Random();
		// int randRow = rand.nextInt(50);
		// int randCol = rand.nextInt(50);
		Cell randomCell = rule == 1 ? selectCellForRule1() : selectCellForRule2();
		int iteration = 0;
		// if(!randomCell.isTarget || (randomCell.isTarget &&
		// randomCell.falseNegative >= Math.random())) {
		while (!randomCell.isTarget || (randomCell.isTarget && randomCell.falseNegative >= Math.random())) {
			// System.out.println("row: " + randomCell.row + "col: " +
			// randomCell.col + "p: " + randomCell.priorBelief);
			iteration++;
			currentMovement = moveTarget(currentMovement);
			double[][] mapProbabilitiesCopy = mapCopy();
			double sumOfZeroedOutCells = 0;
			int totalRemainingCells = 2500;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if ((map[i][j].terrain != currentMovement.locOne.terrain
							&& map[i][j].terrain != currentMovement.locTwo.terrain)
							|| aloneCell(map[i][j], currentMovement)) {
						sumOfZeroedOutCells += mapProbabilitiesCopy[i][j];
						mapProbabilitiesCopy[i][j] = 0;
						totalRemainingCells--;
					}
				}
			}
			double distributeProb = sumOfZeroedOutCells / totalRemainingCells;
			for (int i = 0; i < mapProbabilitiesCopy.length; i++) {
				for (int j = 0; j < mapProbabilitiesCopy[i].length; j++) {
					if (mapProbabilitiesCopy[i][j] != 0) {
						mapProbabilitiesCopy[i][j] += distributeProb;
					}
				}
			}

			for (int i = 0; i < mapProbabilitiesCopy.length; i++) {
				for (int j = 0; j < mapProbabilitiesCopy[i].length; j++) {
					if (mapProbabilitiesCopy[i][j] != 0) {
						// distribute the probability of
						// mapProbabilitiesCopy[i][j] to its neighbors evenly
						if (currentMovement.locOne.terrain == currentMovement.locTwo.terrain) {
							distributeEvenly(mapProbabilitiesCopy, i, j, currentMovement);
							normalizeMapProbabilitiesCopy(mapProbabilitiesCopy);
						} else if (map[i][j].terrain == currentMovement.locTwo.terrain) {
							continue;
						} else if (map[i][j].terrain == currentMovement.locOne.terrain) {
							distributeEvenly(mapProbabilitiesCopy, i, j, currentMovement);
							if (iteration != 1)
								mapProbabilitiesCopy[i][j] = 0;
							else
								normalizeMapProbabilitiesCopy(mapProbabilitiesCopy);
						} else {
							continue;
						}
					}
				}
			}

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j].priorBelief += mapProbabilitiesCopy[i][j];
				}
			}

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j].priorBelief *= 0.5;
				}
			}
			randomCell = rule == 1 ? selectCellForRule1() : selectCellForRule2();
		}

		if(!hundredTimes) {
			System.out.println("Number of iterations: " + iteration);
			System.out.println(randomCell);
		}
		return iteration;
	}

	public static Type1Type2 moveTarget(Type1Type2 originalMovement) {
		if (originalMovement.locTwo == null) { // need to move the initial cell
			// System.out.println(true);
			List<Cell> neighbors = getNeighbors(originalMovement.locOne);
			originalMovement.locOne.isTarget = false;
			Cell randomCell = neighbors.get((int) (Math.random() * neighbors.size()));
			randomCell.isTarget = true;
			return new Type1Type2(originalMovement.locOne, randomCell);
		} else {
			List<Cell> neighbors = getNeighbors(originalMovement.locTwo);
			originalMovement.locTwo.isTarget = false;
			Cell randomCell = neighbors.get((int) (Math.random() * neighbors.size()));
			randomCell.isTarget = true;
			return new Type1Type2(originalMovement.locTwo, randomCell);
		}
	}

	public static double[][] mapCopy() {
		double[][] duplicateMap = new double[50][50];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				duplicateMap[i][j] = map[i][j].priorBelief;
			}
		}
		return duplicateMap;
	}

	public static boolean aloneCell(Cell c1, Type1Type2 currentMovement) {
		// check if c1 has an adjacent cell that is of the first/second type of
		// currentMovement
		if (c1.terrain == currentMovement.locOne.terrain) {
			List<Cell> neighbors = getNeighbors(c1);
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locTwo.terrain)
					return false;
			}
			return true;
		} else {
			List<Cell> neighbors = getNeighbors(c1);
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locOne.terrain)
					return false;
			}
			return true;
		}
	}

	public static void distributeEvenly(double[][] mapProbabilitiesCopy, int i, int j, Type1Type2 currentMovement) {
		double probabilityToDistribute = mapProbabilitiesCopy[i][j];
		List<Cell> neighbors = getNeighbors(map[i][j]);
		int numOfNeighbors = 0;
		if (map[i][j].terrain == currentMovement.locOne.terrain) {
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locTwo.terrain)
					numOfNeighbors++;
			}
			if (numOfNeighbors == 0)
				return;
			double probabilityToAddBy = probabilityToDistribute / numOfNeighbors;
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locTwo.terrain) {
					mapProbabilitiesCopy[c.row][c.col] += probabilityToAddBy;
				}
			}
		} else {
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locOne.terrain)
					numOfNeighbors++;
			}
			if (numOfNeighbors == 0)
				return;
			double probabilityToAddBy = probabilityToDistribute / numOfNeighbors;
			for (Cell c : neighbors) {
				if (c.terrain == currentMovement.locOne.terrain) {
					mapProbabilitiesCopy[c.row][c.col] += probabilityToAddBy;
				}
			}
		}
	}

	public static void normalizeMapProbabilitiesCopy(double[][] mapProbabilitiesCopy) {
		double sum = 0;
		for (int i = 0; i < mapProbabilitiesCopy.length; i++) {
			for (int j = 0; j < mapProbabilitiesCopy[i].length; j++) {
				sum += mapProbabilitiesCopy[i][j];
			}
		}
		double alpha = 1 / sum;
		for (int i = 0; i < mapProbabilitiesCopy.length; i++) {
			for (int j = 0; j < mapProbabilitiesCopy[i].length; j++) {
				mapProbabilitiesCopy[i][j] *= alpha;
			}
		}
	}
}

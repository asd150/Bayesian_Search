
public class Cell {
	int row;
	int col;
	enum Terrain {FLAT, HILLY, FORESTED, MAZE_OF_CAVES};
	Terrain terrain;
	double falseNegative;
	double priorBelief;
	boolean isTarget;
	int numOfTimesExamined;
	
	public Cell(int row, int col, Terrain terrain) {
		this.row = row;
		this.col = col;
		this.terrain = terrain;
		assignFalseNegative(this.terrain);
		this.priorBelief = 0.0004;
		this.isTarget = false;
		this.numOfTimesExamined = 0;
	}
	
	public void assignFalseNegative(Terrain t) {
		if(t == Terrain.FLAT) {
			this.falseNegative = 0.1;
		} else if (t == Terrain.FORESTED) {
			this.falseNegative = 0.7;
		} else if (t == Terrain.HILLY){
			this.falseNegative = 0.3;
		} else {
			this.falseNegative = 0.9;
		}
	}
	
	public void assignTarget() {
		this.isTarget = true;
	}
	
	public String toString() {
		String t = "";
		if(this.terrain == Terrain.FLAT) t = "Flat";
		else if(this.terrain == Terrain.FORESTED) t = "Forested";
		else if(this.terrain == Terrain.HILLY) t = "Hilly";
		else if(this.terrain == Terrain.MAZE_OF_CAVES) t = "Maze of Caves";
		return "Cell (" + this.row + ", " + this.col +"): [" + "Terrain: " + t + ", "
				+ "Belief: " + this.priorBelief + ", Number of Times Examined: " + this.numOfTimesExamined + "]";
	}
}

package dk.sdu.sesem4.smartAIMovement;

public class State {
	final int x;
	final int y;
	final int goalX;
	final int goalY;
	
	public State(int x, int y, int goalX, int goalY) {
		this.x = x;
		this.y = y;
		this.goalX = goalX;
		this.goalY = goalY;
	}
	
	public boolean isGoal() {
		return x == goalX && y == goalY;
	}
	
	public boolean isPossible() {
		boolean[][] navGrid = SmartAIMovementController.navGrid;
		if (x < 0 || y < 0) {
			return false;
		}
		if (x >= 16 || y >= 11) {
			return false;
		}
		return navGrid[x][y];
	}
	
	public int heuristic() {
		return Math.abs(x-goalX) + Math.abs(y-goalY);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State)) {
			return false;
		}
		State other = (State)obj;
		return x == other.x && y == other.y && goalX == other.goalX && goalY == other.goalY;
	}
}

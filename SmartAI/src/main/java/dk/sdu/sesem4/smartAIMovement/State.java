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
		boolean result = navGrid[x][y];
//		System.out.printf("(%d, %d): %b\n", x, y, result);
		return result;
	}
	
	public int heuristic() {
		return Math.abs(x-goalX) + Math.abs(y-goalY);
	}
}

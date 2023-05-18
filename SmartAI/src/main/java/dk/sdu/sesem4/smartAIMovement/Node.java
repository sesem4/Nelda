package dk.sdu.sesem4.smartAIMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node implements Comparable<Node> {
	State state;
	int cost;
	ArrayList<Node> path;
	int depth;
	
	public Node(State state, int cost) {
		this.state = state;
		this.cost = cost;
		path = new ArrayList<>();
		path.add(this);
		depth = 0;
	}
	
	public Node(State state, int cost, ArrayList<Node> path, int depth) {
		this.state = state;
		this.cost = cost;
		this.path = path;
		path.add(this);
		this.depth = depth;
	}
	
	public List<Node> expand() {
		List<Node> newNodes = new ArrayList<>();
		
		List<State> successorStates = successorFunction(this.state);
		for (State successorState : successorStates) {
			newNodes.add(new Node(successorState, this.cost + 1, (ArrayList<Node>) path.clone(), depth + 1));
		}
		
		return newNodes;
	}
	
	private List<State> successorFunction(State state) {
		List<State> successorStates = new ArrayList<>();
		State down  = new State(state.x,     state.y - 1,   state.goalX, state.goalY);
		State up    = new State(state.x,     state.y + 1,   state.goalX, state.goalY);
		State left  = new State(state.x - 1, state.y,       state.goalX, state.goalY);
		State right = new State(state.x + 1, state.y,       state.goalX, state.goalY);
		State standStill = state;
		
		successorStates.add(down);
		successorStates.add(up);
		successorStates.add(left);
		successorStates.add(right);
		successorStates.add(standStill);
		
		return successorStates.stream().filter(State::isPossible).collect(Collectors.toList());
	}
	
	public int evaluation() {
		return this.cost + this.state.heuristic();
	}
	
	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.evaluation(), o.evaluation());
	}
}

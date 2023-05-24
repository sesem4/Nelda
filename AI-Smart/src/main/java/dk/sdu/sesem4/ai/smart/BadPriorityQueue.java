package dk.sdu.sesem4.ai.smart;

import java.util.ArrayList;
import java.util.List;

public class BadPriorityQueue {
	List<Node> nodes;
	
	public BadPriorityQueue() {
		this.nodes = new ArrayList<>();
	}
	
	public void add(Node newNode) {
		for (Node node : nodes) {
			if (node.state == newNode.state) {
				if (node.cost > newNode.cost) {
					node.cost = newNode.cost;
				}
				return;
			}
		}
		nodes.add(newNode);
	}
	
	public void addAll(List<Node> nodes) {
		for (Node node : nodes) {
			this.add(node);
		}
	}
	
	public Node poll() {
		Node node = nodes.get(0);
		nodes.remove(node);
		return node;
	}
	
	
	public int size() {
		return nodes.size();
	}
}

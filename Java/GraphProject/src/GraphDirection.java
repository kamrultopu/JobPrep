import java.util.ArrayList;


public interface GraphDirection {
	final boolean directed = true;
	boolean isDirected();
	void updateEdges();
	ArrayList<Node> FindNodesWithNoIncomingEdge();
	boolean checkDag();
}

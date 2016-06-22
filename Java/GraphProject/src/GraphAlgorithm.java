import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;
/*

public class GraphAlgorithm {
	DirectedGraph gp;
	static int runningNode = 1;
	
	public GraphAlgorithm(int node, HashMap<Node, ArrayList<Edge>> adjList) {
		// TODO Auto-generated constructor stub
		gp = new DirectedGraph(node,adjList);
	}
	//////// DFS////////////////////////////////////////
	private ArrayList<Node> DFSTraverse(Node currentNode){
		currentNode.setVisited(true);
		currentNode.setFoundID(runningNode++);
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(currentNode);
		ArrayList<Node> temp = gp.getAdjacentNode(currentNode);
		//System.out.println(temp.toString());
		//System.out.println(currentNode);
		//System.out.println(temp);
		for( Node e: temp){
			if(!e.isVisited){
				path.addAll(DFSTraverse(e));
			}
		}
		currentNode.setFinishID(runningNode++);
		//System.out.println(currentNode + " " + runningNode);
		return path;
	}
	public void DFS(int source){
		//System.out.println("DFS from source : "  + source);
		Node currentNode = gp.getNode(source);
		//System.out.println(currentNode);
		ArrayList<Node> temp = DFSTraverse(currentNode);
		System.out.println(" source: " + source + " path" + temp.toString());
	}
	public void DFSforAllNode(){
		
		for( int i = 0 ; i < gp.getNoVertices(); i++){
			runningNode = 1;
			gp.resetAllNode();
			DFS(i);
		}
	}
	
	//////////////////////////////////////////////////
	///   Topological Search          ///////////////
	////////////////////////////////////////////////
	public ArrayList<Node> TopologicalSort(){
		LinkedList<Node> NodesWithNoIncomingEdge = new LinkedList<Node>();
		NodesWithNoIncomingEdge.addAll(gp.FindNodesWithNoIncomingEdge());
		while( !NodesWithNoIncomingEdge.isEmpty() ){
			Node CurrentNode = NodesWithNoIncomingEdge.pop();
			DFS(CurrentNode.getNodeId());
		}
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		for( Integer i:gp.nodes.keySet()){
			nodes.add(gp.nodes.get(i));
		}
		nodes.sort(null);
		Stack<Node> result = new Stack<Node>();
		for( Node nd: nodes){
			//System.out.println(nd);
			result.push(nd);
		}
		//System.out.println(result);
		nodes.clear();
		while(!result.empty()){
			nodes.add(result.pop());
		}
		return nodes;
	}
	
	
	///////////////////////////////////////////////
	////     BFS                    //////////////
	//////////////////////////////////////////////
	private LinkedHashSet<Node> BFSTraverse(Node currentNode){
		currentNode.setVisited(true);
		LinkedHashSet<Node> path = new LinkedHashSet<Node>();
		//path.add(currentNode);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(currentNode);
		
		while( !queue.isEmpty()){
			Node curNode = queue.pop();
			path.add(curNode);
			ArrayList<Node> temp = gp.getAdjacentNode(curNode);
			for( Node e: temp){
				if(!e.isVisited){
					e.setVisited(true);
					queue.push(e);
				}
			}
		}
		
		//System.out.println(currentNode + " " + runningNode);
		return path;
	}
	public void BFS(int source){
		Node currentNode = gp.getNode(source);
		System.out.println(currentNode);
		LinkedHashSet<Node> temp = BFSTraverse(currentNode);
		System.out.println(" source: " + source + " path" + temp.toString());
	}
	public void BFSforAllNode(){
		for( int i = 0 ; i < gp.getNoVertices(); i++){
			runningNode = 1;
			gp.resetAllNode();
			BFS(i);
		}
	}
	////////////////////////////////////////////////////////
}
*/
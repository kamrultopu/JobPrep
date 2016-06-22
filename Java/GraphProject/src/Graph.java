import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class Graph {
	int nodeCount;
	int edgeCount;
	TreeMap<Integer,Node> nodeList = new TreeMap<Integer,Node>();
	TreeMap<Integer,Edge> edgeList = new TreeMap<Integer,Edge>();
	HashMap<Node,ArrayList<Node>> adjacentList = new HashMap<Node,ArrayList<Node>>();
	boolean isDirected;
	public Graph(int[][] adjMatrix, boolean dirType){
		nodeCount = adjMatrix.length;
		isDirected = dirType;
		edgeCount = 0 ;
		for( int i = 0 ; i < nodeCount ; i++){
			addNode(i);
		}
		for( int i = 0 ; i < nodeCount; i++){
			for( int j = 0 ; j < nodeCount; j++){
				if( adjMatrix[i][j] != 0){
					int cost = adjMatrix[i][j];
					addEdge(getNodeById(i),getNodeById(j),cost);
				}
			}
		}
		
	}
	public Graph(int[][] adjList){
		nodeCount = adjList.length;
		isDirected = false;
		edgeCount = 0 ;
		for( int i = 0 ; i < nodeCount ; i++){
			addNode(i);
		}
		for( int i = 0 ; i < nodeCount; i++){
			for( int j = 0 ; j < adjList[i].length; j++){
				addEdge(getNodeById(i),getNodeById(adjList[i][j]),0);
				//addEdge(getNodeById(adjList[i][j]),getNodeById(i),0);
			}
		}
	}
	public void addNode(int id){
		nodeList.put(id,new Node(id));
	}
	public Node getNodeById(int id){
		Node retNode = null;
		if(nodeList.containsKey(id)){
			retNode = nodeList.get(id); 
		}
		return retNode;
	}
	
	public void addEdge(Node source, Node dest, int cost){
		edgeList.put(edgeCount,new Edge(source,dest,isDirected, cost));
		edgeCount++;
		if( adjacentList.containsKey(source)){
			adjacentList.get(source).add(dest);
		}
		else{
			ArrayList<Node> temp = new ArrayList<Node>();
			temp.add(dest);
			adjacentList.put(source,temp);
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Graph: [nodeCount = " + nodeCount + ", edgeCount = " + edgeCount + "]\n");
		sb.append("nodeList = " + nodeList.toString() + "\n");
		for(Node nd: adjacentList.keySet()){
			sb.append("adjacent Node for " + nd + ": " + adjacentList.get(nd));
			sb.append("\n");
		}
		return sb.toString();
	}
	
}

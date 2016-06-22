
class Edge{
	int	edgeID;
	Node source;
	Node dest;
	boolean isDirected;
	int linkCost;
	public static int totalEdge = 0;
	Edge(Node s, Node t, boolean type, int cost){
		edgeID =  totalEdge++;
		source = s;
		dest = t;
		isDirected = type;
		linkCost = cost;
	}
	public void updateEdge(Node s, Node t){
		source = s;
		dest = t;
	}
	@Override
	public String toString() {
		return "Edge [source=" + source + "--> dest=" + dest +", cost= " + linkCost + "]";
	}
}

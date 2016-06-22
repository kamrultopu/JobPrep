

public class Main {

	public static void main(String[] args) {
		int[][] adjList = {{1,2},{2},{0,3},{3}};
		int node = adjList.length;
		Graph gp = new Graph(adjList);
		System.out.println(gp.toString());
	}

}

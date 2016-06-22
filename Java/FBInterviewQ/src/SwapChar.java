import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

class Node{
	int id;
	boolean isVisited;
	Node(int n){
		id = n;
		isVisited = false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", isVisited=" + isVisited + "]";
	}
	
}
class Link{
	Node nd1;
	Node nd2;
	Link(Node v1, Node v2){
		nd1 = v1;
		nd2 = v2;
	}
}
class Graph{
	HashSet<Node> hNodeSet;
	HashSet<Link> hLinkSet;
	Graph(int n){
		hNodeSet = new HashSet<Node>();
		hLinkSet = new HashSet<Link>();
		for( int i = 0 ; i < n ; i++){
			hNodeSet.add(new Node(i));
		}
	}
	public void addLink(Node v1, Node v2){
		hLinkSet.add(new Link(v1,v2));
	}
	public Node getNodebyId(int id){
		for( Node nd: hNodeSet){
			if( nd.id == id ){
				return nd;
			}
		}
		return null;
	}
	public HashSet<Node> getAdjacentNodes(Node vertex){
		HashSet<Node> adjacent = new HashSet<Node>();
		for( Link ln:hLinkSet){
			if( ln.nd1 == vertex ){
				adjacent.add(ln.nd2);
			}
			if( ln.nd2 == vertex){
				adjacent.add(ln.nd1);
			}
		}
		return adjacent;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<List<Integer>> getConnectedList(){
		ArrayList<List<Integer>> resultList = new ArrayList<List<Integer>>();
		LinkedList<Node> lklist = new LinkedList<Node>();
		for( Node v: hNodeSet){
			if( !v.isVisited){
				lklist.add(v);
			}
			while( !lklist.isEmpty()){
				//System.out.println(lklist.toString());
				ArrayList<Integer> sortedSet = new ArrayList<Integer>();
				Node temp = lklist.poll();
				HashSet<Node> adjacent = getAdjacentNodes(temp);
				for( Node nd: adjacent){
					if( !lklist.contains(nd) && !nd.isVisited){
						lklist.add(nd);
					}
				}
				if( !temp.isVisited ){
					DFSUtil(temp,sortedSet);
				}
				if( sortedSet.size() > 0 )
					resultList.add(sortedSet );
			}
		}
		return resultList;
	}
	private void DFSUtil(Node nd, ArrayList<Integer> sortedSet) {
		sortedSet.add(nd.id);
		nd.isVisited = true;
		HashSet<Node> adjacent = getAdjacentNodes(nd);
		for( Node v: adjacent){
			if( !v.isVisited ){
				DFSUtil(v,sortedSet);
			}
		}
	}
}

public class SwapChar {
	TreeSet<String> hSet = new TreeSet<String>(Collections.reverseOrder());
	public String getMaxStringNaive(String str, List<List<Integer>> swapIndex){
		LinkedList<String> lkd = new LinkedList<String>();
		lkd.add(str);
		hSet.add(str);
		int size = swapIndex.size();
		while(!lkd.isEmpty()){
			//System.out.println(lkd.toString());
			String tStr = lkd.poll();
			for( int i = 0 ; i < size ; i++){
				String tempStr = swapChar(tStr,swapIndex.get(i));
				//System.out.println(swapIndex.get(i).toString() + " str: " + tempStr);
				if( !hSet.contains(tempStr)){
					hSet.add(tempStr);
					lkd.add(tempStr);
				}
			}
			//System.out.println("hset:" + hSet.toString());
			
		}
		return hSet.pollFirst();
	}
	public String swapChar( String str, List<Integer> swapIndex){
		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(swapIndex.get(0), str.charAt(swapIndex.get(1)));
		sb.setCharAt(swapIndex.get(1), str.charAt(swapIndex.get(0)));
		return sb.toString();
	}
	public String getMaxString(String str, List<List<Integer>> swapIndex){
		 int size = str.length();
		 Graph gp = new Graph(size);
		 int linkCount = swapIndex.size();
		 for( int i = 0 ; i < linkCount; i++){
			 gp.addLink(gp.getNodebyId(swapIndex.get(i).get(0)),gp.getNodebyId(swapIndex.get(i).get(1)));
		 }
		 //System.out.println(gp.hLinkSet);
		 ArrayList<List<Integer>> tempList = gp.getConnectedList();
		 //System.out.println(tempList.toString());
		 int count = tempList.size();
		 StringBuilder sb = new StringBuilder(str);
		 for( int i = 0 ; i < count ;i++){
			 ArrayList<Integer> temp = (ArrayList<Integer>) tempList.get(i);
			 Collections.sort(temp);
			 //System.out.println(temp.toString());
			 int chainlength = temp.size();
			 if( chainlength > 1 ){
				 char[] charArray = new char[chainlength];
				 for( int j = 0 ; j < chainlength;j++){
					 charArray[j] = str.charAt(temp.get(j));
				 }
				 Arrays.sort(charArray);
				 int k = chainlength - 1;
				 for( int j = 0 ; j < chainlength;j++,k--){
					 sb.setCharAt(temp.get(j), charArray[k]);
				 }
			 }
		 }
		 return sb.toString();
	}
}

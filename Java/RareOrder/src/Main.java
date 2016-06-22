import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;


class Node implements Comparable{
	char chVal;
	int foundTime;
	int finishTime;
	HashSet<Node> adjList;
	Node(char ch){
		chVal = ch;
		adjList = new HashSet<Node>();
		foundTime = -1;
		finishTime = -1;
	}
	public void AddAdjacentNode(Node nd){
		if( !adjList.contains(nd)){
			//System.out.println("hi");
			adjList.add(nd);
			//System.out.println(adjList.size() + adjList.toString());
		}
	}
	public HashSet<Node> getAdjacentList(){
		return adjList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chVal;
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
		if (chVal != other.chVal)
			return false;
		return true;
	}
	@Override
	public int compareTo(Object arg0) {
		return finishTime - ((Node)arg0).finishTime;
	}
	@Override
	public String toString() {
		//return "Node: " + chVal;
		//System.out.println(adjList.size());
		return "Node [chVal=" + chVal + ", foundTime=" + finishTime + "]";
				//+ ", finishTime=" + finishTime+ " adjacent:" + adjList.toString() +"]";
	}
	
}

class UtilClass{
	public static int findFirstMisMatch(String str1, String str2){
		int size1 = str1.length();
		int size2 = str2.length();
		int minSize = size1<size2?size1:size2;
		int mismatchPos = minSize;
		for( int i = 0 ; i < minSize;i++){
			if(str1.charAt(i) != str2.charAt(i)){
				mismatchPos = i;
				//System.out.println(str1.charAt(i) + " mismatch" + i);
				break;
			}
		}
		return mismatchPos;
	}
}

class Graph{
	int time = 0;
	TreeMap<Character,Node> nodeMap = new TreeMap<Character,Node>();
	HashSet<Node> NoInLinkNode = new HashSet<Node>();
	Graph(ArrayList<String> arrList){
		int size = arrList.size();
		String prevString = arrList.get(0);
		for( int i = 1 ; i < size;i++){
			String currentString = arrList.get(i);
			int pos = UtilClass.findFirstMisMatch(prevString, currentString);
			if( pos < prevString.length() && pos < currentString.length()){
				char chPrev = prevString.charAt(pos);
				char chcurr = currentString.charAt(pos);
				//System.out.println("pos: " + pos + " prev: " + prevString + " current: "+ currentString );
				//System.out.println("prev: " + chPrev + " chcurr: " + chcurr);
				Node temp1,temp2;
				if( nodeMap.containsKey(chPrev)){
					temp1 = nodeMap.get(chPrev);
				}
				else{
					temp1 = new Node(chPrev);
					nodeMap.put(chPrev, temp1);
					NoInLinkNode.add(temp1);
					//System.out.println(NoInLinkNode.size()+ " , " + NoInLinkNode.toString());
				}
				if( nodeMap.containsKey(chcurr)){
					temp2 = nodeMap.get(chcurr);
				}
				else{
					//System.out.println("hih");
					temp2 = new Node(chcurr);
					nodeMap.put(chcurr, temp2);
					NoInLinkNode.add(temp2);
					
					//System.out.println(NoInLinkNode.size()+ " , " + NoInLinkNode.toString());
				}
				temp2.AddAdjacentNode(temp1);
				//System.out.println(prevString + " " + currentString);
				//System.out.println(chPrev + " " +chcurr );
				//System.out.println(temp2);
				//System.out.println(temp1);
				//System.out.println("hi" + NoInLinkNode.toString() + " size: " + NoInLinkNode.size());
				if(NoInLinkNode.contains(temp1)){
					//System.out.println("hi" + NoInLinkNode.toString());
					NoInLinkNode.remove(temp1);
				}
			}
			prevString = currentString;
		}
		//System.out.println(nodeMap.toString());
		//System.out.println(NoInLinkNode.toString());
	}
	
	public void TopologicalString(Node targetNode){
		targetNode.foundTime = time++;
		//System.out.println(targetNode);
		HashSet<Node> adjacentNode = targetNode.getAdjacentList();
		for(Node nd: adjacentNode){
			if(nd.foundTime < 0){
				TopologicalString(nd);
			}
		}
		targetNode.finishTime = time++;
	}
}

class RareOrder{
	Graph gp;
	RareOrder(ArrayList<String> arrList){
		gp = new Graph(arrList);
	}
	public String getOrder(){
		int size = gp.NoInLinkNode.size();
		//System.out.println(gp.NoInLinkNode.toString());
		ArrayList<Node> nodeswithNoDependency = new ArrayList<Node>();
		for(Node nd: gp.NoInLinkNode){
			nodeswithNoDependency.add(nd);
		}
		Collections.sort((List<Node>) nodeswithNoDependency);
		for(Node nd: nodeswithNoDependency){
			gp.TopologicalString(nd);
		}
		ArrayList<Node> nodeList = new ArrayList<Node>();
		for( Character ch:gp.nodeMap.keySet()){
			nodeList.add(gp.nodeMap.get(ch));
		}
		Collections.sort(nodeList);
		StringBuffer sb = new StringBuffer();
		for(Node nd: nodeList){
			sb.append(nd.chVal);
		}
		return sb.toString();
	}
}

class Main {
	public static void main(String[] args){
		ArrayList<String> strList = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		while(true){
			String str = sc.nextLine();
			if(str.equals("#"))
				break;	
			strList.add(str);
			
		}
		RareOrder rOrder = new RareOrder(strList);
		System.out.println(rOrder.getOrder());
		//System.out.println(strList.toString());
		sc.close();
	}
}

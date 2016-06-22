package Problem;

import java.util.ArrayList;

class Node{
	int val;
	int xIndex;
	int yIndex;
}

public class MaxIncreasingPath {
	
	public int longestIncreasingPath(int[][] matrix) {
        int maxlen = -1;
        
        return maxlen;
    }
}
/*
public class Solution {
    class Node{
        int val;
        ArrayList<Node> adj = new ArrayList<Node>();
        int i;
        int j;
        Node(int x){
            val = x;
        }
        public void addAdjacentNode(Node nd){
            adj.add(nd);
        }
        public boolean isNode(int ix , int jy){
            return (ix==i) && (jy==j);
        }
    }
    ArrayList<Node> nodeList = new ArrayList<Node>();
    public int longestIncreasingPath(int[][] matrix) {
        int size = matrix.length;
        for(int i = 0 ; i < size;i++){
            for( int j = 0 ; j < matrix[i].length;j++){
                if( !IsNodeExist(i,j)){
                    Node temp = new Node();
                    nodeList.add(temp);
                }
                
            }
        }
    }
    public Node IsNodeExist(int ix, int jy){
        int size = nodeList.size();
        for( int i = 0 ; i < size;i++){
            if( nodeList.get(i).isNode(ix,jy) ){
                return nodeList.get(i);
            }
        }
        return null;
    }
}
*/
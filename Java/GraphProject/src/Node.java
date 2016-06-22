import java.util.ArrayList;


public class Node implements Comparable{
	private int nodeId;
	public int nodeVal;
	int foundID;
	int finishID;
	public String nodeName;
	public boolean isVisited;
 	Node(int id){
		this(id,0,"undefined");
	}
 	Node(int id,int val, String name){
		nodeId = id;
		foundID = -1;
		finishID = -1;
		nodeVal = val;
		nodeName = name;
	}
 	Node(int id,int val){
		this(id,val,"undefined");
	}
	@Override
	public String toString() {
		return "Node[nodeId="+nodeId+",nodeVal="+nodeVal+"]";
		//return nodeName;
		//return "Node [nodeId=" + nodeId + ", foundID=" + foundID
				//+ ", finishID=" + finishID + ", isVisited=" + isVisited + "]";
	}
	public int getNodeId(){
		return nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeVal() {
		return nodeVal;
	}
	public void setNodeVal(int nodeVal) {
		this.nodeVal = nodeVal;
	}
	public boolean isVisited() {
		return isVisited;
	}
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	public int getFoundID() {
		return foundID;
	}
	public void setFoundID(int foundID) {
		this.foundID = foundID;
	}
	public int getFinishID() {
		return finishID;
	}
	public void setFinishID(int finishID) {
		this.finishID = finishID;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + finishID;
		result = prime * result + foundID;
		result = prime * result + (isVisited ? 1231 : 1237);
		result = prime * result + nodeId;
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + nodeVal;
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
		if (finishID != other.finishID)
			return false;
		if (foundID != other.foundID)
			return false;
		if (isVisited != other.isVisited)
			return false;
		if (nodeId != other.nodeId)
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (nodeVal != other.nodeVal)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Object arg0) {
		final int LESS = -1;
		final int EQUAL = 0;
		final int BIG = 1;
		if( this == arg0){
			return EQUAL;
		}
		if( this.getFinishID() > ((Node)arg0).getFinishID()){
			return BIG;
		}
		else if ( this.getFinishID() < ((Node)arg0).getFinishID()){
			return LESS;
		}
		else {
			if( this.getNodeId() > ((Node)arg0).getNodeId()){
				return BIG;
			}
			else {
				return LESS;
			}
		}
	}
	
}

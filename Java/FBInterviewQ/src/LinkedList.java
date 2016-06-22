import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("rawtypes")
class ListNode implements Comparable{
	int val;
	ListNode left;
	ListNode right;
	int verValue;
	int height;
	StringBuilder tag;
	ListNode(int x){
		val = x;
		left = null;
		right = null;
	}
	@Override
	public String toString() {
		return "ListNode [val=" + val + ",verVal= " + verValue +"]"; 
		//return "ListNode [val=" + val + ",verVal: " + verValue + ", left=" + left + ", right=" + right
		//		+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + val;
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
		ListNode other = (ListNode) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		if (val != other.val)
			return false;
		return true;
	}
	@Override
	public int compareTo(Object arg0) {
		int result;
		if( this.verValue == ((ListNode)arg0).verValue ) {
			if( this.height == ((ListNode)arg0).height){
				String str1 = this.tag.toString();
				String str2 = ((ListNode)arg0).tag.toString();
				return str1.compareTo(str2);
			}
			return this.height - ((ListNode)arg0).height;
		}
			
		return this.verValue - ((ListNode)arg0).verValue ;
	}
	
}
public class LinkedList {
	ArrayList<ListNode> Nodes = new ArrayList<ListNode>();
	public ArrayList<ListNode> VerticalSorting(ListNode root){
		CalculateVerValue(root);
		Collections.sort(Nodes);
		System.out.println(Nodes.toString());
		return Nodes;
	}

	private void CalculateVerValue(ListNode parent) {
		parent.tag = new StringBuilder();
		PreOrderTraverse(parent);	
		System.out.println(parent);
	}

	private void PreOrderTraverse(ListNode parent) {
		int verVal = parent.verValue;
		int ht = parent.height;
		Nodes.add(parent);
		if( parent.left != null){
			parent.left.verValue = verVal - 1;
			parent.left.height = ht + 1;
			parent.left.tag = new StringBuilder(parent.tag);
			parent.left.tag.append("l");
			PreOrderTraverse(parent.left);
		}
			
		if( parent.right != null){
			parent.right.verValue = verVal + 1;
			parent.right.height = ht + 1; 
			parent.right.tag = new StringBuilder(parent.tag);
			parent.right.tag.append("r");
			PreOrderTraverse(parent.right);
		}
	}
}

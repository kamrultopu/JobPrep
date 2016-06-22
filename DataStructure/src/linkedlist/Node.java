package linkedlist;

public class Node {
	int val;
	Node prev;
	Node next;
	public Node(int val){
		this.val = val;
		prev = null;
		next = null;
	}
	public void SetPrev(Node prev){
		this.prev = prev;
	}
	public void SetNext(Node next){
		this.next = next;
	}
	@Override
	public String toString() {
		return "Node [val=" + val + ", prev=" + prev + ", next=" + next + "]";
	}
}

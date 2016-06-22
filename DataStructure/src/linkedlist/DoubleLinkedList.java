package linkedlist;

public class DoubleLinkedList extends LinkedList{
	
	public DoubleLinkedList(Node head) {
		super(head);
	}
	
	@Override
	public void InsertLast(Node newNode) {
		if( lastNode != null){
			//System.out.println(head);
			lastNode.next = newNode;
			newNode.prev = lastNode;
			lastNode = newNode;
		} else {
			head = newNode;
			lastNode = newNode;
		}
		
	}
	@Override
	public void InsertFirst(Node newNode) {
		newNode.next = head;
		head.prev = newNode;
		head = newNode;
	}
	@Override
	public void InsertPosition(Node newNode, int Pos) {
		Node temp = head;
		int count = 1 ; 
		while( count < Pos){
			temp = temp.next;
			while( temp == null ){
				return;
			}
			count++;
		}
		newNode.prev = temp;		
		newNode.next = temp.next;
		temp.next = newNode;
		temp = newNode.next;
		temp.prev = newNode;
		
	}
	@Override
	public void Remove(int key) {
		Node temp = head;
		Node prev = null;
		while(temp != null ){
			if( temp.val == key ){
				if(prev != null){
					prev.next = temp.next;
					temp.prev = null;
					temp = temp.next;
					temp.prev = prev;
				} else {
					head = head.next;
					head.prev = null;
				}
			}
			prev = temp;
			temp = temp.next;
		}
		
	}
}

package linkedlist;

public class SingleLinkedList extends LinkedList {
	public SingleLinkedList(Node head){
		super(head);
	}
	@Override
	public void Remove(int key) {
		Node temp = head;
		Node prev = null;
		while(temp != null ){
			if( temp.val == key ){
				if(prev != null){
					prev.next = temp.next;
				} else {
					head = head.next;
				}
			}
			prev = temp;
			temp = temp.next;
		}
	}

	
	@Override
	public void InsertLast(Node newNode) {
		if( lastNode != null){
			lastNode.next = newNode;
			lastNode = newNode;
		} else {
			head = newNode;
			lastNode = newNode;
		}
		
	}
	@Override
	public void InsertFirst(Node newNode) {
		newNode.next = head;
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
		newNode.next = temp.next;
		temp.next = newNode;
	}

}

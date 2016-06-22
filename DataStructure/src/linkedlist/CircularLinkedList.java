package linkedlist;

public class CircularLinkedList extends LinkedList {

	public CircularLinkedList(Node head) {
		super(head);
	}
	@Override
	public void Traverse(){
		Node temp = head;
		boolean isComplete = false;
		System.out.print("[");
		while( temp != head && !isComplete){
			System.out.print(temp.val );
			temp = temp.next;
			if( temp != head ){
				System.out.print(" ");
			} else {
				isComplete = true;
			}
		}
		System.out.print("]");
	}
	@Override
	public boolean Search(int key){
		Node tempNode = head;
		while( tempNode != null){
			if( tempNode.val == key){
				return true;
			}
			tempNode = tempNode.next;
		}
		return false;
	}
	
	@Override
	public void InsertLast(Node newNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void InsertFirst(Node newNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void InsertPosition(Node newNode, int Pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Remove(int key) {
		// TODO Auto-generated method stub

	}

}

package linkedlist;

public abstract class LinkedList {
	Node head;
	Node lastNode;
	public LinkedList(Node head){
		this.head = head;
		lastNode = head;
	}
	
	public void Traverse(){
		Node temp = head;
		System.out.print("[");
		while( temp != null ){
			System.out.print(temp.val );
			temp = temp.next;
			if( temp != null ){
				System.out.print(" ");
			}
		}
		System.out.print("]");
	}
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
	public abstract void InsertLast(Node newNode);
	public abstract void InsertFirst(Node newNode);
	public abstract void InsertPosition(Node newNode, int Pos);
	public abstract void Remove(int key);
	
}
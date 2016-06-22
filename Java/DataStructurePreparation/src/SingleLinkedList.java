
public class SingleLinkedList<T> implements LinkedList {
	Node<T> root;
	SingleLinkedList(T[] list){
		root = (Node<T>)CreateLinkedList(list);
	}
	
	@Override
	public Object CreateLinkedList(Object[] list) {
		Node<T> tempRoot = new Node<T>((T)(list[0]));
		Node<T> temp = tempRoot;
		//System.out.println(tempRoot);
		for( int i = 1 ; i < list.length;i++){
			Node<T> newNode = new Node<T>(((T)list[i]));
			//System.out.println(newNode);
			temp.next = newNode;
			temp = temp.next;
		}
		return tempRoot;
	}
	@Override
	public void TraverseLinkedList() {
		Node<T> temp = root;
		while(temp != null){
			System.out.println(temp);
			temp= temp.next;
		}
	}
	@Override
	public Object Search(Object target) {
		Node<T> tempRoot = root;
		while(tempRoot != null){
			if( tempRoot.equals(target)){
				break;
			}
			tempRoot = tempRoot.next;
		}
		return tempRoot;
	}
	@Override
	public void InsertLinkedList(Object targetNode, Object newNode) {
		if(targetNode == null){
			((Node<T>)newNode).next = root;
			root = (Node<T>) newNode;
			return;
		}
		Node<T> tempRoot = root;
		Node<T> prev = null;
		while(tempRoot != null){
			if( tempRoot.equals(targetNode)){
				break;
			}
			prev = tempRoot;
			tempRoot = tempRoot.next;
		}
		((Node<T>)newNode).next = tempRoot.next;
		prev.next = (Node<T>) newNode;
		
	}
	@Override
	public void DeleteNode(Object target) {
		Node<T> tempRoot = root;
		Node<T> prev = null;
		while(tempRoot != null){
			if( tempRoot.equals(target)){
				break;
			}
			prev = tempRoot;
			tempRoot = tempRoot.next;
		}
		if( prev == null){
			root = root.next;
		}
		else{
			prev.next = tempRoot.next;
		}
		
	}
}

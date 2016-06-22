
public class Node<T> {
	T keyVal;
	Node<T> left;
	Node<T> right;
	Node<T> next;
	Node<T> parent;
	Node(T tVal){
		//System.out.println("hi");
		keyVal = tVal;
		left = null;
		right = null;
		next = null;
		parent = null;
		//System.out.println(this);
	}
	@Override
	public String toString() {
		return "Node [keyVal=" + keyVal + ", left=" + left + ", right=" + right
				+ ", next=" + next + ", parent=" + parent + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<T> other = (Node<T>) obj;
		if (keyVal == null) {
			if (other.keyVal != null)
				return false;
		} else if (!keyVal.equals(other.keyVal))
			return false;
		return true;
	}
}

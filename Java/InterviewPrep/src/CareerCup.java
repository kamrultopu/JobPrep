import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

@SuppressWarnings("rawtypes")
class Info implements Comparable{
	int val;
	char type;
	Info(int x, char ch){
		val = x;
		type = ch;
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return val - ((Info)arg0).val;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(val)+" " + type;
	}
	
	
}

class Node{
	int key;
	Node left;
	Node right;
	Node parent;
	Node(int val){
		key = val;
		left = null;
		right = null;
	}
	public String toString(){
		return Integer.toString(key )+ " , left " + left + " , right:"+right;
	}
}

class BST{
	Node root;
	int  count;
	BST(int[] list){
		root = new Node(list[0]);
		//Node temp = root;
		for( int i = 0 ;i < list.length;i++){
			System.out.println("hi" + list[i]);
			InsertNode(root, list[i]);
			System.out.println("root" + root);
		}
	}
	public void InsertNode(Node nd, int val){
		nd= new Node(val);
	}
	public String showBST(Node temp){
		StringBuilder sb = new StringBuilder();
		while(temp!=null){
			System.out.print(temp + " ");
			sb.append(temp.toString() + " ");
			sb.append(showBST(temp.left));
			sb.append(showBST(temp.right));
		}
		return sb.toString();
	}
	public String toString(){
		return showBST(root);
	}
}


public class CareerCup {
	
	public boolean ParenthesisCheck(String str){
		boolean isBalanced = true;
		HashMap<Character, Character> hmap = new HashMap<Character,Character>();
		hmap.put('(', ')');
		hmap.put('{', '}');
		hmap.put('[', ']');
		Stack<Character> stack = new Stack<Character>();
		int size = str.length();
		for( int i = 0 ; i < size; i++){
			char ch = str.charAt(i);
			if( ch== '(' || ch=='{' || ch=='[' ){
				stack.push(ch);
			}
			else {
				if( stack.isEmpty()|| ch!= hmap.get(stack.pop())){
					isBalanced = false;
					break;
				}
			}
		}
		if(!stack.isEmpty())	
			isBalanced = false;
		return isBalanced;
	}
	
	public void BSTEx(int[] list){
		BST bst = new BST(list);
		System.out.println(bst);
	}
	public ArrayList<String> Permute(String str){
		ArrayList<String> permList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(str);
		permute(sb,permList,str.length());
		//System.out.println(permList.toString());
		return permList;
	}
	private void permute(StringBuilder sb, ArrayList<String> permList,int n){
		if( n == 1){
			permList.add(sb.toString());
		}
		else{
			
			for( int i = 0;i< n - 1;i++){
				permute(sb,permList,n-1);
				if( n %2 ==0)
					swap(sb,i,n-1);
				//System.out.println(sb.toString());
				else
					swap(sb,0,n-1);
			}
			permute(sb,permList,n-1);
			//System.out.println(sb.toString());
		}
	}
	private void swap(StringBuilder sb, int i, int j){
		char temp = sb.charAt(i);
		sb.setCharAt(i, sb.charAt(j));
		sb.setCharAt(j, temp);
	}
	
	
	public ArrayList<Integer> MergeRangeArray(int[] arr1, int[] arr2){
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Info> arrInfo = new ArrayList<Info>();
		Stack<Info> stack = new Stack<Info>();
		for( int i = 0 ; i < arr1.length - 1;i+=2){
			Info tempstart = new Info(arr1[i],'S');
			Info tempEnd = new Info(arr1[i+1],'E');
			arrInfo.add(tempstart);
			arrInfo.add(tempEnd);
			//System.out.println(tempstart + " " + tempEnd);
		}
		for( int i = 0 ; i < arr2.length - 1;i+=2){
			Info tempstart = new Info(arr2[i],'S');
			Info tempEnd = new Info(arr2[i+1],'E');
			arrInfo.add(tempstart);
			arrInfo.add(tempEnd);
		}
		Collections.sort(arrInfo);
		//System.out.println(arrInfo.toString());
		for( int i = 0 ; i < arrInfo.size();i++){
			if( arrInfo.get(i).type == 'S'){
				stack.push(arrInfo.get(i));
			}
			else{
				Info temp = stack.pop();
				if( stack.isEmpty()){
					list.add(temp.val);
					list.add(arrInfo.get(i).val);
				}
			}
		}
		return list;
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Item implements Comparable{
	int val;
	char type;
	Item(int x, char ch){
		val = x;
		type = ch;
	}
	@Override
	public int compareTo(Object arg0) {
		if( this.val == ((Item)arg0).val){
			return this.type - ((Item)arg0).type;
		}
		return this.val - ((Item)arg0).val;
	}
	@Override
	public String toString() {
		return "Item [val=" + val + ", type=" + type + "]";
	}
	
}

public class Interval {
	public ArrayList<Integer> MeregeInterval(ArrayList<Integer> list1, ArrayList<Integer> list2){
		ArrayList<Item> items = new ArrayList<Item>();
		ArrayList<Integer> mergeList = new ArrayList<Integer>();
		for(int i = 0 ; i < list1.size();i+=2){
			int temp = list1.get(i);
			items.add(new Item(temp,'S'));
			temp = list1.get(i+1);
			items.add(new Item(temp,'E'));
		}
		for(int i = 0 ; i < list2.size();i+=2){
			int temp = list2.get(i);
			items.add(new Item(temp,'S'));
			temp = list2.get(i+1);
			items.add(new Item(temp,'E'));
		}
		Collections.sort(items);
		Stack<Item> stack = new Stack<Item>();
		for( Item it : items){
			Item temp = null;
			if( it.type == 'S'){
				stack.push(it);
			}
			else{
				temp = stack.pop();
			}
			if( stack.isEmpty()){
				mergeList.add(temp.val);
				mergeList.add(it.val);
			}
		}
		return mergeList;
	}
}

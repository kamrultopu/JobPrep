import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

class Item implements Comparable<Item>{
	int val;
	char type;
	Item partner;
	int height;
	Item(int x, char ch){
		val = x;
		type = ch;
	}
	
	
	@Override
	public String toString() {
		return "Item [val=" + val + ", type=" + type + ", height:" + height +",partner.val="+partner.val+"]";
	}
	public static class Comparators{
		public static Comparator<Item> Building = new Comparator<Item>() {
			public int compare(Item it1,Item it2){
				if( it1.val == it2.val){
					if( it1.type == it2.type ){
						return  it1.partner.val - it2.partner.val;  
					}
					return  it1.type - it2.type;
				}
				return it1.val - it2.val;
			}
		};
		public static Comparator<Item> HEIGHT = new Comparator<Item>() {
			public int compare(Item it1,Item it2){
				return it2.height - it1.height;
			}
		};
	}
	@Override
	public int compareTo(Item arg0) {
		return Comparators.Building.compare(this, arg0);
	}
	
}

public class SkyLine {
	public ArrayList<Integer> getSkyLIne(ArrayList<ArrayList<Integer>> buildings){
		ArrayList<Integer> skyline;
		ArrayList<Item> items = new ArrayList<Item>();
		int count = buildings.size();
		for( int i = 0 ; i < count ; i++){
			Item startItem = new Item(buildings.get(i).get(0),'S');
			Item endItem = new Item(buildings.get(i).get(2),'E');
			int ht = buildings.get(i).get(1);
			startItem.height = ht;
			endItem.height = ht;
			startItem.partner = endItem;
			endItem.partner = startItem;
			items.add(startItem);
			items.add(endItem);
		}
		Collections.sort(items,Item.Comparators.Building);
		skyline = createSkyline(items);
		return skyline;
	}

	private ArrayList<Integer> createSkyline(ArrayList<Item> items) {
		ArrayList<Integer> skyline = new ArrayList<Integer>();
		PriorityQueue<Item> heightHeap = new PriorityQueue<Item>(Item.Comparators.HEIGHT);
		HashSet<Item> itemSet = new HashSet<Item>();
		int count = items.size();
		Item prevStart = null;
		Item prevEnd = null;
		Item curH = null;
		for( int i = 0 ; i < count; i++){
			Item temp = items.get(i);
			System.out.println(temp + "curH: " + curH );
		
			if( temp.type == 'S'){
				//System.out.println("in Start");
				if( itemSet.isEmpty()){
					if( prevStart != null){
						skyline.add(0);
					}
					if( prevStart == null || prevStart.val != temp.val){
						if( prevEnd == null || prevEnd.val != temp.val){
							skyline.add(temp.val);
							curH = temp;
						}	
					}
				}
				else{
					
					if( temp.height > curH.height ){
						System.out.println("in greater");
						if( prevStart.val != temp.val){
							if( prevEnd == null || prevEnd.val != temp.val){
								System.out.println("inn");
								skyline.add(heightHeap.peek().height);
							}
							skyline.add(temp.val);
						}
						curH = temp;
					}
					else if ( temp.height == curH.height){
						curH = temp;
					}
				}
				prevStart = temp;
				itemSet.add(temp);
				heightHeap.add(temp);
			}
			else{
				//System.out.println("in End" + "partner: " + temp.partner);
				if( temp.partner == curH || temp.height == curH.height){
					System.out.println("equal");
					heightHeap.remove(temp.partner);
					if( heightHeap.isEmpty() || curH.height != heightHeap.peek().height){
						if( prevEnd == null || prevEnd.val != temp.val ){
							skyline.add(curH.height);
							skyline.add(temp.val);
							curH = heightHeap.peek();
						}
						else{
							if( prevEnd != null && prevEnd.height == temp.height ){
								skyline.add(curH.height);
								skyline.add(temp.val);
								curH = heightHeap.peek();
							}
						}
						curH = heightHeap.peek();
					}
				}
				else{
					heightHeap.remove(temp.partner);
				}
			
				prevEnd = temp;
				itemSet.remove(temp.partner);
			}
			//System.out.println("items: " + itemSet.toString());
			//System.out.println("prevStart:" + prevStart);
			//System.out.println("prevEnd:" + prevEnd);
			//System.out.println("heap" + heightHeap.toString());
			System.out.println("skyline" + skyline.toString());
		}
		skyline.add(0);
		return skyline;
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

class BuildBlock{
	int start;
	int height;
	int end;
	BuildBlock(int st,int ht, int ed){
		start = st;
		height = ht;
		end = ed;
	}
}
class Cell implements Comparable{
	char Type;
	int corresPond;
	int height;
	Cell(char ch, int a, int b){
		Type = ch;
		corresPond = a;
		height = b;
	}
	@Override
	public String toString() {
		return "Cell [Type=" + Type + "]";//, corresPond=" + corresPond
				//+ ", height=" + height + "]";
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return height - ((Cell)arg0).height;
	}
	
}
class SkyCraper{
	ArrayList<BuildBlock> blocks;
	TreeMap<Integer,ArrayList<Cell>> util = new TreeMap<Integer,ArrayList<Cell>>();
	SkyCraper(){
		blocks = new ArrayList<BuildBlock>();
	}
	public String getSkyLine() {
		//System.out.println("hello");
		for(BuildBlock bb:blocks){
			if(util.containsKey(bb.start)){
				ArrayList<Cell> tempCell = util.get(bb.start);
				tempCell.add(new Cell('S',bb.end,bb.height));
			}
			else{
				ArrayList<Cell> tempCell = new ArrayList<Cell>();
				tempCell.add(new Cell('S',bb.end,bb.height));
				util.put(bb.start,tempCell);
			}
			if(util.containsKey(bb.end)){
				ArrayList<Cell> tempCell = util.get(bb.end);
				tempCell.add(new Cell('E',bb.start,bb.height));
			}
			else{
				ArrayList<Cell> tempCell = new ArrayList<Cell>();
				tempCell.add(new Cell('E',bb.start,bb.height));
				util.put(bb.end,tempCell);
			}
		}
		//System.out.println(util.toString());
		StringBuilder sb = new StringBuilder();
		int startCount = 0;
		int currentHeight = 0;
		HashSet<Integer> tempSet = new HashSet<Integer>();
		PriorityQueue<Integer> heightPQ = new PriorityQueue<Integer>(15,Collections.reverseOrder());
		heightPQ.add(0);
		Cell prev = null;
		for(Integer i:util.keySet()){
			//System.out.println("hello");
			ArrayList<Cell> cellList = util.get(i);
			cellList.sort(Collections.reverseOrder());
			for( int j = 0 ; j < cellList.size();j++){
				Cell cell = cellList.get(j);
				if( prev != null ){
					if( prev.Type == cell.Type ){
						continue;
					}
				}
				if( cell.Type == 'S'){
					tempSet.add(i);
					currentHeight = heightPQ.peek();
					if( cell.height > currentHeight){
						sb.append(i+" ");
						sb.append(cell.height + " ");
					}
					heightPQ.add(cell.height);
					//startCount++;
				}
				else{
					tempSet.remove(cell.corresPond);
					currentHeight = heightPQ.peek();
					if( cell.height == currentHeight || tempSet.isEmpty() ){
						sb.append(i + " ");
					}
					heightPQ.remove(cell.height);
					if(cell.height == currentHeight && !heightPQ.isEmpty()){
						sb.append(heightPQ.peek()+ " ");
					}
					//startCount--;
				}
				prev = cell;
			}
			prev = null;
		}
		return sb.toString();
	}
}

public class Main {

	public static void main(String[] args) {
		SkyCraper skCrap = new SkyCraper();
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			//System.out.println("hi");
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();
			skCrap.blocks.add(new BuildBlock(a,b,c));
			sc.nextLine();
		}
		System.out.println(skCrap.getSkyLine());
		
	}

}

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

class MaxCycle{   //UVA Problem 100
	HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();
	public void Begin(){
		String input;
		int a, b, min,max,num,n,cycle,cyclemax;
		hmap.clear();
		Scanner sc = new Scanner(System.in);
		while( sc.hasNext()){
			cyclemax = -1;
			a = sc.nextInt();
			b = sc.nextInt();
			//System.out.println("a: " + a + " b: "+b);
			if( a > b){
				max = a;
				min = b;
			}
			else{
				max = b;
				min = a;
			}
			for( int i = min ; i <= max ; i++){
				cycle = getCycle(i);
				if( cycle > cyclemax){
					cyclemax = cycle;
				}
			}
			System.out.println(a+" "+b+" "+cyclemax);
		}
	}
	public int getCycle(int n){
		int cycleCount = 0;
		if( n == 1 ){
			return 1;
		}
		if( hmap.containsKey(n)){
			return hmap.get(n);
		}
		else {
			if( n % 2 == 0){
				n = n/2;
				cycleCount = 1 + getCycle(n);
			}
			else{
				n = 3 * n + 1;
				cycleCount = 1 + getCycle(n);
			}
		}
		return cycleCount;
	}
}

class MaxSum{
	int[][] input;
	int[][] sumArray;
	int nDimension;
	int[][] tempArray;
	public void Begin(){
		int maxSum; 
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			maxSum = Integer.MIN_VALUE;
			nDimension = sc.nextInt();
			input = new int[nDimension][nDimension];
			sumArray = new int[nDimension][nDimension];
			tempArray = new int[nDimension][nDimension];
			for( int i = 0 ; i < nDimension ; i++){
				for( int j = 0 ; j < nDimension;j++){
					//System.out.println("i: " +i + "j"+j);
					input[i][j] = sc.nextInt();
				}
			}
			//System.out.println("hi");
			/*for( int i = 0 ; i < nDimension; i++)
				System.out.println(Arrays.toString(input[i]));
			System.out.println("start");*/
			for( int i = 0 ; i < nDimension ; i++){
				tempArray[i] = Arrays.copyOf(input[i], nDimension);
			}
			for( int i = 0 ; i < nDimension ; i++){
				for( int j = 0 ; j < nDimension ; j++){
					input[j] = Arrays.copyOf(tempArray[j], nDimension);
				}
				//System.out.println("int : " + i);
				for( int j = 0 ; j < i; j++){
					for( int k = 0; k < nDimension;k++){
						input[k][j]= 0;
					}
				}
				for( int j = 0 ; j < nDimension ; j++){
					sumArray[j] = Arrays.copyOf(input[j], nDimension);
				}
				int temp = CalculateSum(i,1);
				if( temp > maxSum){
					maxSum = temp;
				}
				for( int j = 0 ; j < nDimension ; j++){
					input[j] = Arrays.copyOf(tempArray[j], nDimension);
				}
				for( int j = 0 ; j < i; j++){
					for( int k = 0; k < nDimension;k++){
						input[j][k]= 0;
					}
				}
				for( int j = 0 ; j < nDimension ; j++){
					sumArray[j] = Arrays.copyOf(input[j], nDimension);
				}
				temp = CalculateSum(i,2);
				if( temp > maxSum){
					maxSum = temp;
				}
			}
			System.out.println(maxSum);
		}
	}
	public int CalculateSum(int startPos, int orientation){
		int max = Integer.MIN_VALUE;
		for( int i = 1 ; i < nDimension ; i++){
			sumArray[0][i] = sumArray[0][i-1] + sumArray[0][i];
			sumArray[i][0] = sumArray[i-1][0] + sumArray[i][0];
		}
		for( int i = 1 ; i < nDimension; i++){
			for( int j = 1; j< nDimension;j++){
				sumArray[i][j] += (sumArray[i-1][j] + sumArray[i][j-1] - sumArray[i-1][j-1]); 
			}
		}
		if( orientation == 1){
			for( int i = 0 ; i < nDimension; i++){
				for( int j = startPos ; j< nDimension;j++){
					if( sumArray[i][j] > max){
						max = sumArray[i][j];
					}
				}
			}
		}
		else{
			for( int i = startPos ; i < nDimension; i++){
				for( int j = 0 ; j< nDimension;j++){
					if( sumArray[i][j] > max){
						max = sumArray[i][j];
					}
				}
			}
		}
		
		return max;
	}
}

class StackBox{
	HashMap<Integer,ArrayList<Integer>> boxes = new HashMap<Integer,ArrayList<Integer>>();
	HashMap<Integer,HashSet<Integer>> subBoxes = new HashMap<Integer,HashSet<Integer>>();
	HashMap<Integer,ArrayList<Integer>> hasResult = new HashMap<Integer,ArrayList<Integer>>();
	
	int dimension = 0;
	int noOfBox = 0;
	Scanner sc = new Scanner(System.in);
	public void Begin(){
		while(sc.hasNext()){
			boxes.clear();
			subBoxes.clear();
			noOfBox = sc.nextInt();
			dimension = sc.nextInt();
			GetBoxInfo();
			ProcessSubBox();
			//Debug();
			//String result = "";
			ArrayList<Integer>  result = null;
			int maxLength = -1;
			for( int i = 0 ; i < noOfBox ; i++){
				ArrayList<Integer>  tempString = FindSubBoxLength(i);
				int Length = tempString.size();
				if( Length > maxLength){
					maxLength = Length;
					result = tempString;
				}
			}
			System.out.println(result.size());
			//StringBuilder strbuild = new StringBuilder(result);
			System.out.println(getString(result));
		}
	}
	
	private String getString(ArrayList<Integer> list){
		StringBuilder sb = new StringBuilder();
		for( int i = 0 ; i < list.size();i++){
			sb.append(Integer.toString(list.get(i)+1));
			sb.append(" ");
		}
		return sb.toString();
	}
	
	private void Debug(){
		System.out.println("box info: ");
		for( int i = 0 ; i < noOfBox; i++){
			System.out.println(boxes.get(i).toString());
		}
		System.out.println("subBox info: ");
		for( int i = 0 ; i < noOfBox; i++){
			System.out.println(subBoxes.get(i).toString());
		}
	}
	
	private ArrayList<Integer> FindSubBoxLength(int target){
		if( subBoxes.get(target).size()==0){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(target);
			hasResult.put(target, temp);
			return temp;
		}
		if ( hasResult.containsKey(target)){
			return hasResult.get(target);
		}
		int maxLength = -1;
		int size = subBoxes.get(target).size();
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.addAll(subBoxes.get(target));
		ArrayList<Integer> strResult = null;
		for( int i = 0 ; i < size ; i++ ){
			ArrayList<Integer> tempResult = FindSubBoxLength(array.get(i));
			tempResult.add(target);
			int length = tempResult.size();
			if( length > maxLength){
				maxLength = length;
				strResult = tempResult;
			}
		}
		//System.out.println("temp result" + result.toString());
		return strResult;
	}
	
	private void ProcessSubBox(){
		boolean[] checkBox = new boolean[noOfBox];
		for( int i = 0 ; i < noOfBox; i++){
			Arrays.fill(checkBox, false);
			HashSet<Integer> temp = subBoxes.get(i);
			for( int j = 0 ; j < noOfBox ; j++){
				if( i == j || checkBox[j])
					continue;
				checkBox[j] = true;
				if(checkSmaller(i,j)){
					temp.add(j);
					temp.addAll(subBoxes.get(j));
					for(Integer e:subBoxes.get(j)){
						checkBox[e.intValue()] = true;
					}
				}
			}
		}
	}
	public void GetBoxInfo(){
		for( int i = 0 ; i < noOfBox ; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			HashSet<Integer> tempSub = new HashSet<Integer>();
			for( int j = 0 ; j < dimension; j++){
				int side = sc.nextInt();
				temp.add(side);
			}
			boxes.put(i, temp);
			subBoxes.put(i, tempSub);
		}
	}
	private boolean checkSmaller( int Big, int Small){
		boolean IsSmaller = true;
		int[] temp1 = ArrayListtoArray(boxes.get(Big));
		int[] temp2 = ArrayListtoArray(boxes.get(Small));
		
		Arrays.sort(temp1);
		Arrays.sort(temp2);
		for( int i = 0 ; i < dimension ; i++){
			if(temp1[i] < temp2[i]){
				IsSmaller = false;
				break;
			}
		}
		
		return IsSmaller;
	}
	public int[] ArrayListtoArray(ArrayList<Integer> list){
		int[] array = new int[list.size()];
		for( int i = 0 ; i < list.size();i++){
			array[i]= list.get(i).intValue();
		}
		return array;
	}
	
}

/*class Item implements Comparable{
	int val;
	char type;
	Item partner;
	int height;
	Item(int x, char ch){
		val = x;
		type = ch;
	}
	@Override
	public int compareTo(Object arg0) {
		if( this.val == ((Item)arg0).val){
			return ((Item)arg0).type - this.type;
		}
		return this.val - ((Item)arg0).val;
	}
	@Override
	public String toString() {
		return "Item [val=" + val + ", type=" + type + "]";
	}
	
}

class SkyLine {
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
		Collections.sort(items);
		skyline = createSkyline(items);
		return skyline;
	}

	private ArrayList<Integer> createSkyline(ArrayList<Item> items) {
		ArrayList<Integer> skyline = new ArrayList<Integer>();
		PriorityQueue<Integer> heightHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
		HashSet<Item> itemSet = new HashSet<Item>();
		int count = items.size();
		Item prevEnd = null;
		Item prevStart = null;
		for( int i = 0 ; i < count; i++){
			Item temp = items.get(i);
			int ht = temp.height;
			//System.out.println(temp);
			if( temp.type == 'S'){
				
				if( itemSet.isEmpty()){
					skyline.add(temp.val);
					skyline.add(ht);
				}
				else{
					if( prevStart != null){
						if( temp.val == prevStart.val){
							skyline.remove(skyline.size()-1);
							skyline.add(temp.height);
						}
						if( ht > heightHeap.peek() ){
							skyline.add(temp.val);
							skyline.add(ht);
						}
					}
				}
				itemSet.add(temp);
				heightHeap.add(ht);
				prevStart = temp;
			}
			else{
				
				
				if( itemSet.isEmpty()){
					itemSet.remove(temp.partner);
					skyline.add(0);
					skyline.add(temp.val);
					heightHeap.remove(ht);
				}
				else{
					itemSet.remove(temp.partner);
					if( ht == heightHeap.peek()){
						heightHeap.remove(ht);
						if( prevEnd == null){
							skyline.add(temp.val);
							skyline.add(heightHeap.peek());
						}
						else if(  temp.val != prevEnd.val){
							skyline.add(temp.val);
						}
						else {
							
							if( !heightHeap.isEmpty() && temp.height > heightHeap.peek() ){
								skyline.add(heightHeap.peek());
							}
						}
					}
					else{
						heightHeap.remove(ht);
					}
					prevEnd = temp;
				}
			}
			//System.out.println(heightHeap.toString());
			//System.out.println(skyline.toString());
			//System.out.println(itemSet.toString());
		}
		skyline.add(0);
		return skyline;
	}
}
*/
class Main {
	static String ReadLn (int maxLg)  // utility function to read from stdin
	    {
	        byte lin[] = new byte [maxLg];
	        int lg = 0, car = -1;
	        String line = "";

	        try
	        {
	            while (lg < maxLg)
	            {
	                car = System.in.read();
	                if ((car < 0) || (car == 13)) {
	                	car = System.in.read();
	                	break;
	                }
	                lin [lg++] += car;
	            }
	        }
	        catch (IOException e)
	        {
	            return (null);
	        }
	        if ((car < 0) && (lg == 0)) return (null);  // eof
	        return (new String (lin, 0, lg));
	    }
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> buildings = new ArrayList<ArrayList<Integer>>();
		//int[][] building = {{1,11,5},{2,6,7},{3,13,9},{12,7,16},{14,3,25},{19,18,22},{23,13,29},{24,4,28}};
		
		
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNextInt()){
			int[] arr = new int[3];
			arr[0]= sc.nextInt();
			arr[1] = sc.nextInt();
			arr[2] = sc.nextInt();
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for( int i = 0 ; i< 3; i++){
				temp.add(arr[i]);
				
			}
			buildings.add(temp);
			sc.hasNextLine();
		}
		/*
		for( int i = 0 ; i < building.length;i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for( int j = 0 ; j < 3 ; j++){
				temp.add(building[i][j]);
			}
			buildings.add(temp);
		}
		*/
		ArrayList<Integer> skyline = new SkyLine().getSkyLIne(buildings);
		for( int i = 0 ; i < skyline.size();i++){
			System.out.print(skyline.get(i));
			if(i != skyline.size()-1){
				System.out.print(" ");
			}
		}
		//StackBox sb = new StackBox();
		//sb.Begin();
	}

}

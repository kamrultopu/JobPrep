package Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class ArrayNumber implements Comparable<ArrayNumber>{
	ArrayList<Integer> nums;
	int size;
	ArrayNumber(ArrayList<Integer> temp){
		nums = (ArrayList<Integer>) temp.clone();
		size = nums.size();
	}
	@Override
	public int compareTo(ArrayNumber o) {
		if( this.size == o.size ){
			for( int i = 0 ; i < this.size; i++ ){
				if( this.nums.get(i) != o.nums.get(i)){
					return this.nums.get(i) - o.nums.get(i);
				}
			}
			return 0;
		}
		return this.size - o.size;
	}
	@Override
	public String toString() {
		return "ArrayNumber [nums=" + nums + "]";
	}
	
}


public class Bignumber{
	int[] array;
	int   sizeOfArray;
	HashMap<Integer,ArrayNumber> maxList = new HashMap<Integer,ArrayNumber>();
	Bignumber(){
		
	}
	Bignumber(int[] nums){
		array = nums.clone();
		sizeOfArray = nums.length;
		int size = nums.length;
		maxList.put(0, new ArrayNumber(new ArrayList<Integer>()));
		for( int i = 1; i <= size ; i++ ){
			if( !maxList.containsKey(i)){
				maxList.put(i, new ArrayNumber(SetBigNumberForLengthK(0,size - 1,i)));
			}
			
		}
		System.out.println(maxList.toString());
	}
	public ArrayList<Integer> SetBigNumberForLengthK(int start, int end, int length){
		ArrayList<Integer> temp = null;
		int need = length;
		if( need == 1 ){
			temp = new ArrayList<Integer>();
			int pos = FindMaxPos(start,end);
			temp.add(pos);
			return temp;
		}
		int size = end - start + 1;
		ArrayList<Integer> newTemp = null;
		if( size == length ){
			newTemp = new ArrayList<Integer>();
			for( int i = start; i <=end; i++){
				newTemp.add(i);
			}
			return newTemp;
		}
		temp = maxList.get(length - 1).nums;
		
		newTemp = (ArrayList<Integer>) temp.clone();
		
		
		for( int i = newTemp.size() - 1 ; i >= 0 ;i--){
			int last = newTemp.get(i);
			if( last < start || last+need > end){
				newTemp.remove(i);
				need++;
			}
			else{
				break;
			}
		}
		if( newTemp.isEmpty()){
			int last = end-length + 1;
			int pos = FindMaxPos(start,last);
			newTemp.add(pos);
			
		}
		need = length - newTemp.size();
		int lastIndex = newTemp.get(newTemp.size() - 1);
		newTemp.addAll(SetBigNumberForLengthK(lastIndex + 1, end,need));
		return newTemp;
	}
	private int FindMaxPos(int start, int end) {
		int index = start;
		int max = array[index];
		
		for( int i = start + 1; i<=end; i++){
			if( max < array[i]){
				max = array[i];
				index = i;
			}
		}
		return index;
	}
	public ArrayList<Integer> maxNumber(int[] array1, int[] array2, int size) {
		int size1 = array1.length;
		int size2 = array2.length;
		if( size1 < size2 ){
			return maxNumber(array2,array1,size);
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		int start1 = 0;
		int start2 = 0;
		int end1 = size1 - 1;
		int end2 = size2 - 1;
		int k = size;
		boolean flag1 = false;
		boolean flag2 = false;
		int pos1,pos2;
		while(k > 0){
			//System.out.println("flag1: " + flag1 + ", flag2:"+flag2);
			if( k - (size2 - start2 ) < 2 ){
				end1 = size1 - 1;
			}
			else{
				end1 = size1 - (k - size2 + start2) ;
			}
			if( k - (size1 - start1 ) < 2 ){
				end2 = size2 - 1;
			}
			else{
				end2 = size2 - (k - size1 + start1) ;
			}
			pos1 = -1;
			pos2 = -1;
			if( start1 <= end1 ){
				pos1 = GetMaxPos(array1,start1,end1);
			}
			if( start2 <= end2){
				pos2 = GetMaxPos(array2,start2,end2);
			}
			if( pos1 == - 1 ){
				result.add(array2[pos2]);
				start2 = pos2 + 1;
				flag1 = true;
			}
			else if ( pos2 == -1 ){
				result.add(array1[pos1]);
				start1 = pos1 + 1;
				flag2 = true;
			}
			else {
				if ( array1[pos1] > array2[pos2]){
					result.add(array1[pos1]);
					start1 = pos1 + 1;
					flag1 = true;
				}
				else if( array1[pos1]==array2[pos2]){
					System.out.println("hi" + pos1 + ", " + pos2);
					int flag = 1;
					flag = GetWhich(array1,array2,start1,start2,pos1,pos2);
					System.out.println(flag);
					System.out.println(Arrays.toString(array1) + ", "+Arrays.toString(array2));
					if( flag == 1 ){
						result.add(array1[pos1]);
						start1 = pos1 + 1;
					}
					else{
						result.add(array2[pos2]);
						start2 = pos2 + 1;
					}
					
				}
				else{
					result.add(array2[pos2]);
					start2 = pos2 + 1;
					flag2 = true;
				}
			}
			//System.out.println(result.toString());
			k--;
		}
		/*int[] retVal = new int[result.size()];
		for( int i =0 ; i < result.size();i++){
		    retVal[i] = result.get(i);
		}*/
		return result;
	}
	
	private int GetWhich(int[] array1, int[] array2, int start1, int start2,int pos1, int pos2) {
		int result = 1;
		int index1 = start1 + 1;
		int index2 = start2 + 1;
		int size1 = array1.length;
		int size2 = array2.length;
		//System.out.println("index1: " + index1 + " index2: "+ index2);
		while(index1 < pos1 && index2 <pos2 ){
			System.out.println("index1: " + index1 + " index2: "+ index2);
			System.out.println("arr1: " + array1[index1] + " arr2: "+ array2[index2]);
			if(array1[index1] > array2[index2]){
				return 2;
			}
			else if ( array1[index1] < array2[index2] ){
				return 1;
			}
			else {
				index1++;
				index2++;
			}
		}
		while(index1 < size1 && index2 < size2 ){
			System.out.println("index1: " + index1 + " index2: "+ index2);
			System.out.println("arr1: " + array1[index1] + " arr2: "+ array2[index2]);
			if(array1[index1] > array2[index2]){
				return 1;
			}
			else if ( array1[index1] < array2[index2] ){
				return 2;
			}
			else {
				index1++;
				index2++;
			}
		}
		if( pos1 < pos2 )
			result = 1;
		else if ( pos1 > pos2 ){
		    result = 2;
		}
		else {
		    if( index1 < size1 )
		        result = 1;
		}
		return result;
	}
	private int GetMaxPos(int[] Array, int start, int end){
		//System.out.println("start: "+start + " end:"+ end);
		int index = start;
		int max = Array[index];
		for( int i = start + 1 ; i <=end;i++){
			if( max < Array[i]){
				index = i;
				max = Array[i];
			}
		}
		return index;
	}
	
	
	
}
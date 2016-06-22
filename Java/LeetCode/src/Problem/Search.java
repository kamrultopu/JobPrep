package Problem;

import java.util.Arrays;

public class Search {
	public static int linearSearch(int[] list, int target){
		int nIndex = -1;
		for( int i = 0 ; i < list.length; i++){
			if( list[i] == target){
				nIndex = i;
				break;
			}
		}
		return nIndex;
	}

	public static int BinarySearch(int[] list, int target){
		int nIndex = -1;
		int[] tempList = Arrays.copyOfRange(list, 0, list.length);
		Sorting.qSort(tempList, true);
		nIndex = BSearch(tempList,0,tempList.length-1,target);
		return nIndex;
	}
	private static int BSearch(int[] list, int from, int to, int target ){
		int nIndex = -1;
		int size = to - from;
		System.out.println(from + ", " + to + " , "+target + ",size"+ size);
		if( size < 1 ){
			if( list[to] == target)
				return to;
			else
				return nIndex;
		}
		int midPoint = (to-from)/2;
		System.out.println("midpoint: " + midPoint);
		System.out.println("val: " + list[midPoint]);
		if( list[midPoint] == target){
			nIndex = midPoint;
		}
		else{
			if( list[midPoint] > target ){
				BSearch(list,from,midPoint-1,target);
			}
			else{
				//BSearch(list,midPoint + 1, to, target);
			}
		}
		return nIndex;
	}
	
}

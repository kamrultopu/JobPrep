package Problem;

import java.util.Arrays;

public class Sorting {
	public static void bubbleSort(int[] list){
		int size = list.length;
		int noOfSwap = 0;
		for( int i = 0 ; i < size;i++){
			for( int j = 0 ; j < size - i - 1; j++){
				if( list[j] > list[j+1]){
					noOfSwap++;
					swap(list,j,j+1);
				}
			}
			if(noOfSwap == 0)
				break;
		}
		System.out.println("no of swap: " + noOfSwap);
	}
	
	private static void swap(int[] list,int a, int b){
		int temp = list[a];
		list[a] = list[b];
		list[b] = temp;
	}
	
	public static void insertionSort(int[] list){
		int size = list.length;
		
		for( int i = 0 ; i < size-1 ;){
			//System.out.println(Arrays.toString(list));
			int sortedIndex = searchSortedRegion(list,i);
			//System.out.println("sortedIndex index: " + sortedIndex);
			if( sortedIndex >= size - 1 )
				break;
			int insertIndex = seekInsertedIndex(list,sortedIndex + 1);
			//System.out.println("insertIndex index: " + insertIndex);
			shiftArray(list,insertIndex,sortedIndex + 1);
			i = sortedIndex + 1 ;
		}
	}
	private static void shiftArray(int[] list, int targetPos, int sourcePos ){
		int temp = list[sourcePos];
		int noOfStatic = 0;
		for( int j = sourcePos ; j > targetPos ; j--){
			list[j] = list[j-1];
			noOfStatic++;
		}
		list[targetPos] = temp;
		//System.out.println("no: "+ noOfStatic);
	}
	private static int seekInsertedIndex(int[] list, int target){
		int index = target;
		
		for( int i = 0 ; i < target ; i++ ){
			if( list[target] < list[i]){
				index = i;
				break;
			}	
		}
		return index;
	}
	private static int searchSortedRegion(int[] list, int startPos){
		int size = list.length;
		int index = size - 1;
		for( int i = startPos ; i < size -1 ; i++){
			if( list[i] > list[i+1]){
				index = i  ;
				break;
			}
		}
		return index;
	}
	
	public static void selectionSort(int[] list){
		int size = list.length;
		for( int i = 0 ; i < size; i++ ){
			int targetPos = indexOfTarget(list,i);
			//System.out.println(targetPos);
			swap(list,i,targetPos);
			//System.out.println(Arrays.toString(list));
		}
	}
	private static int indexOfTarget(int[] list, int startPos){
		int target = list[startPos];
		int targetIndex = startPos;
		int size = list.length;  
		for( int i = startPos + 1; i< size; i++ ){
			if(list[i] < target){
				targetIndex = i;
				target = list[i];
			}
		}
		return targetIndex;
	}
	
	public static int[] mergeSort(int[] list){
		int size = list.length;
		if(size < 8){
			insertionSort(list);
			return list;
		}	
		int midPoint = size/2;
		int[] leftList = Arrays.copyOfRange(list, 0, midPoint);
		int[] rightList = Arrays.copyOfRange(list,midPoint,size);
		leftList = mergeSort(leftList);
		rightList = mergeSort(rightList);
		list = merge(leftList,rightList);
		return list;
	}
	private static int[] merge(int[] list1, int[] list2){
		int size1 = list1.length;
		int size2 = list2.length;
		int[] list = new int[size1+size2];
		int i=0;
		int j=0;
		int k = 0 ;
		while(i<size1 && j < size2){
			list[k++] = list1[i]<list2[j]?list1[i++]:list2[j++];
		}
		while( i < size1){
			list[k++] = list1[i++];
		}
		while( j < size2){
			list[k++] = list2[j++];
		}
		return list;
	}
	
	public static void qSort(int[] list, boolean isAscending){
		int size = list.length;
		QExecution(list, 0, size-1, isAscending);
		
	}
	private static void QExecution(int[] list, int from, int to, boolean isAscending){
		if( from >= to){
			return;
		}
		int pivot = list[to];
		int partition = partitionFunc(list,from,to-1,pivot,isAscending);
		QExecution(list, from, partition -1, isAscending);
		QExecution(list, partition + 1, to, isAscending);
	}
	private static int partitionFunc(int[] list, int left, int right, int pivot, boolean isAscending){
		int pivotPos = right + 1;
		while( true ){
			while( list[left] < pivot ){
				left++;
			}
			while( list[right] > pivot && right > 0){
				right--;
			}
			if( left >= right){
				break;
			}
			swap(list,left,right);
			left++;
			right--;
		}
		swap(list,left,pivotPos);
		return left;
	}
	
	
}

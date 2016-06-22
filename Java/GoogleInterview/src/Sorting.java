import java.util.Arrays;

class Heap{
	int[] heapArray;
	int size;
	Heap(int[] array){
		heapArray = array;
		BuildHeap(heapArray);
	}
	public int[] getHeap(){
		return heapArray;
	}
	private void BuildHeap(int[] array){
		int size = array.length;
		for( int i = size/2 ; i >=0 ;i--){
			Heapify(array,i,size);
		}
		//System.out.println(Arrays.toString(heapArray));
	}
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public  void Heapify(int[] array, int pos, int heapsize){
		//System.out.println("pos: " + pos);
		int leftPos = getLeft(pos);
		int rightPos = getRight(pos);
		int largest = pos;
		//System.out.println("left: " + leftPos + " right " + rightPos);
		if( leftPos < heapsize && heapArray[leftPos] > heapArray[pos]){
			largest = leftPos;
		}
		if( rightPos < heapsize && heapArray[rightPos] > heapArray[largest] ){
			largest = rightPos;
		}
		//System.out.println("largest: " + largest);
		if( largest != pos){
			//System.out.println("hi");
			swap(array,pos,largest);
			Heapify(array,largest,heapsize);
		}
		//System.out.println(Arrays.toString(heapArray));
	}
	private int getParent(int i){
		return i/2;
	}
	private int getLeft(int i){
		return 2*i+1;
	}
	private int getRight(int i){
		return 2*i + 2;
	}
}

public class Sorting {
	public static void BubbleSort(int[] array ){
		boolean swapFlag = false;
		int swapCount = 0;
		int compareCount = 0;
		int size = array.length;
		//System.out.println("size: " + size);
		for( int i = 0 ; i < size - 1; i++ ){
			for( int j = 0 ; j < size - i - 1 ; j++){
				compareCount++;
				if( array[j] > array[j+1]){
					swap(array,j,j+1);
					swapFlag = true;
					swapCount++;
				}
			}
			//System.out.println("i: " + i + " Array: " + Arrays.toString(array));
			if( !swapFlag ){
				//break;
			}
		}
		System.out.println("swap Count: " + swapCount);
		System.out.println("compare Count: " + compareCount);
	}
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static void InsertionSort(int[] array){
		
		int size = array.length;
		for( int i = 0 ; i < size;i++){
			int temp = array[i];
			int j = i;
			for( ; j> 0 && array[j-1] > temp ; j-- ){
				array[j] = array[j-1];
			}
			array[j] = temp;
		}
		
		/*int swapCount = 0;
		int compareCount = 0;
		int size = array.length;
		int posSorted = FindUnsortedPos(array,-1);
		for( int i = posSorted + 1 ; i < size; ){
			compareCount++;
			System.out.println( " array: " + Arrays.toString(array));
			if( i < size - 1 && array[i] > array[i+1]){
				System.out.println("in swap");
				swapCount++;
				swap(array,i,i+1);
			}
			int targetVal = array[i];
			System.out.println(" target: " + targetVal + " array: " + Arrays.toString(array));
			int targetPos = FindTargetInsertPos(array,targetVal,i);
			System.out.println("target pos " + targetPos);
			shiftVal(array, targetPos, i);
			array[targetPos] = targetVal;
			System.out.println( " array: " + Arrays.toString(array));
			i = FindUnsortedPos(array,i-1);
			System.out.println("i: " + i);
		}
		System.out.println("pos: " + posSorted);*/
		
	}

	public static void mergeSort(int[] array) {
		int size = array.length;
		int[] tempArray = Arrays.copyOf(array, size);
		//System.out.println(array + " " + tempArray);
		DomergeSort(array,tempArray,0,size-1);
		InsertionSort(array);
	}
	
	public static void heapSort(int[] array){
		Heap hp = new Heap(array);
		int temp = array.length - 1;
		//System.out.println(Arrays.toString(array));
		for( int i = array.length - 1 ; i >=0;i--){
			swap(array,0,i);
			hp.Heapify(array, 0, temp );
			//System.out.println(Arrays.toString(array));
			temp--;
		}
		//System.out.println(Arrays.toString(array));
	}
	
	
	private static void DomergeSort(int[] array, int[] temp, int lo, int hi){
		int sigma = 8;
		//System.out.println("hi " +" lo" + lo + " hi" + hi + (hi-lo<sigma));
		if( (hi - lo) > sigma){
			//System.out.println("hi " +" lo" + lo + " hi" + hi);
			int mid = (hi+lo)/2;
			DomergeSort(array,temp,lo,mid);
			DomergeSort(array,temp,mid+1,hi);
			DoMerge(array,temp,lo,mid,hi);
		}
	}

	private static void DoMerge(int[] array, int[] temp, int lo, int mid, int hi){
		int left = lo;
		int right = mid+1;
		for( int i = lo;i<=hi;i++){
			temp[i] = array[i];
		}
		//System.out.println(Arrays.toString(array) + " lo" + lo + " hi" + hi);
		for( int i = lo; i<=hi;i++){
			if(left > mid ){
				array[i]=temp[right++];
			}
			else if(right>hi){
				array[i]=temp[left++];
			}
			else if ( temp[left] < temp[right]){
				array[i]=temp[left++];
			}
			else {
				array[i]=temp[right++];
			}
		}
	}

	/*private static void shiftVal(int[] array, int targetPos, int endPos){
		for( int i = endPos ; i > targetPos ; i--){
			array[i] = array[i-1];
		}
	}
	private static int FindTargetInsertPos(int[] array, int target, int startPos){
		int i = startPos-1;
		for( ; i >= 0 && array[i] > target; i-- );
		return ++i;
	}
	
	private static int FindUnsortedPos(int[] array, int startPos){
		int size = array.length;
		int pos = size-1;
		for( int i  = startPos + 1; i < size - 1 ; i++){
			if( array[i] > array[i+1]){
				pos = i;
				break;
			}
		}
		return pos;
	}*/

}

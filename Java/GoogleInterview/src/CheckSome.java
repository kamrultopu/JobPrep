import java.util.Arrays;


public class CheckSome {
	public void BuildMaxHeap(int[] arr,int heapsize){
		for( int i = heapsize/2; i>=0 ;i--){
			Heapify(arr,i);
		}
	}
	public void Heapify(int[] arr, int target){
		
		int left = 2*target + 1;
		int right = 2*target + 2;
		int parent = (target-1)/2;
		int largest = target;
		int size = arr.length;
		if( left<size && arr[left]> arr[target]){
			largest = left;
		}
		if( right < size && arr[right] > arr[largest]){
			largest = right;
		}
		//System.out.println(target + " "+ largest);
		if( largest != target){
			swap(arr,largest,target);
			//System.out.println(Arrays.toString(arr));
			Heapify(arr,largest);
		}
		//System.out.println(Arrays.toString(arr));
	}
	public void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j]= temp;
	}
}

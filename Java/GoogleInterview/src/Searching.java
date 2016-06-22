
public class Searching {
	public static int LinearSearch(int[] array, int target){
		int size = array.length;
		for( int i = 0 ; i < size; i++){
			if( array[i] == target){
				return i;
			}
		}
		return -1;
	}
	public static int BinarySearch(int[] array, int target){
		return binarySearchHelper(array,0,array.length - 1,target);
	}
	private static int binarySearchHelper(int[] array, int low, int high, int target){
		if( low <= high){
			int mid = (low+high)/2;
			if( target == array[mid]){
				return mid;
			}
			else if( target < array[mid]){
				return binarySearchHelper(array, low, mid - 1, target);
			}
			else{
				return binarySearchHelper(array, mid+1, high, target);
			}
		}
		return -1;
	}
}
